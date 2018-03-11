package engine.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.internal.util.ClassLoaderHelper;

import engine.log.LogUtil;

public class IOUtil {

	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = ClassLoaderHelper.getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = Environment.class.getResourceAsStream(resource);
		}
		if (stream == null) {
			stream = Environment.class.getClassLoader().getResourceAsStream(stripped);
		}
		if (stream == null) {
			throw new HibernateException(resource + " not found");
		}
		return stream;
	}

	public static void writeByteArray(String file, byte[] data) {
		try {
			File f = new File(file);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdir();
			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			LogUtil.error("写文件异常", e);
		}
	}

	public static void writeText(File f, String content) {
		try {
			f.getParentFile().mkdirs();
			FileWriter writer = new FileWriter(f);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeText(File f, String content, String charset) {
		BufferedWriter writer = null;
		try {
			f.getParentFile().mkdirs();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), charset));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readText(File f) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saveClose(reader);
		}
		return null;
	}

	public static String readText(File f, String charset) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				builder.append(line).append("\n");
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saveClose(br);
		}
		return null;
	}
	
	public static BufferedReader readTextBuffer(String file, String charset) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file)), charset));
			return br;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//saveClose(br);
		}
		return null;
	}
	
	

	public static void saveClose(Reader r) {
		try {
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveClose(InputStream is) {
		try {
			if (is != null)
				is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Properties loadProperties(File path) {
		FileReader reader = null;
		try {
			reader = new FileReader(path);
			Properties p = new Properties();
			p.load(reader);
			return p;
		} catch (Exception e) {
			LogUtil.error(e);
		} finally {
			saveClose(reader);
		}
		return null;
	}

	public static Properties loadProperties2(String path) throws Exception {
		InputStream is = null;
		try {
			is = getResourceAsStream(path);
			Properties p = new Properties();
			p.load(is);
			return p;
		} catch (Exception e) {
			LogUtil.warn(e);
			return null;
		} finally {
			saveClose(is);
		}
	}

	public static Properties loadProperties(String path) {
		try {
			return loadProperties2(path);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return null;
	}

	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (path.delete());
	}

	public static boolean deleteDirectory(File path, String excludeType) {
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						if (files[i].getName().contains(excludeType))
							continue;
						deleteDirectory(files[i]);
					} else {
						files[i].delete();
					}
				}
			}
		}
		return (path.delete());
	}
}
