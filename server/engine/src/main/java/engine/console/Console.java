package engine.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import engine.log.LogUtil;

/**
 * 控制台对象
 * @author xjf
 *
 */
public class Console {

	private Command cmdStop = new Command(){

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return "stop";
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return "停止控制台";
		}

		@Override
		public void execute(String[] args) throws CommandException {
			// TODO Auto-generated method stub
			stop();
		}
		
	};
	
	private Command cmdList = new Command(){
		@Override
		public String name() {
			// TODO Auto-generated method stub
			return "list";
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return "列出所有控制台指令";
		}

		@Override
		public void execute(String[] args) throws CommandException {
			for (Entry<String, Command> entry:commands.entrySet()){
				Command c = entry.getValue();
				System.out.println(c.name()+"\t:\t" + c.description());
			}
		}
	};
	
	private static Console instance;
	private static Map<String, Command> commands;
	
	private Console(){
		commands = new HashMap<String, Command>();
		registCommand(cmdStop);
		registCommand(cmdList);
		start();
	}
	
	public static void initConsole(){
		instance = new Console();
	}
	
	public static void registCommand(Command cmd){
		if(commands == null){
			throw new IllegalStateException("控制台未初始化");
		}
		commands.put(cmd.name(), cmd);
	}
	
	private void stop(){
		System.exit(0);
	}

	private void start(){
		new Thread(new ConsoleRunner(this), "控制台输入线程").start();
		LogUtil.info("控制台启动");
	}
	
	public boolean isStop() {
		return false;
	}

	public Command getCommand(String name) {
		return commands.get(name);
	}
	
}
