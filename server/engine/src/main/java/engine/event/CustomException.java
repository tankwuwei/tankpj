package engine.event;

/**
 * 项目的自定义异常，每个自定义都有一个唯一的name，唯一的code
 * @author xjf
 */
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3348596801429424359L;
	public String name;
	public short code;

	public CustomException(String name, short code) {
		super();
		this.name = name;
		this.code = code;
	}

	public CustomException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CustomException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public CustomException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
