package engine.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import engine.log.LogUtil;

public class BeanFactory {
	private static ApplicationContext ctx;
	
	public static void init(){
		ctx = new ClassPathXmlApplicationContext("config/newContext.xml");
		LogUtil.debug("BeanFactory inited !");
	}
	/**
	 * 得到bean对象，所有bean实例都统一从这里获得，在创建的时候Spring会自动未其注册相关组件
	 * @param c
	 * @return
	 */
	public static <T> T getBean(Class<T> c){
		return ctx.getBean(c);
	}
	
	public static <T> T getBean(Class<T> c, Object... args) {
		return ctx.getBean(c, args);
	}
}
