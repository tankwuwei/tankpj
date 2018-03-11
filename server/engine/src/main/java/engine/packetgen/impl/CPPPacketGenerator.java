package engine.packetgen.impl;

import java.io.File;
import java.util.LinkedHashMap;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Element;

import engine.util.IOUtil;
import engine.util.IndentStringBuilder;
import engine.util.Utility;

public class CPPPacketGenerator {

	private static String startTag = "public static final short";

	private static String defalueIncludeFile;

	public static File packetXMLFile;
	public static File outputDir;
	public static File cppoutputDir;
	public static File errorCodeFile;
	public static File errorCodeOutFile;

	// 单个属�??
	private static class Field {
		public String name;
		public String type;
		public String refClassName;
		public String des;

		public void parse(Element e) {
			name = e.getAttributeValue("name").trim();
			type = e.getAttributeValue("type").trim();
			des = e.getAttributeValue("des");
			if (type.equals("String"))
				type = "string";
			if (type.equals("String[]"))
				type = "string[]";
			if (type.equals("sbyte"))
				type = "byte";
			if (type.equals("byte"))
				type = "int8";
			if (type.equals("long"))
				type = "int64";
			if (type.equals("long[]"))
				type = "int64[]";
			if (type.equals("float"))
				type = "float32";
			if (type.equals("float[]"))
				type = "float32[]";
			if (type.equals("boolean"))
				type = "bool";
			if (type.equals("boolean[]"))
				type = "bool[]";
			if (type.equals("ref") || type.equals("refArray") || type.equals("refPacket"))
				refClassName = e.getAttributeValue("refType").trim();
		}

		public boolean isRef() {
			return type.equals("ref");
		}

		public boolean isRefArray() {
			return type.equals("refArray");
		}

		public void gen(IndentStringBuilder builder) {
			String cType = type;
			if (type.equals("ref"))
				cType = refClassName;
			else if (type.equals("refArray"))
				cType = refClassName + "[]";
			else if (type.equals("refPacket"))
				cType = refClassName;
			else if (type.equals("directByte[]"))
				cType = "byte[]";
			int pos = cType.indexOf("[]");
			if (pos > 0) {
				builder.appendln2("vector<").append(cType.substring(0, pos)).append("> ").append(name).append(";");
			} else {
				builder.appendln2(cType).append(" ").append(name).append(";");
			}
			if (des != null) {
				builder.append("   /* ").append(des).append(" */");
			}
		}

		public void genRead(IndentStringBuilder builder) {
			switch (type) {
			case "string":
			case "string[]":
			case "int8":
			case "int8[]":
			case "short":
			case "short[]":
			case "int":
			case "int[]":
			case "int64":
			case "int64[]":
			case "bool":
			case "bool[]":
			case "float32":
			case "float32[]":
				builder.appendln2("str >> ").append(name).append(";");
				break;
			case "ref":
				readRef(builder);
				break;
			case "refArray":
				readArray(builder);
				break;
			/*
			 * case "float": case "double": case "String[]": case "int[]": case
			 * "short[]": case "byte[]": case "long[]": case "float[]": case
			 * "double[]": case "directByte[]":
			 */
			default:
				throw new RuntimeException("unknown type:" + type);
			}
		}

		private void readRef(IndentStringBuilder builder) {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			builder.appendln2("int8 bo" + refClassName + ";");
			builder.appendln2("str >> bo" + refClassName + ";");
			builder.appendln2("if(bo" + refClassName + " == 1)");
			builder.appendln2("{");
			builder.appendln2(name).append(".read(str);");
			builder.appendln2("}");
		}

		private void readArray(IndentStringBuilder builder) {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			String lengthName = name + "Length";
			builder.appendln2("int16 " + lengthName + ";");
			builder.appendln2("int8 bo" + refClassName + ";");
			builder.appendln2("str >> ").append(lengthName).append(";");
			builder.appendln2("for(int i=0;i<" + lengthName + ";i++)");
			builder.appendln2("{");
			builder.appendln2("str >> bo" + refClassName + ";");
			builder.appendln2("if(bo" + refClassName + " == 1)");
			builder.appendln2("{");
			builder.appendln2(refClassName + " obj;");
			builder.appendln2("obj.read(str);");
			builder.appendln2(name + ".push_back(obj);");
			builder.appendln2("}");
			builder.appendln2("}");
		}

