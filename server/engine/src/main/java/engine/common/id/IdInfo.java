package engine.common.id;

public class IdInfo {
	//运营商
	private final short operator;
	//服务器编号
	private final short server;
	//id编号，去掉前缀的
	private final long id;
	
	public IdInfo(long id){
		if ((0xF000000000000000L & id) != 0) {
			throw new IllegalArgumentException("无效的ID标识值:" + id);
		}
		this.id = id & 0x0000000FFFFFFFFFL; // 将高位置0(保留位+运营商位+服务器位)
		this.server = (short) ((id >> 36) & 0x0000000000000FFFL);
		this.operator = (short) ((id >> 48) & 0x0000000000000FFFL);
	}
	
	public short getServer(){
		return server;
	}
	
	public short getOperator(){
		return operator;
	}
	
	public long getId(){
		return id;
	}
}
