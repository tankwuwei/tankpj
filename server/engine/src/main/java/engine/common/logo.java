package engine.common;

import java.util.Properties;

public class logo {
    public static void PrintLogo(String server, String version, String localaddress) {
	Properties p = System.getProperties();

	System.out.println("/////////////////////////////////////////////////////////////////////////");
	System.out.println("");
	System.out.println("      /___  __/    / ___ __/    /__   __/");
	System.out.println("         / /      / /             / /");
	System.out.println("        / /      / /__ __/       / /");
	System.out.println("       / /      / /             / /");
	System.out.println("   //_/ /      / /__ __/       /_/          Game System");
	System.out.println("                                            Running in " + p.getProperty("os.name") + "("
		+ p.getProperty("os.arch") + " " + p.getProperty("os.version") + ")" + "  " + localaddress);
	System.out.println(
		"                                            " + server + "  version:" + version);
	System.out.println("");
	System.out.println("/////////////////////////////////////////////////////////////////////////");
    }
}
