package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;
import com.vequinox.colacraft.util.PotionEffectWrapper;

import com.vequinox.colacraft.util.Reference;
import com.vequinox.colacraft.util.StackHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSoda extends ItemFood implements IHasModel{

	public ItemSoda(String name, int hunger, float saturation, boolean isWolfFood) {
		super(hunger, saturation, isWolfFood);
		setAlwaysEdible();
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(Reference.TAB);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (!worldIn.isRemote){
			if(StackHelper.hasTag(stack)) {
				for (String key : stack.getTagCompound().getKeySet()) {
					//player.addPotionEffect(new PotionEffect())
					System.out.println(key);
				}
			}
        }
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
