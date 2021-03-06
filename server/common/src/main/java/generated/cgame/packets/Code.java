package generated.cgame.packets;
public interface Code
{
    // ////////////////client packets//////////////
    short CLogin = 402;
    short ChargeRet = 405;
    short SetNickName = 407;
    short GameSvrRegistLoginSvr = 500;
    short ClientRegistLoginSvr = 501;
    short RequestLoginSvr = 502;
    short CSteamLogin = 504;
    short CSLogin = 5300;
    short CSPing = 5302;
    short CSlientReady = 5310;
    short CSUseTank = 5325;
    short CSSaveKeyboard = 5330;
    short CSGetPVPData = 5340;
    short CSGetRankList = 5342;
    short CSGetShopList = 5350;
    short CSBuyItem = 5352;
    short CSStartPVE = 5400;
    short CSEndPVE = 5402;
    short CSRequireIntoMap = 5500;
    short CSEnterMap = 5503;
    short CSLeaveMap = 5506;
    short CSCancelEnterMap = 5508;
    short RequestSaleTankModel = 5538;
    short RequestBuySaleTankModel = 5540;
    short RequestUnlockTech = 5542;
    short RequestDevelopTankModel = 5543;
    short CSRequestAddTankSpace = 5546;
    short CSRequestDevelopTankPart = 5548;
    short CSRequestBuyTankPart = 5550;
    short CSRequestBuyTank = 5552;
    short CSRequestStartMatch = 5554;
    short CSRequestCancelMatch = 5556;
    short CSChangeTankMember = 5559;
    short CSRequestPlayerRankInfo = 5563;
    short CSGetFighterList = 6000;
    short CSEnterFighter = 6002;
    short CSRequestFightResult = 6004;
    short CSChangeTankPart = 6006;
    short CSAddBullet = 6008;
    short CSTankBulletConfig = 6010;
    short CSTankBulletBakConfig = 6012;
    short CSAddItem = 6014;
    short CSTankChangeItem = 6016;
    short CSTankItemAutoBak = 6018;
    short SCRet = 6020;
    short CSChangeSkill = 6021;
    short CSTechReset = 6025;
    short CSRequestDevelopRate = 6028;
    short CSRequestUpMemberstar = 6034;
    short CSGetZoneInfo = 7000;
    short CSUseItem = 7003;
    short CSUnUseItem = 7005;
    short CSShortcut = 7007;
    short CSBuyShopItem = 7010;
    short CSMailRead = 7016;
    short CSMailGetItem = 7018;
    short CSPlayerClientSetting = 7020;
    short CSPlayerPCinfo = 7021;
    short CSSearchPlayer = 7022;
    short CSPlayerLobbyFromFight = 7024;
    short CSTeamInvite = 7025;
    short CSTeamNotifyRet = 7027;
    short CSTeamLeave = 7029;
    short CSSetIsInvite = 7032;
    short UGlogin = 16000;
    short UGState = 16002;
    short UGUserLoginRet = 16004;
    short UGUserAction = 16005;
    short CSGameSvrStartFightRet = 16007;
    short SSSendFightOver = 16009;
    short CSRoleFighExit = 16010;
    short UGPlayerJoinFight = 16012;
    short UGPlayerDead = 16013;
    short USStartFightRet = 17002;
    short UGPlayerLeave = 17003;
    // ////////////////server packets//////////////
    short LoginRet = 403;
    short Charge = 404;
    short ShouldReadyInfo = 406;
    short SetNickNameRet = 408;
    short RequestLoginSvrRet = 503;
    short VMsgConnect = 1024;
    short VMsgDisconnect = 1025;
    short VMsgConnectError = 1026;
    short SCLoginRet = 5301;
    short SCPong = 5303;
    short SCKickOut = 5304;
    short SCCheatKickOut = 5305;
    short SCPlayerLoginData = 5311;
    short SCPlayerBaseData = 5312;
    short SCPlayerData = 5313;
    short SCFinishLevel = 5322;
    short SCTankModel = 5323;
    short SCTechnologies = 5324;
    short ScUseTankRet = 5326;
    short SCTankParts = 5333;
    short SCPVPData = 5341;
    short SCRankList = 5343;
    short SCShopList = 5351;
    short SCBuyItem = 5353;
    short SCStartPVE = 5401;
    short SCEndPVE = 5403;
    short SCMaps = 5404;
    short SCRequireMapRet = 5501;
    short SCEnterMap = 5502;
    short SCBattleStart = 5504;
    short SCBattleFinish = 5505;
    short CSCancelRet = 5509;
    short RequestSaleTankModelRet = 5539;
    short RequestBuySaleTankModelRet = 5541;
    short RequestDevelopTankModelRet = 5544;
    short SCRequestAddTankSpaceRet = 5547;
    short SCRequestDevelopTankPartRet = 5549;
    short SCRequestBuyTankPartRet = 5551;
    short SCRequestBuyTankRet = 5553;
    short SCRequestStartMatchRet = 5555;
    short SCRequestCancelMatchRet = 5557;
    short SCTankMemberInfo = 5558;
    short SCChangeTankMemberRet = 5560;
    short SCSendFightLoadingInfo = 5561;
    short SCFightRankInfo = 5562;
    short SCPlayerRankInfo = 5564;
    short SCPlayerFightData = 5566;
    short SCFighterList = 6001;
    short SCEnterFighter = 6003;
    short SCRequestFightResultRet = 6005;
    short CSChangeTankPartRet = 6007;
    short ScAddBulletRet = 6009;
    short CSTankBulletConfigRet = 6011;
    short CSTankBulletBakConfigRet = 6013;
    short SCAddItemRet = 6015;
    short ScTankChangeItemRet = 6017;
    short SCTankItemAutoBakRet = 6019;
    short SCChangeSkillRet = 6022;
    short SCCoinChange = 6023;
    short SCTankTechChange = 6024;
    short SCTechResetRet = 6026;
    short SCTechRate = 6027;
    short CSRequestDevelopRateRet = 6029;
    short SCMaxUsedTank = 6030;
    short SCTankFightLockInfo = 6033;
    short SCRequestUpMemberstarRet = 6035;
    short SCRequestUpMemberLvlRet = 6036;
    short SCZoneInfo = 7001;
    short SCPlayerItems = 7002;
    short SCUseItem = 7004;
    short SCUnUseItem = 7006;
    short SCShortcut = 7008;
    short SCShopItem = 7009;
    short SCBuyShopItem = 7011;
    short SCServerShutdown = 7012;
    short SCBroadcast = 7013;
    short SCBroadcastCancel = 7014;
    short SCMail = 7015;
    short SCMailRead = 7017;
    short SCMailGetItem = 7019;
    short SCSearchPlayer = 7023;
    short SCTeamNotify = 7026;
    short SCTeamPlayer = 7028;
    short SCTeamLeaveRet = 7030;
    short SCTeamPlayerLeave = 7031;
    short GULoginRet = 16001;
    short GUUserLogin = 16003;
    short SCGameSvrStartFight = 16006;
    short SCSendFightToClient = 16008;
    short ScTankSpaceCount = 16011;
    short SUStartFight = 17001;
    short ReportServiceInfo = 31000;
    short ReportSystemInfo = 31001;
    short StartService = 31010;
    short Shutdown = 31011;
    short AgentLoginGameSvr = 31012;
    short AgentLoginGameSvrRet = 31013;
    short CGGetShopItem = 31014;
    short CGUpdShopItem = 31015;
    short GCShopItemList = 31016;
    short ServerType = 31017;
    short CGAccountBlock = 31018;
    short CGGetOnlinePlayerItem = 31019;
    short GCOnlinePlayerItem = 31020;
    short CGGetIPBlacklist = 31021;
    short GCIPBlacklist = 31022;
    short CGUpdIPBlacklist = 31023;
    short CGBroadcast = 31024;
    short CGBroadcastCancel = 31025;
    short CGMail = 31026;
}