package engine.net;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * LengthFieldBasedFrameDecoder构造函数， 
 * 第一个参数为信息最大长度，超过这个长度回报异常，
 * 第二参数为长度属性的起始（偏移）位，我们的协议中长度是0到第3个字节，所以这里写0， 
 * 第三个参数为“长度属性”的长度，我们是4个字节，所以写4，
 * 第四个参数为长度调节值，在总长被定义为包含包头长度时，修正信息长度，
 * 第五个参数为跳过的字节数，根据需要我们跳过前4个字节，以便接收端直接接受到不含“长度属性”的内容。
 */
public class IntegerFieldDecoder extends LengthFieldBasedFrameDecoder {
	public static final int SHORT_LENGTH = 1024;

	public static final int NORMAL_LENGTH = 4096;

	public static final int MAX_LENGTH = 32767;

	public IntegerFieldDecoder() {
		super(SHORT_LENGTH, 0, 4, 0, 4);
	}

	public IntegerFieldDecoder(int maxLen) {
		super(maxLen, 0, 4, 0, 4);
	}

	// protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws
	// Exception {
	//// System.err.println("decoder receive data:" + "
	// time="+DateUtils.date2String(new Date(), DateUtils.PATTERN_DATE_TIME) +
	// "."+(System.currentTimeMillis()%1000));
	// return super.decode(ctx, in);
	// }
}
