package engine.packetgen.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import engine.annotation.NeedWrite;
import engine.annotation.NotWrite;
import engine.log.LogUtil;
import engine.net.CValue;
import engine.util.IOUtil;

public class DBObjectWriterGenerator {
	String objectspath = "";

	public static void main(String[] args) {
		new DBObjectWriterGenerator().run(args);
	}

	private String GEN_START = "/////////// CODE_GEN_START//////////";
	private String GEN_END = "///////////// CODE_GEN_END////////////";

	public void run(String[] args) {
		String path = System.getProperty("user.dir") + "/src/main/java";
		for (String objectspath : args) {
			String child = objectspath.replaceAll("\\.", "/");
			File dbObjectFile = new File(path, child);
			File[] files = dbObjectFile.listFiles();
			if (files == null) {
				continue;
			}
			for (File f : files) {
				do1(f, objectspath);
			}
		}
	}

	private void do1(File f, String objectspath) {

		String className = f.getName();
		String simpleName = className.substring(0, className.lastIndexOf("."));
		className = objectspath + "." + simpleName;
		System.out.println(className);
		StringBuilder newContent = genNewContent(className);
		String text = IOUtil.readText(f);
		StringBuilder builder = new StringBuilder(text);

		// int a = builder.indexOf(GEN_START, 0);
		// int b = builder.indexOf(GEN_START, a+1);

		int lastIndex = builder.indexOf(GEN_START);
		if (lastIndex == -1) {
			lastIndex = builder.lastIndexOf("}");
			builder.insert(lastIndex, "\n");
			lastIndex = builder.lastIndexOf("}");
			builder.insert(lastIndex, GEN_START);
			lastIndex = builder.indexOf(GEN_START) + GEN_START.length();
			builder.insert(lastIndex, "\n");
			builder.insert(lastIndex + 1, GEN_END + "\n");
		}
		// remove current content between START and END
		int startIndex = builder.indexOf(GEN_START) + GEN_START.length();
		int endIndex = builder.lastIndexOf(GEN_END);
		builder.delete(startIndex, endIndex);

		builder.insert(startIndex, "\n" + newContent);
		endIndex = builder.lastIndexOf(GEN_END);
		if (!"\n\n".equals(builder.substring(endIndex - 4, endIndex))) {
			builder.insert(endIndex, "\n\n");
		}

		IOUtil.writeText(f, builder.toString());

		// writeCSharpDBObject(className, simpleName);
	}

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

	public StringBuilder append(String v) {
		return content.append(v);
	}

