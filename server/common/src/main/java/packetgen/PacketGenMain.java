package packetgen;

import java.io.File;

import engine.packetgen.impl.CPPPacketGenerator;
import engine.packetgen.impl.DBObjectWriterGenerator;
import engine.packetgen.impl.JavaPacketGenerator;
import engine.util.IOUtil;

public class PacketGenMain {
	// 修改路径
	public static File packetXMLFile;
	public static File javaOutputDir;
	public static File javaOutputDir2;
	public static File srcDir;
	public static File asOutputDir;
	public static File cppHOutputDir; // c++使用的头文件位置
	public static File cppCppOutputDir; // c++使用的cpp文件的位�?
	public static File errorCodeFile;
	public static File errorCodeOutFile;

	public static String[] dbobjs = { "dbobjects.gamedb", "dbobjects.globaldb", "dbobjects.gmdb", "dbobjects.logdb","dbobjects.nickdb" };

	private static String defaultIncludeFile = "Tank.h";

	static {
		String path = new File("").getAbsolutePath();
		srcDir = new File(path + "/src/main/java");
		packetXMLFile = new File(srcDir + "/packetgen/packet.xml");
		javaOutputDir = new File(srcDir + "/");
		javaOutputDir2 = new File(srcDir + "/generated");
		cppHOutputDir = new File(path + "./../../../Client/Source/Tank/network/packet");
		cppCppOutputDir = new File(path + "./../../../Client/Source/Tank/network/packet");
		System.out.println(cppHOutputDir);
		asOutputDir = new File(srcDir + "/generated/as");
		errorCodeFile = new File(srcDir + "/common/ErrorCode.java");
		errorCodeOutFile = new File(path + "./../../../Client/Source/Tank/network/packet/ErrorCode.h");
	}

	public static void main(String[] args) {
		System.out.println("start to generate msgs...");
		System.out.println("//");

		IOUtil.deleteDirectory(javaOutputDir2);
		IOUtil.deleteDirectory(cppHOutputDir);
		IOUtil.deleteDirectory(asOutputDir);

		JavaPacketGenerator.outputDir = javaOutputDir;
		JavaPacketGenerator.packetXMLFile = packetXMLFile;
		JavaPacketGenerator.main(null);

		// ASPacketGenerator.outputDir = asOutputDir;
		// ASPacketGenerator.packetXMLFile = packetXMLFile;
		// ASPacketGenerator.main(null);

		CPPPacketGenerator.outputDir = cppHOutputDir;
		CPPPacketGenerator.cppoutputDir = cppCppOutputDir;
		CPPPacketGenerator.packetXMLFile = packetXMLFile;
		CPPPacketGenerator.errorCodeOutFile = errorCodeOutFile;
		CPPPacketGenerator.errorCodeFile = errorCodeFile;
		String[] params = { defaultIncludeFile };
		CPPPacketGenerator.main(params);

		DBObjectWriterGenerator.main(dbobjs);

		System.out.println("all msgs generate successfull");
	}
}
