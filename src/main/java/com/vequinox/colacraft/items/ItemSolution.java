package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.PotionEffectWrapper;

import com.vequinox.colacraft.util.StackHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

public class ItemSolution extends ItemBase{

	private int waterParts;
	private int maxPowderAmount;

	public ItemSolution(String name, int waterParts, int maxPowderAmount) {
		super(name);
		this.waterParts = waterParts;
		this.maxPowderAmount = maxPowderAmount;
	}
	
	public int getStartingWaterParts() {
		return this.waterParts;
	}
	
	public int getMaxPowderParts() {
		return this.maxPowderAmount;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound tag = StackHelper.getTag(stack);
		int tooltipWaterParts = waterParts;

		if(Keyboard.isKeyDown(42)){
			if(tag.hasKey("water_parts")){
				tooltipWaterParts = tag.getInteger("water_parts");
			}
			tooltip.add(tooltipWaterParts + " part(s) Water");

			for(String key : tag.getKeySet()){
				if(key.contains("packet")){
					tooltip.add(tag.getInteger(key) + " part(s) " + getMobEffectName(key));
				}
			}

			tooltip.add("------------------------");

			if(tag.hasKey("powder_parts")){
				tooltip.add((maxPowderAmount - tag.getInteger("powder_parts")) + " unit(s) for Powder available");
			}else{
				tooltip.add((maxPowderAmount + " unit(s) for Powder available"));
			}

			for(String key : tag.getKeySet()){
				if(key.contains("amount")){
					tooltip.add(tag.getInteger(key) + " unit(s) of " + getPowderNameFromTagKey(key));
				}
			}
		}else{
			tooltip.add("Hold [SHIFT] for more info");
		}


	}

	private String getPowderNameFromTagKey(String tagKey){
		switch(tagKey){
			case "sugar_amount":
				return "Sugar";
			case "redstone_amount":
				return "Redstone";
			case "gunpowder_amount":
				return "Gunpowder";
			case "hydrodust_amount":
				return "Hydrodust";
			default:
				return "Unknown";
		}
	}

	private String getMobEffectName(String tagKey){
		switch(tagKey){
			case "white_flavor_packet":
				return I18n.format(MobEffects.SPEED.getName());
			case "black_flavor_packet":
				return I18n.format(MobEffects.SLOWNESS.getName());
			case "light_blue_flavor_packet":
				return I18n.format(MobEffects.HASTE.getName());
			case "magenta_flavor_packet":
				return I18n.format(MobEffects.MINING_FATIGUE.getName());
			case "brown_flavor_packet":
				return I18n.format(MobEffects.STRENGTH.getName());
			case "red_flavor_packet":
				return I18n.format(MobEffects.INSTANT_HEALTH.getName());
			case "instant_damage_flavor_packet":
				return I18n.format(MobEffects.INSTANT_DAMAGE.getName());
			case "jump_boost_flavor_packet":
				return I18n.format(MobEffects.JUMP_BOOST.getName());
			case "cyan_flavor_packet":
				return I18n.format(MobEffects.NAUSEA.getName());
			case "pink_flavor_packet":
				return I18n.format(MobEffects.REGENERATION.getName());
			case "resistance_flavor_packet":
				return I18n.format(MobEffects.RESISTANCE.getName());
			case "orange_flavor_packet":
				return I18n.format(MobEffects.FIRE_RESISTANCE.getName());
			case "blue_flavor_packet":
				return I18n.format(MobEffects.WATER_BREATHING.getName());
			case "invisibility_flavor_packet":
				return I18n.format(MobEffects.INVISIBILITY.getName());
			case "gray_flavor_packet":
				return I18n.format(MobEffects.BLINDNESS.getName());
			case "lime_flavor_packet":
				return I18n.format(MobEffects.NIGHT_VISION.getName());
			case "green_flavor_packet":
				return I18n.format(MobEffects.HUNGER.getName());
			case "purple_flavor_packet":
				return I18n.format(MobEffects.WEAKNESS.getName());
			case "yellow_flavor_packet":
				return I18n.format(MobEffects.POISON.getName());
			case "light_gray_flavor_packet":
				return I18n.format(MobEffects.WITHER.getName());
			case "health_boost_flavor_packet":
				return I18n.format(MobEffects.HEALTH_BOOST.getName());
			case "absorption_flavor_packet":
				return I18n.format(MobEffects.ABSORPTION.getName());
			case "saturation_flavor_packet":
				return I18n.format(MobEffects.SATURATION.getName());
			case "glowing_flavor_packet":
				return I18n.format(MobEffects.GLOWING.getName());
			case "levitation_flavor_packet":
				return I18n.format(MobEffects.LEVITATION.getName());
			case "luck_flavor_packet":
				return I18n.format(MobEffects.LUCK.getName());
			case "unluck_flavor_packet":
				return I18n.format(MobEffects.UNLUCK.getName());
			default:
				return null;
		}
	}

}
