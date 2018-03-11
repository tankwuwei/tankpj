package engine.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import engine.log.LogUtil;
import engine.net.CPacket;

/**
 * @ClassName: Handler
 * @Description: 消息包处理类,功能对应的管理类需extends此类 并重写handle方法
 * @author jichang.yan
 * @date 2016年5月17日 下午1:43:58
 *
 */
public abstract class Handler {

	private static List<Handler> handlers = new ArrayList<>();

    public static String errInfo(Exception e) {  
        StringWriter sw = null;  
        PrintWriter pw = null;  
        try {  
            sw = new StringWriter();  
            pw = new PrintWriter(sw);  
            // 将出错的栈信息输出到printWriter中  
            e.printStackTrace(pw);  
            pw.flush();  
            sw.flush();  
        } finally {  
            if (sw != null) {  
                try {  
                    sw.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (pw != null) {  
                pw.close();  
            }  
        }  
        return sw.toString();  
    }  
    
	/** 消息包处理类注册, 启动之后调用 */
	@PostConstruct
	private void initial() {
		handlers.add(this);
	}

	/** 消息派发入口 */
	public static void delegate(CPacket packet) {
		for (Handler handler : handlers) {
			try {
				handler.handle(packet);
			} catch (Exception e) {
				LogUtil.error("消息处理捕获异常"+Handler.errInfo(e));
			}
		
		}
	}

	/** 消息包派发方法, 功能handler实现需重写此方法 */
	abstract protected void handle(CPacket packet);
}
