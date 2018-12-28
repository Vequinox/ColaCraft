package com.vequinox.colacraft.init;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.items.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final Item CAN = new ItemBase("can");
	public static final Item SUGAR_WATER = new ItemBase("sugar_water");
	
	public static final Item ALBIUM_INGOT = new ItemBase("albium_ingot");
	public static final Item CERASIUM_INGOT = new ItemBase("cerasium_ingot");
	public static final Item CINERIUM_INGOT = new ItemBase("cinerium_ingot");
	public static final Item LUXIUM_INGOT = new ItemBase("luxium_ingot");
	public static final Item TENEBRIUM_INGOT = new ItemBase("tenebrium_ingot");
}