	// private void writeCSharpDBObject(String className, String shortName) {
	// try {
	// Class<?> c = Class.forName(className);
	// content.setLength(0);
	// appendln("package com.rpc");
	// appendln("{");
	// appendln("public class ").append(shortName).append(" extends CValue");
	// appendln("{");
	//
	// for (java.lang.reflect.Field f : c.getDeclaredFields()) {
	// String name = f.getName();
	// int mod = f.getModifiers();
	// if (Modifier.isStatic(mod))
	// continue;
	// if (Modifier.isTransient(mod)) {
	// if (f.getAnnotation(NeedWrite.class) == null)
	// continue;
	// } else {
	// if (f.getAnnotation(NotWrite.class) != null)
	// continue;
	// }
	// Class<?> type = f.getType();
	// if (type == int.class) {
	// appendln("public var ").append(name).append(": int ;");
	// } else if (type == short.class) {
	// appendln("public var ").append(name).append(": int ;");
	// } else if (type == byte.class) {
	// appendln("public var ").append(name).append(": int ;");
	// } else if (type == long.class) {
	// appendln("public var ").append(name).append(": Number ;");
	// } else if (type == float.class) {
	// appendln("public var ").append(name).append(": Number ;");
	// } else if (type == double.class) {
	// appendln("public var ").append(name).append(": Number ;");
	// } else if (type == boolean.class) {
	// appendln("public var ").append(name).append(": Boolean ;");
	// } else if (type == String.class) {
	// appendln("public var ").append(name).append(": String ;");
	// } else if (type == int[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == short[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == byte[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == long[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == float[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == double[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type == String[].class) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (type.isArray()) {
	// appendln("public var ").append(name).append(": Array;");
	// } else if (CValue.class.isAssignableFrom(type)) {
	// appendln("public var ").append(name).append(":
	// ").append(type.getSimpleName()).append(";");
	// } else {
	// throw new RuntimeException(
	// "unknown db object field:" + name + " type:" + type + " :classname:" +
	// className);
	// }
	// }
	//
	// appendln("override public function read(buf:NativeBuffer):void");
	// appendln("{");
	// for (java.lang.reflect.Field f : c.getDeclaredFields()) {
	// String name = f.getName();
	// int mod = f.getModifiers();
	// if (Modifier.isStatic(mod))
	// continue;
	// if (Modifier.isTransient(mod)) {
	// if (f.getAnnotation(NeedWrite.class) == null)
	// continue;
	// } else {
	// if (f.getAnnotation(NotWrite.class) != null)
	// continue;
	// }
	// Class<?> type = f.getType();
	//
	// if (type == int.class) {
	// appendln(name).append("=buf.readInt();");
	// } else if (type == short.class) {
	// appendln(name).append("=buf.readShort();");
	// } else if (type == byte.class) {
	// appendln(name).append("=buf.readByte();");
	// } else if (type == long.class) {
	// appendln(name).append("=buf.readLong();");
	// } else if (type == float.class) {
	// appendln(name).append("=buf.readFloat();");
	// } else if (type == double.class) {
	// appendln(name).append("=buf.readDouble();");
	// } else if (type == boolean.class) {
	// appendln(name).append("=buf.readBoolean();");
	// } else if (type == String.class) {
	// appendln(name).append("=buf.readUTF();");
	// } else if (type == int[].class) {
	// appendln(name).append("=buf.readIntArray();");
	// } else if (type == short[].class) {
	// appendln(name).append("=buf.readShortArray();");
	// } else if (type == byte[].class) {
	// appendln(name).append("=buf.readByteArray();");
	// } else if (type == long[].class) {
	// appendln(name).append("=buf.readLongArray();");
	// } else if (type == float[].class) {
	// appendln(name).append("=buf.readFloatArray();");
	// } else if (type == double[].class) {
	// appendln(name).append("=buf.readDoubleArray();");
	// } else if (type == String[].class) {
	// appendln(name).append("=buf.readUTFArray();");
	// } else if (type.isArray()) {
	// String classNamet = type.getComponentType().getSimpleName();
	// String lengthName = name + "Length";
	// String varFor = "index" + name;
	// appendln(name + "=[];");
	// appendln("var " + lengthName + ":int = buf.readShort();");
	// appendln("for(var " + varFor + ":int=0;" + varFor + "<" + lengthName +
	// ";" + varFor + "++)");
	// appendln("{");
	// appendln("if(buf.readByte()==1)");
	// appendln("{");
	// String varName = name + "Component";
	// appendln("var " + varName + ":" + classNamet + " = new " + classNamet +
	// "();");
	// appendln(varName + ".read(buf);");
	// appendln(name + "[" + varFor + "]=" + varName + ";");
	// appendln("}");
	// appendln("}");
	// } else if (CValue.class.isAssignableFrom(type)) {
	// appendln("if(buf.readByte()==1)");
	// appendln("{");
	// appendln(name).append("= new
	// ").append(type.getSimpleName()).append("();");
	// appendln(name).append(".read(buf);");
	// appendln("}");
	// appendln("else");
	// appendln("{");
	// appendln(name).append("=null;");
	// appendln("}");
	// } else {
	// throw new RuntimeException(
	// "unknown db object field:" + name + " type:" + type + " :classname:" +
	// className);
	// }
	// }
	// appendln("}");
	// appendln("}");
	// appendln("}");
	// IOUtil.writeText(new File(PacketGenMain.asOutputDir, "com/rpc/" +
	// shortName + ".as"), content.toString());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	private StringBuilder genNewContent(String className) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("\npublic void write(engine.net.NativeBuffer buf){\n");
			Class<?> c = Class.forName(className);
			// if(!IWritable.class.isAssignableFrom(c)) return null;
			Field[] fs = c.getDeclaredFields();
			for (java.lang.reflect.Field f : fs) {
				String name = f.getName();
				int mod = f.getModifiers();
				if (Modifier.isStatic(mod))
					continue;
				if (Modifier.isTransient(mod)) {
					if (f.getAnnotation(NeedWrite.class) == null)
						continue;
				} else {
					if (f.getAnnotation(NotWrite.class) != null)
						continue;
				}

				Class<?> type = f.getType();
				if (type == int.class) {
					builder.append("buf.writeInt(").append(name).append(");\n");
				} else if (type == short.class) {
					builder.append("buf.writeShort(").append(name).append(");\n");
				} else if (type == byte.class) {
					builder.append("buf.writeByte(").append(name).append(");\n");
				} else if (type == long.class) {
					builder.append("buf.writeLong(").append(name).append(");\n");
				} else if (type == float.class) {
					builder.append("buf.writeFloat(").append(name).append(");\n");
				} else if (type == double.class) {
					builder.append("buf.writeDouble(").append(name).append(");\n");
				} else if (type == boolean.class) {
					builder.append("buf.writeBoolean(").append(name).append(");\n");
				} else if (type == String.class) {
					builder.append("buf.writeUTF(").append(name).append(");\n");
				} else if (type == int[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == short[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == byte[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == long[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == float[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == double[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == String[].class) {
					builder.append("buf.writeArray(").append(name).append(");\n");
				} else if (type == int[][].class) {
					builder.append("buf.writeTowArray(").append(name).append(");\n");
				} else {
					if (type.isArray()) {
						builder.append("buf.writeArray(").append(name).append(");\n");
					} else if (CValue.class.isAssignableFrom(type)) {
						builder.append("buf.write(").append(name).append(");\n");
					}
				}
			}
			builder.append("}\n");

			builder.append("\npublic void read(engine.net.NativeBuffer buf){\n");
			// if(!IWritable.class.isAssignableFrom(c)) return null;
			for (java.lang.reflect.Field f : fs) {
				String name = f.getName();
				int mod = f.getModifiers();
				if (Modifier.isStatic(mod))
					continue;
				if (Modifier.isTransient(mod)) {
					if (f.getAnnotation(NeedWrite.class) == null)
						continue;
				} else {
					if (f.getAnnotation(NotWrite.class) != null)
						continue;
				}

				Class<?> type = f.getType();

				if (type == int.class) {
					builder.append(name).append("=buf.readInt();\n");
				} else if (type == short.class) {
					builder.append(name).append("=buf.readShort();\n");
				} else if (type == byte.class) {
					builder.append(name).append("=buf.readByte();\n");
				} else if (type == long.class) {
					builder.append(name).append("=buf.readLong();\n");
				} else if (type == float.class) {
					builder.append(name).append("=buf.readFloat();\n");
				} else if (type == double.class) {
					builder.append(name).append("=buf.readDouble();\n");
				} else if (type == boolean.class) {
					builder.append(name).append("=buf.readBoolean();\n");
				} else if (type == String.class) {
					builder.append(name).append("=buf.readUTF();\n");
				} else if (type == int[].class) {
					builder.append(name).append("=buf.readIntArray();\n");
				} else if (type == short[].class) {
					builder.append(name).append("=buf.readShortArray();\n");
				} else if (type == byte[].class) {
					builder.append(name).append("=buf.readByteArray();\n");
				} else if (type == long[].class) {
					builder.append(name).append("=buf.readLongArray();\n");
				} else if (type == float[].class) {
					builder.append(name).append("=buf.readFloatArray();\n");
				} else if (type == String[].class) {
					builder.append(name).append("=buf.readUTFArray();\n");
				} else if (type == double[].class) {
					builder.append(name).append("=buf.readDoubleArray();\n");
				} else if (type == int[][].class) {
					builder.append("=buf.readIntTwoArray();\n");
				} else if (type.isArray()) {
					builder.append(name).append("=(").append(type.getSimpleName()).append(")")
							.append("buf.readArray(" + type.getComponentType().getSimpleName() + ".class);\n");
				} else if (CValue.class.isAssignableFrom(type)) {
					builder.append(name).append("=(").append(type.getSimpleName()).append(")")
							.append("buf.read(" + type.getSimpleName() + ".class);\n");
				} else {

					throw new RuntimeException("unknown db object field:" + name + " type:" + type);
				}

			}
			builder.append("}\n");

			return builder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
