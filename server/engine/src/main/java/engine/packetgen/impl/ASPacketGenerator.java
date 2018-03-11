package engine.packetgen.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Element;

import engine.util.Utility;

public class ASPacketGenerator {
	public static File packetXMLFile;
	public static File outputDir;
	public static String packetNameStatic = "com.rpc";

	private static class Field {
		@SuppressWarnings("unused")
		public PacketClass packetClass;
		public String name;
		public String type;
		public String des;
		public String refClassName;
		public boolean isTransient;

		private int indent = 0;
		private String indentSpace = "	";
		private StringBuilder content = new StringBuilder();

		public StringBuilder appendln(String v) {
			content.append("\n");

			if (v.equals("}"))
				indent--;

			for (int i = 0; i < indent; i++)
				content.append(indentSpace);

			if (v.equals("{"))
				indent++;

			content.append(v);
			return content;
		}

		@SuppressWarnings("unused")
		public StringBuilder append(String v) {
			return content.append(v);
		}

		public void parse(Element e) {
			name = e.getAttributeValue("name").trim();
			type = e.getAttributeValue("type").trim();
			des = e.getAttributeValue("des");
			if (type.equals("string"))
				type = "String";
			if (type.equals("string[]"))
				type = "String[]";
			if (type.equals("sbyte"))
				type = "byte";
			if (type.equals("ref") || type.equals("refArray") || type.equals("refPacket"))
				refClassName = e.getAttributeValue("refType").trim();

			isTransient = Boolean.parseBoolean(e.getAttributeValue("transient", "false"));
		}

		public boolean isRef() {
			return type.equals("ref");
		}

		public boolean isRefArray() {
			return type.equals("refArray");
		}

		@SuppressWarnings("unused")
		public boolean isRefPacket() {
			return type.equals("refPacket");
		}

