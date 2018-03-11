package common;

/**
 * 系统通用错误码定义
 * 
 * @author cxz
 *
 */
public class ErrorCode {
	public static final short FAILED = -1;
	public static final short SUCCESS = 0;

	public static final short Login = 0x100;
	public static final short NoAccount = Login + 1;
	public static final short WrongPwd = Login + 2;
	public static final short Blocked = Login + 3; // 账号封停
	public static final short VersionUnmatch = Login + 4;
	public static final short InvalidCode = Login + 5; // 无效的校验码
	public static final short InvalidPacket = Login + 6;
	public static final short AccountBlocked = Login + 7;// 账号封停
	public static final short SteamInitError = Login + 8;// Steam初始化失败

	public static final short NoRole = Login + 10; // no role
	public static final short InvalidName = Login + 11; // filter name
	public static final short DuplicateName = Login + 12; //
	public static final short NickNameIllegitmacy = Login + 13; // 昵称非法
	public static final short NickNameRepeat = Login + 14; // 玩家昵称重复
	public static final short NoLogin = Login + 15; // 服务器即将重启不允许登陸

	public static final short Mod = 0x150;
	public static final short NoCard = Mod + 1; //
	public static final short NoExp = Mod + 2;
	public static final short NoMoney = Mod + 3;
	public static final short WorngCardNum = Mod + 4;
	public static final short NoConfig = Mod + 5;
	public static final short TopLevel = Mod + 6;
	public static final short NoComm = Mod + 7;
	public static final short DupComm = Mod + 8; // 已解锁
	public static final short NoIngot = Mod + 9; // 没有足够的元宝
	public static final short NoItem = Mod + 10;
	public static final short BHaveSale = Mod + 11; // 已经卖了
	public static final short BNotHaveSale = Mod + 12; // 没有卖掉 所以没法购买
	public static final short AddTankRepeat = Mod + 13; // 请求购买的tank重复
	public static final short AddTankNoSpace = Mod + 14; // 请求购买的tank车位数不够
	public static final short DevelopTankPartNoTank = Mod + 15; // 添加tank部件 没有该tank
	public static final short DevelopTankPartNoPre = Mod + 16;// 添加tank部件前置条件不够
	public static final short BuyTankNotFound = Mod + 17;// 购买tank找不到
	public static final short BuyTankLvlError = Mod + 18;// 购买tank等级错误
	public static final short BuyTankPartNotFound = Mod + 19;// 购买tankpart找不到
	public static final short BuyTankPartLvlError = Mod + 20;// 购买tankpart等级错误

	public static final short NoSliver = Mod + 21;// 银币不够
	public static final short NoGold = Mod + 22;// 金币不够
	public static final short NoTech = Mod + 23;// 天赋点不够
	public static final short NoDevelop = Mod + 24;// 研发点不够

	public static final short StartMatchTankLock = Mod + 25;// 开始匹配的时候tank已经因为中途退出锁住了
	public static final short DiyPartRepeat = Mod + 26;// diy部件类型不允许重复

	public static final short Battle = 0x200;
	public static final short UnknowMap = Battle + 1;
	public static final short NotIdle = Battle + 2; // in battle map
	public static final short NotInMatch = Battle + 3;
	public static final short ServerFull = Battle + 4; // fightserver is full
	public static final short InMatch = Battle + 5; // 在匹配隊列中
	public static final short WrongMatchType = Battle + 6; // 匹配模式不正確
	public static final short NotMatch = Battle + 7; // 服务器即将重启不允许再匹配
	public static final short WaitMatch = Battle + 8; // waiting

	public static final short Item = 0x250;
	public static final short NotItem = Item + 1;// 道具不存在
	public static final short UnUse = Item + 2;// 已经被卸载
	public static final short InvalidPos = Item + 3;// 無效位置
	public static final short OverDeadLine = Item + 4;// 已过有效期

	public static final short ShopItem = 0x300;
	public static final short NotEnoughGold = ShopItem + 1;// Gold不足

	public static final short Mail = 0x350;
	public static final short IsGetItem = Mail + 1;// 已领取

	public static final short Team = 0x400;
	public static final short NoFriend = Team + 1;
	public static final short TeamLeaved = Team + 2;// 已离开
	public static final short TeamMax = Team + 3;// 队伍已满
}
