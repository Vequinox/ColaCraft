package com.vequinox.colacraft.util;

import com.vequinox.colacraft.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ColaCraftTab extends CreativeTabs{
	public ColaCraftTab() {
		super(Reference.MOD_ID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.CAN);
	}
}