		public void genWrite(IndentStringBuilder builder) {
			switch (type) {
			case "string":
			case "string[]":
			case "int8":
			case "int8[]":
			case "short":
			case "short[]":
			case "int":
			case "int[]":
			case "int64":
			case "int64[]":
			case "boolean":
			case "boolean[]":
			case "float32":
			case "float32[]":
				builder.appendln2("str << ").append(name).append(";");
				break;
			case "ref":
				writeRef(builder);
				break;
			case "refArray":
				writeRefArray(builder);
				break;
			default:
				throw new RuntimeException("unknown type:" + type);
			}

		}

		private void writeRef(IndentStringBuilder builder) {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified. " + name);
			builder.appendln2("str << (int8)1;");
			builder.appendln2(name).append(".write(str);");

		}

		private void writeRefArray(IndentStringBuilder builder) {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");

			builder.appendln2("str << ((uint16)").append(name).append(".size());");
			builder.appendln2("for(int i=0;i<").append(name).append(".size();i++)");
			builder.appendln2("{");
			builder.appendln2("str << (int8)1;");
			builder.appendln2(name).append("[i].write(str);");
			builder.appendln2("}");
		}

	}

	/**
	 * 单个节点的结�?
	 * 
	 * @author Administrator
	 *
	 */
	private static class PacketClass {
		public int type;
		public int tagType = -1; // -1 only c# code gen,0 packet 1 ref
		public String name;
		public String des;
		public int code;
		public boolean genRead, genWrite;
		public List<Field> fields = new ArrayList<Field>();
		// public boolean refDBObject;
		// public boolean refVOObject;

		public void parse(Element e) {
			String tag = e.getName();
			name = e.getAttributeValue("name");
			des = e.getAttributeValue("des");
			if (tag.equals("packet"))
				tagType = 0;
			else if (tag.equals("ref"))
				tagType = 1;

			if (tagType == 0) {// packet
				code = Integer.parseInt(e.getAttributeValue("id"));
				type = Integer.parseInt(e.getAttributeValue("type"));
				if (type == JavaPacketGenerator.ClientServer) {
					// client to server packet
					genWrite = true;
				} else if (type == JavaPacketGenerator.ServerClient) {
					// server to client packet
					genRead = true;
				}
			} else if (tagType == 1) {// objects
				genWrite = true;
				genRead = true;
			}

			for (Element child : e.getChildren()) {
				Field f = new Field();
				f.parse(child);
				fields.add(f);
			}
		}

		/**
		 * 生成单个对象的具体代�?
		 * 
		 * @return
		 */
		public IndentStringBuilder gen() {
			IndentStringBuilder builder = new IndentStringBuilder();
			builder.appendln2("/* ").append(des).append(" */");
			builder.appendln2("struct ");
			if (tagType == 0)
				builder.append("st");
			builder.append(name);
			if (tagType == 0) { // packet
				builder.append(" : public msgbase");
			}
			builder.appendln2("{");
			if (tagType == 0) {
				builder.appendln2("st").append(name).append("()");
				builder.appendln2("{");
				builder.appendln2("code=").append(name).append(";");
				// builder.appendln2("code=").append(code).append(";");
				builder.appendln2("}");
			}
			for (Field f : fields) {
				f.gen(builder);
			}
			builder.appendln2("");
			if (genRead) {
				builder.appendln2("void read(Stream& str)");
				builder.appendln2("{");
				for (Field f : fields) {
					f.genRead(builder);
				}
				builder.appendln2("}");

			}
			builder.appendln2("");
			if (genWrite) {
				builder.appendln2("void write(Stream& str)");
				builder.appendln2("{");
				for (Field f : fields) {
					f.genWrite(builder);
				}
				builder.appendln2("}");

			}

			builder.appendln2("};");
			builder.appendln2("");
			return builder;
		}

		/**
		 * case LoginRet: pMsg = new stLoginRet(); pMsg->read(str); break;
		 * 
		 * @return
		 */
		public IndentStringBuilder genGetCode() {
			IndentStringBuilder builder = new IndentStringBuilder();
			builder.appendln2("\tcase ").append(name).append(":");
			builder.appendln2("\t\tpMsg = new st").append(name).append("();");
			builder.appendln2("\t\tpMsg->read(str);");
			builder.appendln2("\t\tbreak;");
			return builder;
		}

	}

