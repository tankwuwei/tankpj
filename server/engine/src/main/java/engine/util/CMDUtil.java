package engine.util;

import java.util.Scanner;

public class CMDUtil {

	private static Scanner in;
	private static Process process;

	public static void main(String... args) {
		tasklist();
		int pid = getPIDbywindowtitle("gameserver");
		System.err.println(pid);
		System.err.println(killPID(pid));
	}

	public static void tasklist() {
		try {
			process = Runtime.getRuntime().exec("tasklist -v /fo table");
			in = new Scanner(process.getInputStream(), "gbk");
			while (in.hasNextLine()) {
				String str = in.nextLine();
				System.err.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			process.destroy();

		}
	}

	public static int getPIDbywindowtitle(String windowtitle) {
		try {
			process = Runtime.getRuntime().exec("tasklist /fo csv /fi \" windowtitle eq " + windowtitle + "\"");
			in = new Scanner(process.getInputStream(), "gbk");
			int i = 0;
			while (in.hasNextLine()) {
				String str = in.nextLine();
				if (i == 1)
					return Integer.parseInt(str.split(",")[1].replaceAll("\"", ""));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			process.destroy();
		}
		return -1;
	}

	public static int killPID(int pid) {
		try {
			process = Runtime.getRuntime().exec("taskkill /f /pid " + pid);
			in = new Scanner(process.getInputStream(), "gbk");
			while (in.hasNextLine()) {
				String str = in.nextLine();
				System.err.println(str);
				return pid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
			process.destroy();
		}
		return -1;
	}

	public static void runBAT(String filepath) {
		try {
			Runtime.getRuntime().exec(filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}