package engine.db;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom2.Element;

import engine.util.IOUtil;
import engine.util.IndentStringBuilder;
import engine.util.Utility;

public class DBPacketGener {

    private static final File packetXMLFile = new File(
	    new File("").getAbsolutePath() + "/src/main/java/engine/db/dbpacket.xml");
    private static final String packageName = "engine.db.packets";
    private static final File root = new File(new File("").getAbsolutePath() + "/src/main/java/");

    private static class Field {
	public String name;
	public String type;
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
	}

	public void gen(IndentStringBuilder builder) {
	    String cType = type;
	    if (type.equals("directByte[]"))
		cType = "byte[]";
	    builder.appendln2("public ").append(cType).append(" ").append(name).append(";");
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
	    default:
		throw new RuntimeException("unknown type:" + type);
	    }
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
		default:
		    throw new RuntimeException("unknown type:" + type);
		}
	    }

	}

    }

    private static class PacketClass {
	public int type;
	public String packageName;
	public String name;
	public int code;
	public boolean genRead, genWrite;
	public List<Field> fields = new ArrayList<Field>();

	public void parse(Element e) {
	    code = Integer.parseInt(e.getAttributeValue("id"));
	    type = Integer.parseInt(e.getAttributeValue("type"));
	    name = e.getAttributeValue("name");
	    if (type == 3) {
		packageName += ".client";
	    } else if (type == 4) {
		packageName += ".server";
	    }
	    genWrite = true;
	    genRead = true;

	    for (Element child : e.getChildren()) {
		Field f = new Field();
		f.parse(child);
		fields.add(f);
	    }
	}

	public IndentStringBuilder gen() {
	    IndentStringBuilder builder = new IndentStringBuilder();
	    builder.append("package ").append(packageName).append(";");
	    builder.appendln2("import engine.db.DBPacket;");
	    builder.appendln2("");
	    builder.appendln2("public class ").append(name).append(" extends ");
	    builder.append("DBPacket");
	    builder.appendln2("{");
	    builder.appendln2("public ").append(name).append("()");
	    builder.appendln2("{");
	    builder.appendln2("code=").append(code).append(";");
	    builder.appendln2("}");
	    builder.appendln2("");
	    for (Field f : fields) {
		f.gen(builder);
	    }
	    builder.appendln2("");
	    if (genRead) {
		builder.appendln2("public void read(engine.net.NativeBuffer buf)");
		builder.appendln2("{");
		builder.appendln2("id = buf.readLong();");
		for (Field f : fields) {
		    f.genRead(builder);
		}
		builder.appendln2("}");

	    }
	    builder.appendln2("");
	    if (genWrite) {
		builder.appendln2("public void write(engine.net.NativeBuffer buf)");
		builder.appendln2("{");
		builder.appendln2("buf.writeLong(id);");
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
	    DBPacketGener.write(out, gen().toString());
	}

    }

    private static class ClientPackets {
	public String packageName;
	public String name;
	public List<PacketClass> packets = new ArrayList<PacketClass>();

	public void write(File root) {
	    writeDBPacket(root);
	}

	private void writeDBPacket(File root) {
	    IndentStringBuilder builder = new IndentStringBuilder();
	    builder.append("package ").append(packageName).append(";");
	    builder.appendln2("import java.util.*;");
	    builder.appendln2("import engine.db.DBPacket;");
	    builder.appendln2("import ").append(packageName).append(".client.*;");
	    builder.appendln2("import ").append(packageName).append(".server.*;");
	    builder.appendln2("");
	    builder.appendln2("public class ").append(name);
	    builder.appendln2("{");
	    builder.appendln2("private static final Map<Integer,DBPacket> packets=new HashMap<>();");
	    builder.appendln2("static");
	    builder.appendln2("{");
	    for (PacketClass pc : packets) {
		if (pc.type == 3 || pc.type == 4) {
		    builder.appendln2("packets.put(").append(pc.code).append(",new ").append(pc.name).append("());");
		}
	    }
	    builder.appendln2("}");
	    builder.appendln2("");
	    // gen get packet method
	    builder.appendln2("public static DBPacket get(engine.net.NativeBuffer buf)");
	    builder.appendln2("{");
	    builder.appendln2("int code=buf.readShort();");
	    builder.appendln2("DBPacket packet=packets.get(code);");
	    builder.appendln2("if(packet==null) packet=new DBPacket();");
	    builder.appendln2("else");
	    builder.appendln2("{");
	    builder.appendln2("try");
	    builder.appendln2("{");
	    builder.appendln2("packet=(DBPacket)packet.clone();");
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
	    DBPacketGener.write(out, builder.toString());
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
	IOUtil.deleteDirectory(new File(root.getAbsolutePath() + packageName.replace(".", "/")));
	new DBPacketGener().run();
    }

    void run() {
	try {
	    Element xmlroot = Utility.readXML(packetXMLFile).getRootElement();

	    clientPackets.name = "DBClientPackets";
	    clientPackets.packageName = packageName;

	    List<PacketClass> cList = new ArrayList<PacketClass>();
	    List<PacketClass> sList = new ArrayList<PacketClass>();

	    Set<Integer> codeSet = new HashSet<>();
	    for (Element e : xmlroot.getChildren()) {

		PacketClass pc = new PacketClass();
		pc.name = e.getAttributeValue("name");
		pc.packageName = packageName;
		pc.parse(e);
		clientPackets.packets.add(pc);
		if (pc.code != 0) {
		    if (codeSet.contains(pc.code)) {
			throw new RuntimeException("PacketCode repeated:" + pc.code);
		    }
		    codeSet.add(pc.code);
		}

		pc.write(root);
		if (pc.type == 3) {
		    cList.add(pc);
		} else if (pc.type == 4) {
		    sList.add(pc);
		}
	    }

	    clientPackets.write(root);

	    genCode(cList, sList);

	} catch (Exception e) {
	    e.printStackTrace();
	}

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
	builder.append("package ").append(packageName).append(";");
	builder.appendln2("public interface DBCode");
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
	relPath += "DBCode.java";
	File out = new File(root, relPath);
	DBPacketGener.write(out, builder.toString());
    }

}