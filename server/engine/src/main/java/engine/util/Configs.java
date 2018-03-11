package engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import engine.log.LogUtil;
import java.io.*;
import java.util.Properties;

/**
 * @classname Configs
 * @Description ������������������
 * @author cxz
 *
 */

public class Configs {

	/**
	 * ���������������������������������������
	 * 
	 * @param filename���������������������������
	 */
	public void AddConfigFile(String filename) {
		Properties properties = IOUtil.loadProperties(filename);
		if (properties != null) {
			allProperties.add(0, properties);
		}
	}

	public void AddFullPathConfigFile(String filename) {
		try {
			Properties p = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			p.load(new InputStreamReader(in, "utf-8"));
			allProperties.add(0, p);
		} catch (Exception e) {
			LogUtil.debug(e);
		}
	}

	/**
	 * 读取不确定有的配置文件
	 * 
	 * @param filename
	 */
	public void AddUncertaintyConfigFile(String filename) {
		try {
			File file = new File(filename);
			if (!file.exists()) {
				return;
			}
			Properties p = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			p.load(new InputStreamReader(in, "utf-8"));
			// p.load(new FileReader(System.getProperty("user.dir") +
			// File.separator + filename));
			allProperties.add(0, p);
		} catch (Exception e) {
			LogUtil.debug(e);
		}
	}

	/**
	 * ���������int���������������
	 * 
	 * @param config
	 * @return
	 */
	public int GetIntConfig(String configname, int defaultvalue) {
		String strPro;
		for (Properties pro : allProperties) {
			strPro = pro.getProperty(configname);
			if (strPro != null) {
				return Integer.parseInt(strPro);
			}
		}
		return defaultvalue;
	}

	/**
	 * ���������������������������������������������
	 * 
	 * @param configname
	 * @return
	 */
	public String GetStrConfig(String configname, String defalutstr) {
		String strPro;
		for (Properties pro : allProperties) {
			strPro = pro.getProperty(configname);
			if (strPro != null) {
				return strPro;
			}
		}
		return defalutstr;
	}

	/**
	 * ���������bool���������������
	 * 
	 * @param configname
	 * @param defaultvalue
	 * @return
	 */
	public boolean GetBoolConfig(String configname, boolean defaultvalue) {
		String strPro;
		for (Properties pro : allProperties) {
			strPro = pro.getProperty(configname);
			if (strPro != null) {
				return Integer.parseInt(strPro) > 0;
			}
		}
		return defaultvalue;
	}

	List<Properties> allProperties = new ArrayList<>();
}
