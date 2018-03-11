package com.ft.gmserver;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import com.ft.gmserver.Center.CenterClient;
import com.ft.gmserver.Center.CenterManager;
import com.ft.gmserver.csv.CSVConfig;
import com.ft.gmserver.httpclient.Client;
import com.ft.gmserver.pages.pagelayerclose;
import com.ft.gmserver.pages.gm.pageaccount;
import com.ft.gmserver.pages.gm.pageaccountblock;
import com.ft.gmserver.pages.gm.pagebox;
import com.ft.gmserver.pages.gm.pagebroadcast;
import com.ft.gmserver.pages.gm.pagedecal;
import com.ft.gmserver.pages.gm.pageemail;
import com.ft.gmserver.pages.gm.pageemaillog;
import com.ft.gmserver.pages.gm.pageemaillogdetail;
import com.ft.gmserver.pages.gm.pagefightdata;
import com.ft.gmserver.pages.gm.pagegm;
import com.ft.gmserver.pages.gm.pageipblacklist;
import com.ft.gmserver.pages.gm.pageitem;
import com.ft.gmserver.pages.gm.pageplayer;
import com.ft.gmserver.pages.gm.pageshopitem;
import com.ft.gmserver.pages.gm.pageshopitemadd;
import com.ft.gmserver.pages.gm.pageshopitemupd;

import engine.bean.BeanFactory;
import engine.common.TimeCreator;
import engine.log.LogUtil;
import engine.string.StringUtil;
import engine.util.DateUtils;
import engine.util.IPv4Util;
import generated.cgame.packets.objects.ShopItemList;

public class SubPageGM extends SubPage {

	public SubPageGM(Client client) {
		this.client = client;
	}

	@Override
	public void AnalyzeParam(String str) {

	}

	public void AnalyzeUri(String uri, String param) {
		try {
			// 权限检查
			if (!client.accountinfo.getGm()) {
				client.writeErrorPage("没有足够权限");
				return;
			}
			if (uri.equals("/gmtool")) {
				gm();
			} else if (uri.equals("/gmtool/account")) {
				account(param);
			} else if (uri.equals("/gmtool/unblock")) {
				unblock(param);
			} else if (uri.equals("/gmtool/block")) {
				block(param);
			} else if (uri.equals("/gmtool/blocksave")) {
				blocksave(param);
			} else if (uri.equals("/gmtool/player")) {
				player(param);
			} else if (uri.equals("/gmtool/item")) {
				item(param);
			} else if (uri.equals("/gmtool/box")) {
				box(param);
			} else if (uri.equals("/gmtool/decal")) {
				decal(param);
			} else if (uri.equals("/gmtool/fightdata")) {
				fightdata(param);
			} else if (uri.equals("/gmtool/shopitem")) {
				if (!gameserver_isok())
					return;

				shopitem(param);
			} else if (uri.equals("/gmtool/shopitemonsale")) {
				shopitemonsale(param);
			} else if (uri.equals("/gmtool/shopitemdel")) {
				shopitemdel(param);
			} else if (uri.equals("/gmtool/shopitemadd")) {
				shopitemadd();
			} else if (uri.equals("/gmtool/shopitemAddSave")) {
				shopitemAddSave(param);
			} else if (uri.equals("/gmtool/shopitemupd")) {
				shopitemupd(param);
			} else if (uri.equals("/gmtool/shopitemUpdSave")) {
				shopitemUpdSave(param);
			} else if (uri.equals("/gmtool/ipblacklist")) {
				ipblacklist();
			} else if (uri.equals("/gmtool/ipblackadd")) {
				ipblackadd(param);
			} else if (uri.equals("/gmtool/ipblackdel")) {
				ipblackdel(param);
			} else if (uri.equals("/gmtool/broadcast")) {// 广播消息
				broadcast();
			} else if (uri.equals("/gmtool/broadcastdeploy")) {// 发布
				broadcastdeploy(param);
			} else if (uri.equals("/gmtool/broadcastcancel")) {// 取消发布
				broadcastcancel();
			} else if (uri.equals("/gmtool/email")) {// email发放道具
				email();
			} else if (uri.equals("/gmtool/emailsend")) {// 发放道具
				emailsend(param);
			} else if (uri.equals("/gmtool/emaillog")) {// email log
				emaillog();
			} else if (uri.equals("/gmtool/emaillogdetail")) {// email log detail
				emaillogdetail(param);
			}
		} catch (Exception e) {
			LogUtil.warn("SubPageGM Exception", e);
		}
	}

