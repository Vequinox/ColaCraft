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
import net.minecraft.nbt.NBTTagCompound;
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
				NBTTagCompound tagComp = stack.getTagCompound();
				for (String key : tagComp.getKeySet()) {
					if(key.contains("level") && getMobEffect(key) != null){
						player.addPotionEffect(new PotionEffect(getMobEffect(key), tagComp.getInteger("duration"), tagComp.getInteger(key)));
					}
				}
			}
        }
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	private Potion getMobEffect(String key){
		switch(key){
			case "white_flavor_packet_level":
				return MobEffects.SPEED;
			case "black_flavor_packet_level":
				return MobEffects.SLOWNESS;
			case "light_blue_flavor_packet_level":
				return MobEffects.HASTE;
			case "magenta_flavor_packet_level":
				return MobEffects.MINING_FATIGUE;
			case "brown_flavor_packet_level":
				return MobEffects.STRENGTH;
			case "red_flavor_packet_level":
				return MobEffects.INSTANT_HEALTH;
			case "minecraft:instant_damage"://does not exist yet as a flavor packet
				return MobEffects.INSTANT_DAMAGE;
			case "minecraft:jump_boost"://does not exist yet as a flavor packet
				return MobEffects.JUMP_BOOST;
			case "cyan_flavor_packet_level":
				return MobEffects.NAUSEA;
			case "pink_flavor_packet_level":
				return MobEffects.REGENERATION;
			case "minecraft:resistance"://does not exist yet as a flavor packet
				return MobEffects.RESISTANCE;
			case "orange_flavor_packet_level":
				return MobEffects.FIRE_RESISTANCE;
			case "blue_flavor_packet_level":
				return MobEffects.WATER_BREATHING;
			case "minecraft:invisibility"://does not exist yet as a flavor packet
				return MobEffects.INVISIBILITY;
			case "gray_flavor_packet_level":
				return MobEffects.BLINDNESS;
			case "lime_flavor_packet_level":
				return MobEffects.NIGHT_VISION;
			case "green_flavor_packet_level":
				return MobEffects.HUNGER;
			case "purple_flavor_packet_level":
				return MobEffects.WEAKNESS;
			case "yellow_flavor_packet_level":
				return MobEffects.POISON;
			case "light_gray_flavor_packet_level":
				return MobEffects.WITHER;
			case "minecraft:health_boost"://does not exist yet as a flavor packet
				return MobEffects.HEALTH_BOOST;
			case "minecraft:absorption"://does not exist yet as a flavor packet
				return MobEffects.ABSORPTION;
			case "minecraft:saturation"://does not exist yet as a flavor packet
				return MobEffects.SATURATION;
			case "minecraft:glowing"://does not exist yet as a flavor packet
				return MobEffects.GLOWING;
			case "minecraft:levitation"://does not exist yet as a flavor packet
				return MobEffects.LEVITATION;
			case "minecraft:luck"://does not exist yet as a flavor packet
				return MobEffects.LUCK;
			case "minecraft:unluck"://does not exist yet as a flavor packet
				return MobEffects.UNLUCK;
			default:
				return null;
		}
	}
}
