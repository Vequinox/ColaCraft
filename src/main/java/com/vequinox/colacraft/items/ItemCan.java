package com.vequinox.colacraft.items;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;
import net.minecraft.item.ItemStack;

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

	public ItemStack getSodaType(){
		switch(this.name){
			case "can":
				return new ItemStack(ModItems.SODA);
			case "gold_can":
				return new ItemStack(ModItems.GOLD_SODA);
			case "holy_can":
				return new ItemStack(ModItems.HOLY_SODA);
			case "demonic_can":
				return new ItemStack(ModItems.DEMONIC_SODA);
			case "ancient_can":
				return new ItemStack(ModItems.ANCIENT_SODA);
			default:
				return new ItemStack(ModItems.SODA);
		}
	}

	public int getBaseDuration(){
		return this.baseDuration;
	}

	public int getLevelModifier(){
		return this.levelModifier;
	}
}
