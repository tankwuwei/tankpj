package com.ft.gameserver.globalmods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.basemod.GlobalBaseMod;
import com.ft.gameserver.clients.Client;
import com.ft.gameserver.player.Player;
import com.ft.gameserver.playermod.pack.PackMod;
import com.ft.gameserver.utility.IdCreator;

import common.ErrorCode;
import dbobjects.gamedb.MailPlayerTable;
import engine.common.TimeCreator;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.Condition;
import engine.db.client.DBType;
import engine.net.CPacket;
import generated.cgame.packets.Code;
import generated.cgame.packets.client.CSMailGetItem;
import generated.cgame.packets.client.CSMailRead;
import generated.cgame.packets.objects.Mail;
import generated.cgame.packets.server.CGMail;
import generated.cgame.packets.server.SCMail;
import generated.cgame.packets.server.SCMailGetItem;
import generated.cgame.packets.server.SCMailRead;

@Controller
public class MailMod extends GlobalBaseMod implements DBHandler {

	@Autowired
	PlayerManager PlayerManager;
	@Autowired
	PackMod PackMod;
	@Autowired
	IdCreator IdCreator;

	@Override
	protected void handle(CPacket packet) {
		Client client = (Client) packet.o;
		Player player = client.player;
		switch (packet.code) {
		case Code.CSMailRead:// read mail
			readmail(player, ((CSMailRead) packet).mailids);
			break;
		case Code.CSMailGetItem:// mail get item
			mailgetitem(player, ((CSMailGetItem) packet).mailid);
			break;
		default:
			break;
		}

	}

	@PostConstruct
	private void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		List<MailPlayerTable> list = DBManager.get(MailPlayerTable.class, DBType.GameDB, Condition.eq("isdelete", 0));
		for (MailPlayerTable o : list)
			setPlayerMail(o);
	}

	private void setPlayerMail(MailPlayerTable o) {
		Map<Long, MailPlayerTable> mails;
		if (playermail.containsKey(o.nickname))
			mails = playermail.get(o.nickname);
		else {
			mails = new HashMap<>();
			playermail.put(o.nickname, mails);
		}
		mails.put(o.id, o);
	}

	@Override
	public void onPlayerLogin(Player player) {
		if (playermail.containsKey(player.getName()))
			sendMail(player, playermail.get(player.getName()));
	}

	private void sendMail(Player player, Map<Long, MailPlayerTable> mails) {
		isdeadline(player, mails);

		Mail[] mailArr = new Mail[mails.size()];
		int i = 0;
		for (MailPlayerTable o : mails.values()) {
			if (o.isdelete == 1)
				continue;

			Mail mail = new Mail();
			mail.mailid = o.id;
			mail.mailtitle = o.mailtitle;
			mail.mailcontent = o.mailcontent;
			mail.createtime = o.createtime;
			mail.isread = o.isread;
			mail.isget = o.isget;
			mail.propid = str2intarr(o.propid);
			mail.num = str2intarr(o.num);
			mailArr[i++] = mail;
		}

		SCMail msg = new SCMail();
		msg.mails = mailArr;
		player.Send(msg);
	}

	private void isdeadline(Player player, Map<Long, MailPlayerTable> mails) {
		Set<MailPlayerTable> delset = new HashSet<>();
		for (MailPlayerTable o : mails.values()) {
			if (StringUtils.equals("[0, 0, 0, 0, 0]", o.propid) && 7 * 24 * 3600 + o.createtime < TimeCreator.GetTimeStamp()) {// 沒有附件 七天過期刪除
				o.isdelete = 1;
				delset.add(o);
			}
		}

		if (delset.size() > 0)
			DBManager.saveOrUpdate(delset, DBType.GameDB);
	}

	private void readmail(Player player, long[] mailids) {
		Map<Long, MailPlayerTable> mails = playermail.get(player.getName());

		Set<MailPlayerTable> mailread = new HashSet<>();
		for (long mailid : mailids) {
			mails.get(mailid).isread = 1;
			mailread.add(mails.get(mailid));
		}

		DBManager.saveOrUpdate(mailread, DBType.GameDB);

		sendReadMail(player, mailids);
	}

	private void sendReadMail(Player player, long[] mailids) {
		SCMailRead msg = new SCMailRead();
		msg.errorcode = ErrorCode.SUCCESS;
		msg.mailids = mailids;
		player.Send(msg);
	}

	private void mailgetitem(Player player, long mailid) {
		Map<Long, MailPlayerTable> mails = playermail.get(player.getName());
		MailPlayerTable mail = mails.get(mailid);
		if (mail.isget == 1) {// 已领取
			sendMailGetItem(player, mailid, ErrorCode.IsGetItem, null, null);
			return;
		}

		mail.isget = 1;
		mail.isdelete = 1;
		DBManager.saveOrUpdate(mail, DBType.GameDB);

		int[] propidArr = str2intarr(mail.propid);
		int[] numArr = str2intarr(mail.num);

		PackMod.putPack(player, propidArr, numArr);// 放入背包

		sendMailGetItem(player, mailid, ErrorCode.SUCCESS, propidArr, numArr);

	}

	private int[] str2intarr(String str) {
		String[] strarr = str.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		List<Integer> list = new ArrayList<>();
		for (String s : strarr) {
			int i = Integer.parseInt(s.trim());
			if (i == 0)
				continue;
			list.add(i);
		}

		return getIntArr(list);
	}

	private int[] getIntArr(List<Integer> list) {
		Integer[] a = (Integer[]) list.toArray(new Integer[list.size()]);
		int[] b = new int[a.length];
		b = Arrays.stream(a).mapToInt(Integer::valueOf).toArray();
		return b;
	}

	private void sendMailGetItem(Player player, long mailid, short errorcode, int[] propid, int[] num) {
		SCMailGetItem msg = new SCMailGetItem();
		msg.errorcode = errorcode;
		msg.mailid = mailid;
		msg.propid = propid;
		msg.num = num;
		player.Send(msg);
	}

	@Override
	public void atfterInitDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerLogout(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDayChanged() {
		// TODO Auto-generated method stub

	}

	/**
	 * 1持久化; 2更新缓存; 3推送给在线玩家
	 * 
	 * @param msg
	 */
	public void mail(CGMail msg) {
		int createtime = TimeCreator.GetTimeStamp();// 批次
		for (String nickname : msg.nickname) {
			MailPlayerTable mailplayer = new MailPlayerTable();
			mailplayer.id = IdCreator.GetId64();
			mailplayer.mailtitle = msg.mailtitle;
			mailplayer.mailcontent = msg.mailcontent;
			mailplayer.createtime = createtime;
			mailplayer.nickname = nickname;
			mailplayer.propid = IntStream.of(msg.propid).boxed().collect(Collectors.toList()).toString();
			mailplayer.num = IntStream.of(msg.num).boxed().collect(Collectors.toList()).toString();
			DBManager.saveOrUpdate(mailplayer, DBType.GameDB);

			setPlayerMail(mailplayer);

			Map<Long, MailPlayerTable> newmail = new HashMap<>();
			newmail.put(mailplayer.id, mailplayer);
			Player player = PlayerManager.getOnlinePlayer(nickname);
			if (player != null)
				sendMail(player, newmail);
		}
	}

	/*
	 * 玩家邮件缓存<nickname, Map<mailid, MailPlayerTable>>
	 */
	private Map<String, Map<Long, MailPlayerTable>> playermail = new HashMap<>();
}
