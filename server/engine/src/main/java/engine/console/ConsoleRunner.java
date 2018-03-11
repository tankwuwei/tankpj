package engine.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import engine.log.LogUtil;


public class ConsoleRunner implements Runnable{

private Console console;
	
	public ConsoleRunner(Console console) {
		this.console = console;
	}

	@Override
	public void run() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			while(!console.isStop()) {
				String line = in.readLine();
				String name = CommandHelper.getName(line);
				Command command = console.getCommand(name);
				if (command == null) {
					FormattingTuple message = MessageFormatter.format("指令[{}]不存在", name);
//					if (LogUtil.isDebugEnabled()) {
//						LogUtil.debug(message.getMessage());
//					}
					System.err.println(message.getMessage());
					continue;
				}
				String[] arguments = CommandHelper.getArguments(line);
				try {
					command.execute(arguments);
					FormattingTuple message = MessageFormatter.format("[{}]指令执行完成", command.name());
					System.out.println(message.getMessage());
//				} catch (ArgumentException e) {
//					FormattingTuple message = MessageFormatter.format("指令[{}]参数{}异常", name, arguments);
//					if (logger.isDebugEnabled()) {
//						logger.debug(message.getMessage(), e);
//					}
//					System.err.println(message.getMessage());
//				} catch (ExecuteException e) {
//					FormattingTuple message = MessageFormatter.format("指令[{}:{}]执行异常", new Object[]{name, arguments, e});
//					if (logger.isDebugEnabled()) {
//						logger.debug(message.getMessage(), e);
//					}
//					System.err.println(message.getMessage());
//				} catch (CommandException e) {
//					FormattingTuple message = MessageFormatter.format("指令[{}:{}]未知异常", new Object[]{name, arguments, e});
//					if (logger.isDebugEnabled()) {
//						logger.debug(message.getMessage(), e);
//					}
//					System.err.println(message.getMessage());
				}catch(Exception e){
					LogUtil.error("命令错误", e);
				}
			}
			
			// 修复在 JDK 6 环境下，出现 JDWP exit error AGENT_ERROR_NO_JNI_ENV(183) 的问题
			System.exit(0);
		} catch (IOException e) {
			LogUtil.error("获取命令行输入时出现异常", e);
		}
	}

}
