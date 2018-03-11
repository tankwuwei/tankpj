package engine.console;
/**
 * 控制台异常
 * @author xjf
 *
 */
public abstract class CommandException extends Exception{
	public CommandException(){
		super();
	}
	
	public CommandException(String message, Throwable cause){
		super(message, cause);
	}
	
	public CommandException(String message){
		super(message);
	}
	
	public CommandException(Throwable cause){
		super(cause);
	}
}
