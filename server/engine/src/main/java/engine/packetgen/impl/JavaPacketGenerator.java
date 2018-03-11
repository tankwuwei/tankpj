package engine.packetgen.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Element;

import engine.util.IndentStringBuilder;
import engine.util.Utility;

public class JavaPacketGenerator {
	
	/**
	 * ��Ϣ���Ͷ���
	 */
	public static final int ClientServer = 0;
	public static final int ServerClient = 1;
	public static final int ServerServer = 2;
	public static final int ServerDBServer = 3;
	public static final int DBServerServer = 4;
	
	public static File packetXMLFile;
	public static File outputDir;

	private static class Field {
		public String name;
		public String type;
		public String refClassName;
		public boolean isTransient;

		public void parse(Element e) {
			name = e.getAttributeValue("name").trim();
			type = e.getAttributeValue("type").trim();
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

			builder.appendln2("public ").append(isTransient ? "transient " : " ").append(cType).append(" ").append(name)
					.append(";");
		}

		public void genRead(IndentStringBuilder builder) {
			switch (type) {
			case "String":
				builder.appendln2(name).append("=buf.readUTF();");
				break;
			case "int":
				builder.appendln2(name).append("=buf.readInt();");
				break;
			case "short":
				builder.appendln2(name).append("=buf.readShort();");
				break;
			case "byte":
				builder.appendln2(name).append("=buf.readByte();");
				break;
			case "long":
				builder.appendln2(name).append("=buf.readLong();");
				break;
			case "float":
				builder.appendln2(name).append("=buf.readFloat();");
				break;
			case "double":
				builder.appendln2(name).append("=buf.readDouble();");
				break;
			case "boolean":
				builder.appendln2(name).append("=buf.readBoolean();");
				break;
			case "String[]":
				builder.appendln2(name).append("=buf.readUTFArray();");
				break;
			case "int[]":
				builder.appendln2(name).append("=buf.readIntArray();");
				break;
			case "short[]":
				builder.appendln2(name).append("=buf.readShortArray();");
				break;
			case "byte[]":
				builder.appendln2(name).append("=buf.readByteArray();");
				break;
			case "long[]":
				builder.appendln2(name).append("=buf.readLongArray();");
				break;
			case "float[]":
				builder.appendln2(name).append("=buf.readFloatArray();");
				break;
			case "double[]":
				builder.appendln2(name).append("=buf.readDoubleArray();");
				break;
			case "directByte[]": {
				builder.appendln2("if(buf.readByte()==1)");
				builder.appendln2("{");
				builder.appendln2(name).append("=buf.readBytes();");
				builder.appendln2("}");
				break;
			}
			case "ref":
				readRef(builder);
				break;
			case "refArray":
				readArray(builder);
				break;
			default:
				throw new RuntimeException("unknown type:" + type);
			}
		}

		private void readRef(IndentStringBuilder builder) {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			builder.appendln2("if(buf.readByte()==0) ").append(name).append("=null;");
			builder.appendln2("else");
			builder.appendln2("{");
			builder.appendln2(name).append("=new ").append(refClassName).append("();");
			builder.appendln2(name).append(".read(buf);");
			builder.appendln2("}");
		}

		private void readArray(IndentStringBuilder builder) {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			String lengthName = name + "Length";
			builder.appendln2("int " + lengthName + "=buf.readShort();");
//			builder.appendln2("if(" + lengthName + "==0) ").append(name).append("=null;");
//			builder.appendln2("else");
//			builder.appendln2("{");
			builder.appendln2(name).append("=new ").append(refClassName).append("[" + lengthName + "];");
			builder.appendln2("for(int i=0;i<" + lengthName + ";i++)");
			builder.appendln2("{");
			builder.appendln2(refClassName + " d").append("=new ").append(refClassName).append("();");
			builder.appendln2("if(buf.readByte()==1)");
			builder.appendln2("{");
			builder.appendln2("d.read(buf);");
			builder.appendln2(name + "[i]=").append("d;");
			builder.appendln2("}");
			builder.appendln2("}");
			//builder.appendln2("}");
		}

