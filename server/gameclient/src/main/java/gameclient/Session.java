package gameclient;

import java.util.Random;

import common.ErrorCode;
import engine.common.TimeCreator;
import engine.job.Time;
import engine.log.LogUtil;
import engine.net.CPacket;
import engine.net.NativeBuffer;
import engine.net.SocketListener;
import engine.net.TcpConnector;
import engine.server.ServerBase;
import engine.util.Utility;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSLogin;
import generated.cgame.packets.client.CSRequestAddTankSpace;
import generated.cgame.packets.client.CSRequestCancelMatch;
import generated.cgame.packets.client.CSRequestStartMatch;
import generated.cgame.packets.client.CSlientReady;
import generated.cgame.packets.client.SetNickName;
import generated.cgame.packets.objects.BoxData;
import generated.cgame.packets.objects.FinishLevelinfo;
import generated.cgame.packets.objects.TankModelinfo;
import generated.cgame.packets.objects.TechnologyInfo;
import generated.cgame.packets.client.RequestDevelopTankModel;
import generated.cgame.packets.client.RequestSaleTankModel;
import generated.cgame.packets.server.SCBuyShopItem;
import generated.cgame.packets.server.SCFinishLevel;
import generated.cgame.packets.server.SCLoginRet;
import generated.cgame.packets.server.SCTankModel;
import generated.cgame.packets.server.SCTechnologies;
import generated.cgame.packets.server.SetNickNameRet;
import generated.cgame.packets.server.ShouldReadyInfo;
import generated.cgame.packets.client.CSUseItem;
import generated.cgame.packets.client.CSBuyShopItem;
import generated.cgame.packets.server.SCPlayerItems;
import generated.cgame.packets.server.SCUseItem;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

public class Session {
	Channel channel;
	String account;
	int serverid;
	int port;
	private Long roleid;
	TcpConnector connector;
	Random random = new Random();

	public Session() {
		// account = Utility.getRandomString(10);
		account = "gameclient2";
	}

	private void reset() {
		serverid = 0;
		port = 0;
		roleid = 0L;
	}

