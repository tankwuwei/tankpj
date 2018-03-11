package engine.console;
/**
 * 控制台接口
 * @author xjf
 *
 */
public interface Command {
	/**
	 * 命令名
	 * @return
	 */
	String name();
	/**
	 * 命令说明
	 * @return
	 */
	String description();
	/**
	 * 执行命令
	 * @param args
	 * @throws CommandException
	 */
	void execute(String[] args) throws CommandException;
}