	private static void write(File out, String content) {
		out.getParentFile().mkdirs();
		try {
			FileWriter writer = new FileWriter(out);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writebom(File out, String content) {
		out.getParentFile().mkdirs();
		try {
			FileWriter writer = new FileWriter(out);
			byte[] uft8bom = { (byte) 0xef, (byte) 0xbb, (byte) 0xbf };// bom
			writer.write(new String(uft8bom));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		defalueIncludeFile = args[0];
		new CPPPacketGenerator().run();
	}

	void run() {
		try {
			System.out.println("start to generate cpp server-client msg files...");
			Element root = Utility.readXML(packetXMLFile).getRootElement();

			List<PacketClass> cList = new ArrayList<PacketClass>();
			List<PacketClass> sList = new ArrayList<PacketClass>();
			List<PacketClass> voList = new ArrayList<PacketClass>();// for
																	// object

			Set<Integer> codeSet = new HashSet<>(); // 避免code定义重复
			for (Element e : root.getChildren()) {

				PacketClass pc = new PacketClass();
				pc.parse(e);
				if (pc.code != 0) {
					if (codeSet.contains(pc.code)) {
						throw new RuntimeException("PacketCode repeated:" + pc.code);
					}
					codeSet.add(pc.code);
				}

				// pc.write(outputDir);
				if (pc.tagType == 0) {
					if (pc.type == JavaPacketGenerator.ClientServer)
						cList.add(pc);
					else if (pc.type == JavaPacketGenerator.ServerClient)
						sList.add(pc);
				} else if (pc.tagType == 1) {
					voList.add(pc);
				}
			}
			genObject(voList);
			genCode(cList, sList);

			System.out.println("finish to generate cpp server-client msg files.");
			System.out.println("//");

			genErrorCode();
			System.out.println("finish to generate cpp errorcode files.");
			System.out.println("//");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void genErrorCode() {
		IndentStringBuilder builder = new IndentStringBuilder();
		builder.append("#pragma once");
		builder.appendln2("#include \"CoreMinimal.h\"");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("// 通用错误码定义");
		builder.appendln2("// 自动生成，请勿修改！");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("namespace ErrorCode ");
		builder.appendln2("{\n");

		Map<String, String> linked = new LinkedHashMap();
		String text = IOUtil.readText(errorCodeFile);
		int indexStart = text.indexOf(startTag);
		while (indexStart != -1) {
			int indexEnd = text.indexOf(";", indexStart) + 1;
			int equalend = text.indexOf("=", indexStart) + 1;
			builder.appendln2("const int16 ");
			String val = text.substring(indexStart + startTag.length(), equalend);
			String[] vals = val.split("=");
			val = vals[0].trim();

			builder.append(text.substring(indexStart + startTag.length(), indexEnd));
			// 添加注释
			builder.append(" /* ");
			indexStart = indexEnd;
			indexEnd = text.indexOf("\n", indexStart);
			String comm = text.substring(indexStart, indexEnd);
			comm = comm.trim();
			if (comm.length() >= 2) {
				comm = comm.substring(2, comm.length());
			}
			builder.append(comm.trim());
			builder.append(" */");
			indexStart = text.indexOf(startTag, indexEnd);

			// System.out.println(val+" "+comm);
			linked.put(val, comm);
		}
		builder.appendln2("}\n");

		builder.append("class ErrorCodeMap\n{\npublic:\n\tErrorCodeMap()\n\t{\n");
		String printf = "\t\tMap.Add(ErrorCode::%s, NSLOCTEXT(\"ErrorCode\", \"%s\", \"%s\"));\n";
		for (Map.Entry<String, String> entry : linked.entrySet()) {
			builder.append(String.format(printf, entry.getKey().trim(), entry.getKey(), entry.getValue().trim()));
		}
		String end = "\t}\n\n\tFText Get(int16 Code) const\n\t{\n\t\tif (Map.Contains(Code))\n\t\t{\n\t\t\treturn Map[Code];\n\t\t}\n\t\treturn NSLOCTEXT(\"ErrorCode\", \"UnknownError\", \"未知错误\");\n\t}\nprivate:\n\tTMap<int16, FText> Map;\n};";

		builder.append(end);
		CPPPacketGenerator.writebom(errorCodeOutFile, builder.toString());
	}

	/**
	 * 生成object集合文件
	 * 
	 * @param olist
	 */
	private void genObject(List<PacketClass> olist) {
		IndentStringBuilder builder = new IndentStringBuilder();
		builder.append("#ifndef __PACKAGE_OBJ_H__");
		builder.appendln2("#define __PACKAGE_OBJ_H__");
		builder.appendln2("#include \"../stream.h\"");
		builder.appendln2("using namespace std;");
		builder.appendln2("");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("// 网络数据包中用到的结构定义");
		builder.appendln2("// 自动生成，请勿修改！");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("");

		for (PacketClass obj : olist) {
			builder.appendln2(obj.gen().toString());
		}
		builder.appendln2("#endif");

		File out = new File(outputDir, "packageobj.h");
		CPPPacketGenerator.write(out, builder.toString());
	}

	private void genCode(List<PacketClass> cList, List<PacketClass> sList) {
		// Gen code
		cList.sort(new Comparator<PacketClass>() {
			public int compare(PacketClass o1, PacketClass o2) {
				return o1.code - o2.code;
			}
		});
		sList.sort(new Comparator<PacketClass>() {
			public int compare(PacketClass o1, PacketClass o2) {
				return o1.code - o2.code;
			}
		});

		IndentStringBuilder builder = new IndentStringBuilder();
		builder.append("#ifndef __CODE_H__");
		builder.appendln2("#define __CODE_H__");
		builder.appendln2("#include \"packageobj.h\"");
		builder.appendln2("");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("// 网络数据包定义");
		builder.appendln2("// 自动生成，请勿修改！");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("");
		// builder.appendln2("class Code{");
		// builder.appendln2("public:");
		builder.appendln2("// ////////////////client packets//////////////");
		for (PacketClass pc : cList) {
			builder.appendln2("static const short ").append(pc.name).append(" = ").append(pc.code).append(";")
					.append("\t/* ").append(pc.des).append(" */");
		}
		builder.appendln2("// ////////////////server packets//////////////");
		for (PacketClass pc : sList) {
			builder.appendln2("static const short ").append(pc.name).append(" = ").append(pc.code).append(";")
					.append("\t/* ").append(pc.des).append(" */");
		}
		// builder.appendln2("}");
		builder.appendln2("// ////////////////packets define//////////////");
		builder.appendln2("struct msgbase");
		builder.appendln2("{");
		builder.appendln2("int length;");
		builder.appendln2("short appkey;");
		builder.appendln2("short code;");
		builder.appendln2("virtual void write(Stream& str){}");
		builder.appendln2("virtual void read(Stream& str){}");
		builder.appendln2("virtual ~msgbase() {}");
		builder.appendln2("};");

		for (PacketClass pc : cList) {
			builder.appendln2(pc.gen().toString());
		}
		for (PacketClass pc : sList) {
			builder.appendln2(pc.gen().toString());
		}

		// builder.appendln2(GenGetCode(sList));
		builder.appendln2("msgbase* GetMsg(short msgid, Stream& str);");
		builder.appendln2("#endif");
		File out = new File(outputDir, "netpackage.h");
		CPPPacketGenerator.write(out, builder.toString());

		File cpp = new File(cppoutputDir, "netpackage.cpp");
		CPPPacketGenerator.write(cpp, GenGetCode(sList).toString());

	}

	private IndentStringBuilder GenGetCode(List<PacketClass> sList) {
		IndentStringBuilder builder = new IndentStringBuilder();
		if (!defalueIncludeFile.isEmpty()) {
			builder.append("#include \"" + defalueIncludeFile + "\"");
		}
		builder.appendln2("#include \"netpackage.h\"");
		builder.appendln2("");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("// 网络数据包定义");
		builder.appendln2("// 自动生成，请勿修改！");
		builder.appendln2("/////////////////////////////////////////");
		builder.appendln2("");
		builder.appendln2("msgbase* GetMsg(short msgid, Stream& str) {");
		builder.appendln2("\tmsgbase* pMsg = NULL;");
		builder.appendln2("\tswitch (msgid) {");
		for (PacketClass pc : sList) {
			builder.append(pc.genGetCode().toString());
		}

		builder.appendln2("\tdefault:");
		builder.appendln2("\t\tbreak;");
		builder.appendln2("\t}");
		builder.appendln2("\treturn pMsg;");
		builder.appendln2("}");
		return builder;
	}

}
