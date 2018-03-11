package generated.cgame.packets;
import java.util.*;
import engine.net.*;
import generated.cgame.packets.client.*;
import generated.cgame.packets.server.*;

public class ClientPackets
{
    private static final Map<Integer,CPacket> packets=new HashMap<Integer,CPacket>();

    static
    {
        packets.put(402,new CLogin());
        packets.put(405,new ChargeRet());
        packets.put(407,new SetNickName());
        packets.put(500,new GameSvrRegistLoginSvr());
        packets.put(501,new ClientRegistLoginSvr());
        packets.put(502,new RequestLoginSvr());
        packets.put(504,new CSteamLogin());
        packets.put(5300,new CSLogin());
        packets.put(5302,new CSPing());
        packets.put(5310,new CSlientReady());
        packets.put(5325,new CSUseTank());
        packets.put(5330,new CSSaveKeyboard());
        packets.put(5340,new CSGetPVPData());
        packets.put(5342,new CSGetRankList());
        packets.put(5350,new CSGetShopList());
        packets.put(5352,new CSBuyItem());
        packets.put(5400,new CSStartPVE());
        packets.put(5402,new CSEndPVE());
        packets.put(5500,new CSRequireIntoMap());
        packets.put(5503,new CSEnterMap());
        packets.put(5506,new CSLeaveMap());
        packets.put(5508,new CSCancelEnterMap());
        packets.put(5538,new RequestSaleTankModel());
        packets.put(5540,new RequestBuySaleTankModel());
        packets.put(5542,new RequestUnlockTech());
        packets.put(5543,new RequestDevelopTankModel());
        packets.put(5546,new CSRequestAddTankSpace());
        packets.put(5548,new CSRequestDevelopTankPart());
        packets.put(5550,new CSRequestBuyTankPart());
        packets.put(5552,new CSRequestBuyTank());
        packets.put(5554,new CSRequestStartMatch());
        packets.put(5556,new CSRequestCancelMatch());
        packets.put(5559,new CSChangeTankMember());
        packets.put(5563,new CSRequestPlayerRankInfo());
        packets.put(6000,new CSGetFighterList());
        packets.put(6002,new CSEnterFighter());
        packets.put(6004,new CSRequestFightResult());
        packets.put(6006,new CSChangeTankPart());
        packets.put(6008,new CSAddBullet());
        packets.put(6010,new CSTankBulletConfig());
        packets.put(6012,new CSTankBulletBakConfig());
        packets.put(6014,new CSAddItem());
        packets.put(6016,new CSTankChangeItem());
        packets.put(6018,new CSTankItemAutoBak());
        packets.put(6020,new SCRet());
        packets.put(6021,new CSChangeSkill());
        packets.put(6025,new CSTechReset());
        packets.put(6028,new CSRequestDevelopRate());
        packets.put(6034,new CSRequestUpMemberstar());
        packets.put(7000,new CSGetZoneInfo());
        packets.put(7003,new CSUseItem());
        packets.put(7005,new CSUnUseItem());
        packets.put(7007,new CSShortcut());
        packets.put(7010,new CSBuyShopItem());
        packets.put(7016,new CSMailRead());
        packets.put(7018,new CSMailGetItem());
        packets.put(7020,new CSPlayerClientSetting());
        packets.put(7021,new CSPlayerPCinfo());
        packets.put(7022,new CSSearchPlayer());
        packets.put(7024,new CSPlayerLobbyFromFight());
        packets.put(7025,new CSTeamInvite());
        packets.put(7027,new CSTeamNotifyRet());
        packets.put(7029,new CSTeamLeave());
        packets.put(7032,new CSSetIsInvite());
        packets.put(16000,new UGlogin());
        packets.put(16002,new UGState());
        packets.put(16004,new UGUserLoginRet());
        packets.put(16005,new UGUserAction());
        packets.put(16007,new CSGameSvrStartFightRet());
        packets.put(16009,new SSSendFightOver());
        packets.put(16010,new CSRoleFighExit());
        packets.put(16012,new UGPlayerJoinFight());
        packets.put(16013,new UGPlayerDead());
        packets.put(17002,new USStartFightRet());
        packets.put(17003,new UGPlayerLeave());
        packets.put(31000,new ReportServiceInfo());
        packets.put(31001,new ReportSystemInfo());
        packets.put(31010,new StartService());
        packets.put(31011,new Shutdown());
        packets.put(31012,new AgentLoginGameSvr());
        packets.put(31013,new AgentLoginGameSvrRet());
        packets.put(31014,new CGGetShopItem());
        packets.put(31015,new CGUpdShopItem());
        packets.put(31016,new GCShopItemList());
        packets.put(31017,new ServerType());
        packets.put(31018,new CGAccountBlock());
        packets.put(31019,new CGGetOnlinePlayerItem());
        packets.put(31020,new GCOnlinePlayerItem());
        packets.put(31021,new CGGetIPBlacklist());
        packets.put(31022,new GCIPBlacklist());
        packets.put(31023,new CGUpdIPBlacklist());
        packets.put(31024,new CGBroadcast());
        packets.put(31025,new CGBroadcastCancel());
        packets.put(31026,new CGMail());
    }
    public static CPacket get(engine.net.NativeBuffer buf)
    {
        int code=buf.readShort();
        CPacket packet=packets.get(code);
        if(packet==null) packet=new CPacket();
        else
        {
            try
            {
                packet=(CPacket)packet.clone();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        packet.code=code;
        packet.read(buf);
        return packet;
    }
}