		public void gen(StringBuilder content, int indent) {
			this.content = content;
			this.indent = indent;

			// String cType=type;
			// if(type.equals("ref"))cType=refClassName;
			// else if(type.equals("refArray")) cType=refClassName+"[]";
			// else if(type.equals("refPacket")) cType=refClassName;

			appendln("//" + des);
			switch (type) {
			case "String":
				appendln("public var ").append(name).append(": String;");
				break;
			case "int":
				appendln("public var ").append(name).append(": int;");
				break;
			case "short":
				appendln("public var ").append(name).append(": int;");
				break;
			case "byte":
				appendln("public var ").append(name).append(": int;");
				break;
			case "long":
				appendln("public var ").append(name).append(": Number;");
				break;
			case "float":
				appendln("public var ").append(name).append(": Number;");
				break;
			case "double":
				appendln("public var ").append(name).append(": Number;");
				break;
			case "boolean":
				appendln("public var ").append(name).append(": Boolean;");
				break;
			case "String[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "int[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "short[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "byte[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "long[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "float[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "double[]":
				appendln("public var ").append(name).append(": Array;");
				break;
			case "ref":
				appendln("public var ").append(name).append(": " + refClassName + ";");
				break;
			case "refArray":
				appendln("public var ").append(name).append(": Array;");
				break;
			default:
				throw new RuntimeException("unknown type:" + type);
			}
		}

		public void genRead(StringBuilder content, int indent) {
			this.content = content;
			this.indent = indent;
			switch (type) {
			case "String":
				appendln(name).append("=buf.readUTF();");
				break;
			case "int":
				appendln(name).append("=buf.readInt();");
				break;
			case "short":
				appendln(name).append("=buf.readShort();");
				break;
			case "byte":
				appendln(name).append("=buf.readByte();");
				break;
			case "long":
				appendln(name).append("=buf.readLong();");
				break;
			case "float":
				appendln(name).append("=buf.readFloat();");
				break;
			case "double":
				appendln(name).append("=buf.readDouble();");
				break;
			case "boolean":
				appendln(name).append("=buf.readBoolean();");
				break;
			case "String[]":
				appendln(name).append("=buf.readUTFArray();");
				break;
			case "int[]":
				appendln(name).append("=buf.readIntArray();");
				break;
			case "short[]":
				appendln(name).append("=buf.readShortArray();");
				break;
			case "byte[]":
				appendln(name).append("=buf.readByteArray();");
				break;
			case "long[]":
				appendln(name).append("=buf.readLongArray();");
				break;
			case "float[]":
				appendln(name).append("=buf.readFloatArray();");
				break;
			case "double[]":
				appendln(name).append("=buf.readDoubleArray();");
				break;

			case "ref":
				readRef();
				break;
			case "refArray":
				readArray();
				break;
			default:
				throw new RuntimeException("unknown type:" + type);
			}
		}

		private void readRef() {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			appendln("if(buf.readByte()==0) ").append(name).append("=null;");
			appendln("else");
			appendln("{");
			appendln(name).append("=new ").append(refClassName).append("();");
			appendln(name).append(".read(buf);");
			appendln("}");
		}

		private void readArray() {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			String lengthName = name + "Length";
			appendln("var " + lengthName + ":int=buf.readShort();");
			appendln("if(" + lengthName + "==0) ").append(name).append("=null;");
			appendln("else");
			appendln("{");
			appendln(name).append("=[];");
			String varFor = "index" + name;
			appendln("for(var " + varFor + ":int = 0;" + varFor + "<" + lengthName + ";" + varFor + "++)");
			appendln("{");
			String varName = name + "Component";
			appendln("if(buf.readByte()==1)");
			appendln("{");

			appendln("var " + varName + ":" + refClassName).append("=new ").append(refClassName).append("();");
			appendln(varName + ".read(buf);");
			appendln("" + name + "[" + varFor + "]=").append(varName + ";");
			appendln("}");
			appendln("}");
			appendln("}");
		}

		public void genWrite(StringBuilder content, int indent) {
			if (!isTransient) {
				switch (type) {
				case "String":
					appendln("buf.writeUTF(").append(name).append(");");
					break;
				case "int":
					appendln("buf.writeInt(").append(name).append(");");
					break;
				case "short":
					appendln("buf.writeShort(").append(name).append(");");
					break;
				case "byte":
				case "sbyte":
					appendln("buf.writeByte(").append(name).append(");");
					break;
				case "long":
					appendln("buf.writeLong(").append(name).append(");");
					break;
				case "float":
					appendln("buf.writeFloat(").append(name).append(");");
					break;
				case "double":
					appendln("buf.writeDouble(").append(name).append(");");
					break;
				case "boolean":
					appendln("buf.writeBoolean(").append(name).append(");");
					break;
				case "String[]":
					appendln("buf.writeUTFArray(").append(name).append(");");
					break;
				case "int[]":
					appendln("buf.writeIntArray(").append(name).append(");");
					break;
				case "short[]":
					appendln("buf.writeShortArray(").append(name).append(");");
					break;
				case "byte[]":
					appendln("buf.writeByteArray(").append(name).append(");");
					break;
				case "long[]":
					appendln("buf.writeLongArray(").append(name).append(");");
					break;
				case "float[]":
					appendln("buf.writeFloatArray(").append(name).append(");");
					break;
				case "double[]":
					appendln("buf.writeDoubleArray(").append(name).append(");");
					break;

				case "ref":
					writeRef();
					break;
				case "refArray":
					writeRefArray();
					break;
				default:
					throw new RuntimeException("unknown type:" + type);
				}
			}

		}

		private void writeRef() {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified. " + name);
			appendln("if(").append(name).append("==null) buf.writeByte(0);");
			appendln("else");
			appendln("{");
			appendln("buf.writeByte(1);");
			appendln(name).append(".write(buf);");
			appendln("}");

		}

		private void writeRefArray() {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			appendln("if(").append(name).append("==null) buf.writeShort(0);");
			appendln("else");
			appendln("{");
			String varFor = "index" + name;
			appendln("buf.writeShort(").append(name).append(".length);");
			appendln("for(var " + varFor + ":int=0;" + varFor + "<").append(name).append(".length;" + varFor + "++)");
			appendln("{");
			appendln(name).append("[" + varFor + "].write(buf);");
			appendln("}");
			appendln("}");
		}

		// private void writeArray(StringBuilder builder,String type){
		// if(packetClass.firstWriteArray){
		// packetClass.firstWriteArray=false;
		// builder.append("int _length=0;\n");
		// }
		// builder.append("if(").append(name).append("==null||").append(name).append(".length==0").append(")").append("buf.writeShort(0);\n");
		// builder.append("else{\n");
		// builder.append("_length=").append(name).append(".length;\n");
		// builder.append("buf.writeShort(_length);\n");
		// builder.append("for(int i=0;i<_length;i++) ");
		// switch(type){
		// case "String":
		// builder.append("writeUTF(buf,").append(name).append("[i]);\n");break;
		// case
		// "int":builder.append("buf.writeInt(").append(name).append("[i]);\n");break;
		// case
		// "short":builder.append("buf.writeShort(").append(name).append("[i]);\n");break;
		// case
		// "byte":builder.append("buf.writeByte(").append(name).append("[i]);\n");break;
		// case
		// "long":builder.append("buf.writeLong(").append(name).append("[i]);\n");break;
		// case
		// "float":builder.append("buf.writeFloat(").append(name).append("[i]);\n");break;
		// }
		//
		//
		// builder.append("}\n");
		// }

	}

	private static class PacketClass {
		public int type;
		public int tagType = -1; // -1 only c# code gen,0 packet 1 ref
		public String packageName;
		public String name;
		public String des;
		public int code;
		public boolean genRead, genWrite;
		public List<Field> fields = new ArrayList<Field>();

		// public boolean firstArray=true;
		// public boolean firstWriteArray=true;

		// String[] forVars = new
		// String[]{"ii1","ii2","ii3","ii4","ii5","ii6","ii7","ii8","ii9","ii10","ii11","ii12","ii13"};

		public int indent = 0;
		public String indentSpace = "	";
		StringBuilder content = new StringBuilder();

		public StringBuilder appendln(String v) {
			content.append("\n");

			if (v.equals("}"))
				indent--;

			for (int i = 0; i < indent; i++)
				content.append(indentSpace);

			if (v.equals("{"))
				indent++;

			content.append(v);
			return content;
		}

		public StringBuilder append(String v) {
			return content.append(v);
		}

		public void parse(Element e) {
			String tag = e.getName();
			des = e.getAttributeValue("des");
			if (tag.equals("packet"))
				tagType = 0;
			else if (tag.equals("ref"))
				tagType = 1;

			if (tagType == 0) {// packet
				code = Integer.parseInt(e.getAttributeValue("id"));
				type = Utility.parseInt(e.getAttributeValue("type"));
				if (type == 0) {
					// client to server packet
					// packageName+=".client";
					clientPackets.packets.add(this);
				} else if (type == 1) {
					// server to client packet
					// packageName+=".server";
					clientPackets.packets.add(this);
				} else if (type == 3) {
					// packageName+=".client";
					clientPackets.packets.add(this);
				} else if (type == 4) {
					// packageName+=".server";
					clientPackets.packets.add(this);
				}
			} else if (tagType == 1) {// objects
				// packageName+=".objects";
			}

			genRead = true;
			genWrite = true;

			for (Element child : e.getChildren()) {
				Field f = new Field();
				f.packetClass = this;
				f.parse(child);
				fields.add(f);
			}
		}

		public StringBuilder gen() {
			content.setLength(0);
			appendln("package " + packageName);
			appendln("{");
			if (!StringUtils.isEmpty(des)) {
				appendln("//").append(des);
			}
			appendln("public class ").append(name).append(" extends ");
			if (tagType == 0) {
				append("CPacket");
				appendln("{");
				appendln("public function ").append(name).append("()");
				appendln("{");
				appendln("code=").append(code).append(";");
				appendln("}");
			} else if (tagType == 1) {
				append("CValue");
				appendln("{");
			}

			for (Field f : fields) {
				f.gen(content, indent);
			}

			if (genRead) {
				appendln("override public function read(buf:NativeBuffer):void");
				appendln("{");
				for (Field f : fields) {
					f.genRead(content, indent);
				}
				appendln("}");
			}
			if (genWrite) {
				appendln("override public function write(buf:NativeBuffer ):void");
				appendln("{");
				for (Field f : fields) {
					f.genWrite(content, indent);
				}
				appendln("}");

			}

			appendln("}");
			appendln("}");
			return content;
		}

		public void write(File root) {
			if (type == 3 || type == 4)
				return;
			String relPath = packageName.replace('.', '/');
			if (!relPath.endsWith("/"))
				relPath += "/";
			relPath += name + ".as";
			File out = new File(root, relPath);
			ASPacketGenerator.write(out, gen().toString());
		}

	}

	private static class ClientPackets {
		public String packageName;
		@SuppressWarnings("unused")
		public String name;
		public List<PacketClass> packets = new ArrayList<PacketClass>();

		public int indent = 0;
		public String indentSpace = "	";
		StringBuilder content = new StringBuilder();

		public StringBuilder appendln(String v) {
			content.append("\n");

			if (v.equals("}"))
				indent--;

			for (int i = 0; i < indent; i++)
				content.append(indentSpace);

			if (v.equals("{"))
				indent++;

			content.append(v);
			return content;
		}

		public StringBuilder append(String v) {
			return content.append(v);
		}

		public void write(File root) {
			content.setLength(0);
			;
			append("package ").append(packageName);
			appendln("{");
			appendln("public class Quoter");
			appendln("{");
			appendln("public function Quoter()");
			appendln("{");
			appendln("super() ;");
			appendln("_c=");
			appendln("{");
			int index = 0;
			for (PacketClass pc : packets) {
				if (pc.tagType == 0 && (pc.type == 0 || pc.type == 1)) {
					if (index > 0) {
						append(",");
					}
					appendln(pc.code + ":" + pc.name);
					index++;
				}
			}
			appendln("}");
			appendln("}");
			appendln("private var _c:Object;");
			appendln("public function get commands():Object");
			appendln("{");
			appendln("return _c;");
			appendln("}");
			appendln("}");
			appendln("}");

			String relPath = packageName.replace('.', '/');
			if (!relPath.endsWith("/"))
				relPath += "/";
			relPath += "Quoter.as";
			File out = new File(root, relPath);
			ASPacketGenerator.write(out, content.toString());
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

	private static ClientPackets clientPackets = new ClientPackets();

	public static void main(String[] args) {
		new ASPacketGenerator().run();
	}

	void run() {
		try {
			System.out.println("start to generate as server-client msg files...");
			Element root = Utility.readXML(packetXMLFile).getRootElement();

			clientPackets.name = "ClientPackets";
			clientPackets.packageName = packetNameStatic;

			List<PacketClass> cList = new ArrayList<PacketClass>();
			List<PacketClass> sList = new ArrayList<PacketClass>();

			for (Element e : root.getChildren()) {

				PacketClass pc = new PacketClass();
				pc.name = e.getAttributeValue("name");
				pc.packageName = packetNameStatic;
				pc.parse(e);
				pc.write(outputDir);

				if (pc.tagType == 0) {
					if (pc.type == 0)
						cList.add(pc);
					else
						sList.add(pc);
				}
			}
			clientPackets.write(outputDir);

			genCode(cList, sList, packetNameStatic);
			System.out.println("finish to generate as server-client msg files.");
			System.out.println("//");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void genCode(List<PacketClass> cList, List<PacketClass> sList, String packageName) {
		// gen code
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

		StringBuilder builder = new StringBuilder();
		builder.append("package ").append(packageName);
		builder.append("{\n");
		builder.append("public class Code{\n");
		// builder.append("// /////////////TNet inherient code////////////\n");
		// int i=0;
		// for(java.lang.reflect.Field f:Packet.class.getFields()){
		// builder.append("short TN_"+f.getName()+"="+i+++";\n");
		// }
		builder.append("\n// ////////////////client packets//////////////\n");
		for (PacketClass pc : cList) {
			builder.append("short ").append(pc.name).append(" = ").append(pc.code).append(";\n");
		}
		builder.append("\n// ////////////////server packets//////////////\n");
		for (PacketClass pc : sList) {
			builder.append("short ").append(pc.name).append(" = ").append(pc.code).append(";\n");
		}

		builder.append("}\n");
		builder.append("}\n");
		String relPath = packageName.replace('.', '/');
		if (!relPath.endsWith("/"))
			relPath += "/";
		relPath += "Code.as";
		File out = new File(outputDir, relPath);
		ASPacketGenerator.write(out, builder.toString());
	}

}
