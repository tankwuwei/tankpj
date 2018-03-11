package com.ft.gameserver.basemod;

public abstract class SourceBaseMod {

	public ModType GetType() {
		return ModType;
	}

	abstract protected void LoadData(long id);

	abstract protected void SaveData(long id);

	protected ModType ModType;
}
