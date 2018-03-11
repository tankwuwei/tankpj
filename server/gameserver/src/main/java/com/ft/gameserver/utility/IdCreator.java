/**
 * @ClassName: IdCreator 
 * @Description: id创建器
 * @author cxz
 * @date 2016.9.6 
 */

package com.ft.gameserver.utility;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ft.gameserver.GameServer;

import dbobjects.gamedb.IDSeed;
import engine.db.DBHandler;
import engine.db.DBManager;
import engine.db.client.DBType;

@Component
public final class IdCreator implements DBHandler {
	private final static int BATCH_ID_COUNT = 10000;

	public IdCreator() {
		m_nIntId = 0;
		m_nInt64 = 10000;
		m_nInt64Seg = 0;
	}

	@PostConstruct
	public void regist() {
		DBManager.regist(this);
	}

	@Override
	public void initDB() {
		m_lSeed = DBManager.getlastid(IDSeed.class, DBType.GameDB);
		m_nInt64Seg = 0;
		// TestTable data = new TestTable();
		// data.roleid = 12345;
		// data.time = TimeCreator.GetTimeStamp();
		// DBManager.saveOrUpdate(data, DBType.GameDB);
	}

	@Override
	public void atfterInitDB() {
	}
	
	/** 获取一个进程唯一的int类型id */
	public int GetTmpId32() {
		return ++m_nIntId;
	}

	/** 获取一个进程唯一的long类型id */
	public long GetTmpId64() {
		return ++m_nInt64;
	}

	/** 获取一个永久不会重复的id */
	public long GetId64() {
		if (m_nInt64Seg >= BATCH_ID_COUNT) {
			initDB();
		}
		return m_lSeed * BATCH_ID_COUNT + ++m_nInt64Seg + GameServer.serverid * 100000000000000L;
	}

	private int m_nIntId;
	private long m_nInt64;
	private long m_nInt64Seg;
	private long m_lSeed;
}
