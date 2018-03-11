package engine.loader;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * 从指定http服务加载配置文件，并解压到特定目录
 * @author xjf
 */
public class VersionLoader {
	/**
	 * 启动版本更新，从指定目录获取到更新的配置文件，并解压到本地对应的目录下。 整个过程将阻塞当前线程，直到所有更新完成
	 * @param properties
	 */
	public static void start(Properties properties) {
		String httpHost = properties.getProperty("version.host");

	}

	/**
	 * 取指定文件
	 * @param url
	 */
	private InputStream getFile(String url) {
		return null;
	}

	/**
	 * 将指定文件解压，并强制覆盖
	 * @param file
	 */
	private void unzip(File file) {

	}
}
