package com.ft.gameserver.basemod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ft.gameserver.playermod.pack.PackSource;

import engine.bean.BeanFactory;

@Controller
@Scope("prototype")
public class SourceManager {
	private Map<ModType, SourceBaseMod> sources = new HashMap<>();

	@PostConstruct
	private void createMods() {
		regist(BeanFactory.getBean(PackSource.class));
	}

	public SourceBaseMod GetSource(ModType type) {
		return sources.get(type);
	}

	private void regist(SourceBaseMod mod) {
		sources.put(mod.GetType(), mod);
	}

	public void Load(Long id) {
		for (SourceBaseMod mod : sources.values()) {
			mod.LoadData(id);
		}
	}

	public void Save(long id) {
		for (SourceBaseMod mod : sources.values()) {
			mod.SaveData(id);
		}
	}

	protected long roleid;

}