	public void login() {
		if (channel != null && channel.isActive()) {
			doLogin();
		} else {
			try {
				Thread.sleep(random.nextInt(10000));
				connector = new TcpConnector();
				connector.connect("127.0.0.1", 8055, listener);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
	}

	private void doLogin() {
		System.out.println("send loginserver ");
		CSLogin msg = new CSLogin();
		// account = "tedder";
		msg.account = "test101";
		msg.password = "123456";
		msg.time = TimeCreator.GetTimeStamp();
		String str = msg.account + gameclient.appkey + msg.time;
		msg.encrpt = Utility.md5_32(str);
		Send(msg);
	}

	public void ready() {
		LogUtil.debug("send ready msg");
		CSlientReady msg = new CSlientReady();
		Send(msg);
		
		CSRequestStartMatch match =new CSRequestStartMatch();
		match.matchtype=2;
		Send(match);
		
//	CSRequestCancelMatch cancelMatch =new CSRequestCancelMatch();
	
	//	Send(cancelMatch);
		
//		RequestDevelopTankModel  add=  new RequestDevelopTankModel();
//		add.modelid=1001;
//		Send(add);
//		
//
//		add.modelid=1002;
//		Send(add);
//
//		add.modelid=1003;
//		Send(add);
//		add.modelid=1004;
//		Send(add);
//		add.modelid=1005;
//		Send(add);
//
//		RequestSaleTankModel saleTankModel=new RequestSaleTankModel();
//
//
//		saleTankModel.modelid=1000;
//		Send(saleTankModel);


	}

	public void logout() {
		// CLogout msg = new CLogout();
		// Send(msg);
		channel.disconnect();
		connector = null;
		reset();
		login();

	}

	private void OnLoginReturn(NativeBuffer buf) {
		SCLoginRet msg = new SCLoginRet();
		msg.read(buf);
		System.out.println("login loginserver ret " + msg.ret);
		if (msg.ret == 0) {
			CSBuyShopItem _msg = new CSBuyShopItem();
			_msg.propid = 7001;
			Send(_msg);
		} else {
			System.err.println("login loginserver error " + msg.ret);
		}
		// LogUtil.debug(msg.account + " login result: " + msg.ret);
	}

	private void OnData(ByteBuf msg) {
		NativeBuffer buf = NativeBuffer.wrap(msg);
		int readLen = buf.readableBytes();
		if (readLen < 4 || readLen > 1024 * 8) {
			return;
		}
		buf.readShort(); // appid
		Short code = buf.readShort();

		LogUtil.debug("recv code " + code);
		
		if( code == Code.SCBuyShopItem ){
			SCBuyShopItem _ret = new SCBuyShopItem();
			_ret.read(buf);
			
			if( _ret.errcode != 0 ){
				LogUtil.debug("buyitem :" + _ret.propid + ",errcode:" + _ret.errcode );
				return;
			}
		}
		
		if( code == Code.SCPlayerItems){
			SCPlayerItems _ret = new SCPlayerItems();
			if( _ret.items.length == 1 ){
				CSUseItem _msg = new CSUseItem();
				_msg.id = _ret.items[0].id;
				_msg.pos = 0;
				Send(_msg);
			}
		}
		
		if( code == Code.SCUseItem ){
			SCUseItem _ret = new SCUseItem();
			LogUtil.debug("useitem :" + _ret.id + ",errcode:" + _ret.errcode + ",pos:" + _ret.pos );
		}
		
		if (code == Code.SCLoginRet) {
			OnLoginReturn(buf);
		}

		if (code == Code.SCFinishLevel) {
			SCFinishLevel finishLevel = new SCFinishLevel();

			finishLevel.read(buf);
			if (finishLevel.finishs != null) {
				for (FinishLevelinfo value : finishLevel.finishs) {
					LogUtil.debug("finishlvl " + value.id + " " + value.star);
				}
			}
		}

		if (code == Code.SCTankModel) {
			SCTankModel finishLevel = new SCTankModel();

			finishLevel.read(buf);
			if (finishLevel.tankmodels != null) {
				for (TankModelinfo value : finishLevel.tankmodels) {
					LogUtil.debug("tankmodleid:" + value.modelid + " lvl:" + value.masterlvl+" bsale: "+value.bsale);
				}
			}

		}
		
		if (code==Code.SCTechnologies) {
			SCTechnologies tech=new SCTechnologies();
			tech.read(buf);
			if (tech.technologies!=null) {
				for (TechnologyInfo value : tech.technologies) {
					LogUtil.debug("techid:" + value.techId + " techlvl:" + value.techLvl);
				}
			}
		}
		

		if (code == Code.ShouldReadyInfo) {
			ShouldReadyInfo readyNickName = new ShouldReadyInfo();
			readyNickName.read(buf);

			if (readyNickName.bsetnick == 1) {
				LogUtil.debug("设置玩家 昵称" + account);
				SetNickName setNickName = new SetNickName();
				setNickName.nickname = "gameclient"+System.currentTimeMillis();
				Send(setNickName);
			} else {
				ready();
			}

		}

		if (code == Code.SetNickNameRet) {
			SetNickNameRet ret = new SetNickNameRet();
			ret.read(buf);
			LogUtil.debug("设置玩家昵称返回 "+ret.ret);
			if (ret.ret == ErrorCode.SUCCESS) {

				ready();
				LogUtil.debug("设置玩家昵称成功");
			}else {
				LogUtil.error("设置玩家昵称 失败");
			}
		}
	}

	public void Send(CPacket packet) {
		if (channel == null || !channel.isActive())
			return;
		NativeBuffer buffer = NativeBuffer.allocate();
		buffer.writeInt(0);
		buffer.writeShort(ServerBase.appid);
		buffer.writeShort(packet.code);
		packet.write(buffer);
		buffer.setInt(0, buffer.readableBytes() - 4);
		channel.writeAndFlush(buffer.byteBuf());
		buffer.free();
	}

	private SocketListener listener = new SocketListener() {
		@Override
		public void setUdpChannel(Channel channel) {
		}

		@Override
		public void onPacket(Channel channel, DatagramPacket packet) {
		}

		@Override
		public void onDisconnected(Channel channel) {
			LogUtil.debug("disconnected");
		}

		@Override
		public void onData(Channel channel, ByteBuf msg) {
			OnData(msg);
		}

		@Override
		public void onConnected(Channel channel) {
			LogUtil.debug("connected:");
			Session.this.channel = channel;
			doLogin();
		}
	};

}