	private void gm() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagegm().content());
		client.writeEnd();
	}

	private void unblock(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ids = params.get("ids");

		gameserver.accountblock(ids, 0);

		account(param);
	}

	private void block(String param) {
		client.writeHeader();
		client.responseContent.append(new pageaccountblock(client.accountinfo).content(param));
		client.writeEnd();
	}

	private void blocksave(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ids = params.get("ids").replaceAll("%2C", ",");
		String isnever = params.get("isnever");
		String day = params.get("day");
		String hour = params.get("hour");

		int blocktime = 0;
		if (isnever.equals("1")) {
			blocktime = (int) ((DateUtils.string2Date("2030-01-01 00:00:00", DateUtils.PATTERN_DATE_TIME).getTime()) / 1000);
		} else {
			blocktime = (Integer.parseInt(day) * 24 + Integer.parseInt(hour)) * 3600 + TimeCreator.GetTimeStamp();
		}

		gameserver.accountblock(ids, blocktime);

		close();
		account(param);
	}

	private void account(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageaccount(client.accountinfo).content(param));
		client.writeEnd();
	}

	private void close() {
		client.writeHeader();
		client.responseContent.append(new pagelayerclose().content());
		client.writeEnd();
	}

	private void player(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageplayer(client.accountinfo).content(param));
		client.writeEnd();
	}

	private void item(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageitem().content(param));
		client.writeEnd();
	}

	private void box(String param) {
		client.writeHeader();
		client.responseContent.append(new pagebox().content(param));
		client.writeEnd();
	}

	private void decal(String param) {
		client.writeHeader();
		client.responseContent.append(new pagedecal().content(param));
		client.writeEnd();
	}

	private void fightdata(String param) {
		client.writeHeader();
		client.responseContent.append(new pagefightdata().content(param));
		client.writeEnd();
	}

	private void shopitem(String param) {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageshopitem().content(param));
		client.writeEnd();
	}

	private void shopitemonsale(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		short on = Short.parseShort(params.get("on"));
		String ids = params.get("ids");

		for (String id : ids.split(",")) {
			for (ShopItemList o : gameserver.shopitemList) {
				if (o.propid == Integer.parseInt(id)) {
					o.onsale = on;
					break;
				}
			}
		}
		gameserver.updShopItem();
		shopitem(param);
	}

	private void shopitemdel(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ids = params.get("ids");

		Iterator<ShopItemList> it = gameserver.shopitemList.iterator();
		while (it.hasNext()) {
			if (ids.contains(String.valueOf(it.next().propid)))
				it.remove();
		}

		gameserver.updShopItem();
		shopitem(param);
	}

	private void shopitemadd() {
		client.writeHeader();
		client.responseContent.append(new pageshopitemadd().content());
		client.writeEnd();
	}

	private void shopitemAddSave(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		int propid = Integer.parseInt(params.get("propid"));
		int price = Integer.parseInt(params.get("price"));
		short type = Short.parseShort(params.get("type"));
		short onsale = Short.parseShort(params.get("onsale"));

		if (CSVConfig.propconfig.get(propid) == null) {
			client.writeErrorPage("该道具配置不存在！propid=" + propid);
			shopitem(param);
			return;
		}

		ShopItemList o = new ShopItemList();
		o.propid = propid;
		o.price = price;
		o.type = type;
		o.onsale = onsale;

		boolean b = false;
		for (ShopItemList v : gameserver.shopitemList) {
			if (v.propid == propid) {
				b = true;
				break;
			}
		}
		if (b) {
			client.writeErrorPage("该道具已存在！propid=" + propid);
			shopitem(param);
			return;
		}

		gameserver.shopitemList.add(o);

		gameserver.updShopItem();
		close();
		shopitem(param);
	}

	private void shopitemupd(String param) {
		client.writeHeader();
		client.responseContent.append(new pageshopitemupd().content(param));
		client.writeEnd();
	}

	private void shopitemUpdSave(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		int propid = Integer.parseInt(params.get("propid"));
		int price = Integer.parseInt(params.get("price"));
		short type = Short.parseShort(params.get("type"));
		short onsale = Short.parseShort(params.get("onsale"));

		for (ShopItemList v : gameserver.shopitemList) {
			if (v.propid == propid) {
				v.price = price;
				v.type = type;
				v.onsale = onsale;
				break;
			}
		}

		gameserver.updShopItem();
		close();
		shopitem(param);
	}

	private void ipblacklist() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageipblacklist().content());
		client.writeEnd();
	}

	private void ipblackadd(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ip = params.get("ip");

		int[] arr = new int[1];
		arr[0] = IPv4Util.ipStr2Int(ip);

		if (gameserver.ipblacklist != null && gameserver.ipblacklist.length != 0) {
			if (Arrays.stream(gameserver.ipblacklist).boxed().collect(Collectors.toList()).contains(arr[0])) {
				// IntStream.of(centerClient.ipblacklist).boxed().collect(Collectors.toList());// 也可以
				client.writeErrorPage("此IP已加入黑名單！IP=" + ip);
				return;
			}
		}

		gameserver.updIPBlacklist((short) 0, arr);
		ipblacklist();
	}

	private void ipblackdel(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String ids = params.get("ids");

		String[] ips = ids.split(",");
		int[] arr = new int[ips.length];
		int i = 0;
		for (String ip : ips)
			arr[i++] = Integer.parseInt(ip);

		gameserver.updIPBlacklist((short) 1, arr);
		ipblacklist();
	}

	private void broadcast() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pagebroadcast().content());
		client.writeEnd();
	}

	private void broadcastdeploy(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String content = params.get("content");
		gameserver.broadcastdeploy(content);
		broadcast();
	}

	private void broadcastcancel() {
		if (!gameserver_isok())
			return;

		gameserver.broadcastcancel();
		broadcast();
	}

	private boolean gameserver_isok() {
		gameserver = BeanFactory.getBean(CenterManager.class).getCenterClient_GameServer();
		if (gameserver == null) {
			client.writeErrorPage("GameServer 未启动！");
			return false;
		}
		return true;
	}

	private void email() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageemail().content());
		client.writeEnd();
	}

	private void emailsend(String param) {
		if (!gameserver_isok())
			return;

		Map<String, String> params = StringUtil.split(param, "&", "=");
		String nickname = params.get("nickname").trim();
		String mailtitle = params.get("mailtitle");
		String mailcontent = params.get("mailcontent");
		String propid1 = params.get("propid1");
		String propid2 = params.get("propid2");
		String propid3 = params.get("propid3");
		String propid4 = params.get("propid4");
		String propid5 = params.get("propid5");
		String num1 = params.get("num1");
		String num2 = params.get("num2");
		String num3 = params.get("num3");
		String num4 = params.get("num4");
		String num5 = params.get("num5");

		String[] nicknames = nickname.split(",");
		int[] propid = new int[5];
		int[] num = new int[5];

		int i = 0;
		if (propid1 != null) {
			propid[i] = Integer.parseInt(propid1);
			num[i++] = Integer.parseInt(num1);
		}
		if (propid2 != null) {
			propid[i] = Integer.parseInt(propid2);
			num[i++] = Integer.parseInt(num2);
		}
		if (propid3 != null) {
			propid[i] = Integer.parseInt(propid3);
			num[i++] = Integer.parseInt(num3);
		}
		if (propid4 != null) {
			propid[i] = Integer.parseInt(propid4);
			num[i++] = Integer.parseInt(num4);
		}
		if (propid5 != null) {
			propid[i] = Integer.parseInt(propid5);
			num[i++] = Integer.parseInt(num5);
		}

		gameserver.mail(nicknames, mailtitle, mailcontent, propid, num);
		email();
	}

	private void emaillog() {
		client.writeHeader();
		client.writeSlide();
		client.responseContent.append(new pageemaillog().content());
		client.writeEnd();
	}

	private void emaillogdetail(String param) {
		client.writeHeader();
		client.responseContent.append(new pageemaillogdetail(param).content());
		client.writeEnd();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	private CenterClient gameserver;// GameServer连接CenterServer

}
