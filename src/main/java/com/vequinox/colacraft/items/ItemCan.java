package com.vequinox.colacraft.items;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.util.IHasModel;

public class ItemCan extends ItemBase implements IHasModel{
	private String name;
	private int baseDuration;
	private int levelModifier;

	public ItemCan(String name, int baseDuration, int levelModifier) {
		super(name);
		this.name = name;
		this.baseDuration = baseDuration;
		this.levelModifier = levelModifier;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	public String getName(){
		return this.name;
	}

	public int getBaseDuration(){
		return this.baseDuration;
	}

	public int getLevelModifier(){
		return this.levelModifier;
	}
}