		public void genWrite(IndentStringBuilder builder) {
			if (!isTransient) {
				switch (type) {
				case "String":
					builder.appendln2("buf.writeUTF(").append(name).append(");");
					break;
				case "int":
					builder.appendln2("buf.writeInt(").append(name).append(");");
					break;
				case "short":
					builder.appendln2("buf.writeShort(").append(name).append(");");
					break;
				case "byte":
				case "sbyte":
					builder.appendln2("buf.writeByte(").append(name).append(");");
					break;
				case "long":
					builder.appendln2("buf.writeLong(").append(name).append(");");
					break;
				case "double":
					builder.appendln2("buf.writeDouble(").append(name).append(");");
					break;
				case "float":
					builder.appendln2("buf.writeFloat(").append(name).append(");");
					break;
				case "boolean":
					builder.appendln2("buf.writeBoolean(").append(name).append(");");
					break;
				case "String[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "int[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "short[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "byte[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "long[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "float[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "double[]":
					builder.appendln2("buf.writeArray(").append(name).append(");");
					break;
				case "directByte[]": {
					builder.appendln2("if(").append(name).append("!=null)");
					builder.appendln2("{");
					builder.appendln2("buf.writeByte(1);");
					builder.appendln2("buf.writeBytes(").append(name).append(");");
					builder.appendln2("}");
					builder.appendln2("else");
					builder.appendln2("{");
					builder.appendln2("buf.writeByte(0);");
					builder.appendln2("}");

					break;
				}
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

		}

		private void writeRef(IndentStringBuilder builder) {
			if (isRef() && refClassName == null)
				throw new RuntimeException("ref class name not specified. " + name);
			builder.appendln2("if(").append(name).append("==null) buf.writeByte(0);");
			builder.appendln2("else");
			builder.appendln2("{");
			builder.appendln2("buf.writeByte(1);");
			builder.appendln2(name).append(".write(buf);");
			builder.appendln2("}");

		}

		private void writeRefArray(IndentStringBuilder builder) {
			if (isRefArray() && refClassName == null)
				throw new RuntimeException("ref class name not specified.");
			if (refClassName.equals("IWritable")) {
				builder.appendln2("if(").append(name).append("==null) buf.writeShort(0);");
				builder.appendln2("else");
				builder.appendln2("{");
				builder.appendln2("buf.writeShort(").append(name).append(".length);");
				builder.appendln2("for(int i=0;i<").append(name).append(".length;i++)");
				builder.appendln2("{");
				builder.appendln2("if(").append(name).append("[i]==null) buf.writeByte(0);");
				builder.appendln2("else");
				builder.appendln2("{");
				builder.appendln2("buf.writeByte(game.DataFactory.getWritableType(").append(name).append("[i])")
						.append(");");
				builder.appendln2(name).append("[i].write(buf);");
				builder.appendln2("}");
				builder.appendln2("}");
				builder.appendln2("}");
			} else {
				builder.appendln2("buf.writeArray(").append(name).append(");");
			}

			// builder.append("\nif(").append(name).append("==null)
			// buf.writeShort(0);\n");
			// builder.append("else{\n");
			// builder.append("buf.writeShort(").append(name).append(".length));\n");
			// builder.append("for(int i=0,j<").append()
			// builder.append(name).append(".write(buf);\n");
			// builder.append("}\n");
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
		public int code;
		public boolean genRead, genWrite;
		public List<Field> fields = new ArrayList<Field>();
		// public boolean refDBObject;
		// public boolean refVOObject;

		public void parse(Element e) {
			String tag = e.getName();
			if (tag.equals("packet"))
				tagType = 0;
			else if (tag.equals("ref"))
				tagType = 1;

			if (tagType == 0) {// packet
				code = Integer.parseInt(e.getAttributeValue("id"));
				type = Integer.parseInt(e.getAttributeValue("type"));
				if (type == ClientServer) {
					// client to server packet
					packageName += ".client";
					clientPackets.packets.add(this);
				} else if (type == ServerClient) {
					// server to client packet
					packageName += ".server";
					clientPackets.packets.add(this);
				} else if (type == ServerServer) {
					// server to Server packet
					packageName += ".server";
					clientPackets.packets.add(this);
				} else if (type == ServerDBServer) {
					packageName += ".client";
					clientPackets.packets.add(this);
				} else if (type == DBServerServer) {
					packageName += ".server";
					clientPackets.packets.add(this);
				}
			} else if (tagType == 1) {// objects
				packageName += ".objects";
			}

			genRead = true;
			genWrite = true;

			for (Element child : e.getChildren()) {
				Field f = new Field();
				f.parse(child);
				fields.add(f);
			}
		}

		public IndentStringBuilder gen() {
			IndentStringBuilder builder = new IndentStringBuilder();
			builder.append("package ").append(packageName).append(";");
			builder.appendln2("import engine.net.*;");
			builder.appendln2("import generated.cgame.packets.objects.*;");
			builder.appendln2("");
			// 增加忽略warning代码生成
			builder.appendln2("@SuppressWarnings(\"unused\")");
			builder.appendln2("public class ").append(name).append(" extends ");
			if (tagType == 0) {
				builder.append("CPacket");
				builder.appendln2("{");
				builder.appendln2("public ").append(name).append("()");
				builder.appendln2("{");
				builder.appendln2("code=").append(code).append(";");
				builder.appendln2("}");
			} else if (tagType == 1) {
				builder.append("CValue");
				builder.appendln2("{");
			}
			builder.appendln2("");
			for (Field f : fields) {
				f.gen(builder);
			}
			builder.appendln2("");
			if (genRead) {
				builder.appendln2("public void read(engine.net.NativeBuffer buf)");
				builder.appendln2("{");
				for (Field f : fields) {
					f.genRead(builder);
				}
				builder.appendln2("}");

			}
			builder.appendln2("");
			if (genWrite) {
				builder.appendln2("public void write(engine.net.NativeBuffer buf)");
				builder.appendln2("{");
				for (Field f : fields) {
					f.genWrite(builder);
				}
				builder.appendln2("}");

			}

			builder.appendln2("}");
			return builder;
		}

		public void write(File root) {
			String relPath = packageName.replace('.', '/');
			if (!relPath.endsWith("/"))
				relPath += "/";
			relPath += name + ".java";
			File out = new File(root, relPath);
			JavaPacketGenerator.write(out, gen().toString());
		}

	}

	/**
	 * 用于生成 ClientPackets.java
	 * 
	 * @author cxz
	 *
	 */
	private static class ClientPackets {
		public String packageName;
		public String name;
		public List<PacketClass> packets = new ArrayList<PacketClass>();

		public void write(File root) {
			writePacket(root);
			writeDBPacket(root);
		}

		private void writeDBPacket(File root) {
			IndentStringBuilder builder = new IndentStringBuilder();
			builder.append("package ").append(packageName).append(";");
			builder.appendln2("import java.util.*;");
			builder.appendln2("import engine.net.*;");
			// builder.appendln2("import ").append(packageName)
			// .append(".client.*;");
			// builder.appendln2("import ").append(packageName)
			// .append(".server.*;");
			builder.appendln2("");
			builder.appendln2("public class ").append("DBClientPackets");
			builder.appendln2("{");
			builder.appendln2("private static final Map<Integer,CPacket> packets=new HashMap<Integer,CPacket>();");
			builder.appendln2("static");
			builder.appendln2("{");
			for (PacketClass pc : packets) {
				// 3.4用于与dbserver之间的�?�信
				if (pc.type == 3 || pc.type == 4) {
					builder.appendln2("packets.put(").append(pc.code).append(",new ").append(pc.name).append("());");
				}
			}
			builder.appendln2("}");
			builder.appendln2("");
			// gen get packet method
			builder.appendln2("public static CPacket get(engine.net.NativeBuffer buf)");
			builder.appendln2("{");
			builder.appendln2("int code=buf.readShort();");
			builder.appendln2("CPacket packet=packets.get(code);");
			builder.appendln2("if(packet==null) packet=new CPacket();");
			builder.appendln2("else");
			builder.appendln2("{");
			builder.appendln2("try");
			builder.appendln2("{");
			builder.appendln2("packet=(CPacket)packet.clone();");
			builder.appendln2("}");
			builder.appendln2("catch(Exception ex)");
			builder.appendln2("{");
			builder.appendln2("ex.printStackTrace();");
			builder.appendln2("}");
			builder.appendln2("}");
			builder.appendln2("packet.code=code;");
			builder.appendln2("packet.read(buf);");
			builder.appendln2("return packet;");
			builder.appendln2("}");
			builder.appendln2("}");
			String relPath = packageName.replace('.', '/');
			if (!relPath.endsWith("/"))
				relPath += "/";
			relPath += "DBClientPackets.java";
			File out = new File(root, relPath);
			JavaPacketGenerator.write(out, builder.toString());
		}

		private void writePacket(File root) {
			IndentStringBuilder builder = new IndentStringBuilder();
			builder.append("package ").append(packageName).append(";");
			builder.appendln2("import java.util.*;");
			builder.appendln2("import engine.net.*;");
			builder.appendln2("import ").append(packageName).append(".client.*;");
			builder.appendln2("import ").append(packageName).append(".server.*;");
			builder.appendln2("");
			builder.appendln2("public class ").append(name);
			builder.appendln2("{");
			builder.appendln2("private static final Map<Integer,CPacket> packets=new HashMap<Integer,CPacket>();\n");

			builder.appendln2("static");
			builder.appendln2("{");
			for (PacketClass pc : packets) {
				if (pc.type == 0 || pc.type == 2) {
					builder.appendln2("packets.put(").append(pc.code).append(",new ").append(pc.name).append("());");
				}
			}
			builder.appendln2("}");

			// gen get packet method
			builder.appendln2("public static CPacket get(engine.net.NativeBuffer buf)");
			builder.appendln2("{");
			builder.appendln2("int code=buf.readShort();");
			builder.appendln2("CPacket packet=packets.get(code);");
			builder.appendln2("if(packet==null) packet=new CPacket();");
			builder.appendln2("else");
			builder.appendln2("{");
			builder.appendln2("try");
			builder.appendln2("{");
			builder.appendln2("packet=(CPacket)packet.clone();");
			builder.appendln2("}");
			builder.appendln2("catch(Exception ex)");
			builder.appendln2("{");
			builder.appendln2("ex.printStackTrace();");
			builder.appendln2("}");
			builder.appendln2("}");
			builder.appendln2("packet.code=code;");
			builder.appendln2("packet.read(buf);");
			builder.appendln2("return packet;");
			builder.appendln2("}");
			builder.appendln2("}");

			String relPath = packageName.replace('.', '/');
			if (!relPath.endsWith("/"))
				relPath += "/";
			relPath += name + ".java";
			File out = new File(root, relPath);
			JavaPacketGenerator.write(out, builder.toString());
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
		new JavaPacketGenerator().run();
	}

	void run() {
		try {
			System.out.println("start to generate java server-client msg files...");
			Element root = Utility.readXML(packetXMLFile).getRootElement();

			String packageName = root.getAttributeValue("package");
			clientPackets.name = "ClientPackets";
			clientPackets.packageName = packageName;

			List<PacketClass> cList = new ArrayList<PacketClass>();
			List<PacketClass> sList = new ArrayList<PacketClass>();
			List<PacketClass> voList = new ArrayList<PacketClass>();

			Set<Integer> codeSet = new HashSet<>();
			for (Element e : root.getChildren()) {

				PacketClass pc = new PacketClass();
				pc.name = e.getAttributeValue("name");
				pc.packageName = packageName;
				pc.parse(e);
				if (pc.code != 0) {
					if (codeSet.contains(pc.code)) {
						throw new RuntimeException("PacketCode repeated:" + pc.code);
					}
					codeSet.add(pc.code);
				}

				pc.write(outputDir);
				if (pc.tagType == 0) {
					if (pc.type == ClientServer)
						cList.add(pc);
					else if (pc.type == ServerClient || pc.type == ServerServer)
						sList.add(pc);
				} else if (pc.tagType == 1) {
					voList.add(pc);
				}
			}

			clientPackets.write(outputDir);

			genCode(cList, sList, packageName);

			System.out.println("finish to generate java server-client msg files.");
			System.out.println("//");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void genCode(List<PacketClass> cList, List<PacketClass> sList, String packageName) {
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
		builder.append("package ").append(packageName).append(";");
		builder.appendln2("public interface Code");
		builder.appendln2("{");
		builder.appendln2("// ////////////////client packets//////////////");
		for (PacketClass pc : cList) {
			builder.appendln2("short ").append(pc.name).append(" = ").append(pc.code).append(";");
		}
		builder.appendln2("// ////////////////server packets//////////////");
		for (PacketClass pc : sList) {
			builder.appendln2("short ").append(pc.name).append(" = ").append(pc.code).append(";");
		}

		builder.appendln2("}");
		String relPath = packageName.replace('.', '/');
		if (!relPath.endsWith("/"))
			relPath += "/";
		relPath += "Code.java";
		File out = new File(outputDir, relPath);
		JavaPacketGenerator.write(out, builder.toString());
	}

}
