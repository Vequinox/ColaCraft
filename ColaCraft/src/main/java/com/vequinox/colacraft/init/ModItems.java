package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.items.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Item CAN = new ItemBase("can", CreativeTabs.MATERIALS);
	public static final Item SUGAR_WATER = new ItemBase("sugar_water", CreativeTabs.MATERIALS);
	public static final Item CARBONATED_WATER = new ItemBase("carbonated_water", CreativeTabs.MATERIALS);
}
