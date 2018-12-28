package com.vequinox.colacraft.util;

import com.vequinox.colacraft.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ColaCraftTab extends CreativeTabs{
	public ColaCraftTab() {
		super(Reference.NAME);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.CAN);
	}
}
