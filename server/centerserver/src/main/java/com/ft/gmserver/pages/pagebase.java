package com.ft.gmserver.pages;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.ft.gmserver.CenterServer;

import engine.log.LogUtil;

public abstract class pagebase {
	protected Properties properties = new Properties();
	protected VelocityContext context = new VelocityContext();
	protected VelocityEngine velocityEngine = new VelocityEngine();
	protected StringWriter sw = new StringWriter();

	protected int pageSize = 20;// 分页，每页显示多少

	protected pagebase() {
		// properties.setProperty("resource.loader", "class");
		// properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		properties.setProperty("resource.loader", "file");
		properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

		properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

		// css\js\image等等静态资源文件服务器URI
		context.put("fileserver", CenterServer.fileserver);

		try {
			velocityEngine.init(properties);
		} catch (Exception e) {
			LogUtil.warn(e);
		}
	}

	public abstract String content();

	protected String parsetime(String s) {
		return s.replace("T", " ").replace("%3A", ":");
	}

	protected String parsetime2(String s) {
		return s.replace(" ", "T");
	}

	protected String limit = " LIMIT.^*^."; // limit分隔符
}
