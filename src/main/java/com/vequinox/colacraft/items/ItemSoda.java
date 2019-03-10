package com.vequinox.colacraft.items;

import java.util.ArrayList;
import java.util.List;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;
import com.vequinox.colacraft.util.PotionEffectWrapper;

import com.vequinox.colacraft.util.Reference;
import com.vequinox.colacraft.util.StackHelper;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoda extends ItemFood implements IHasModel{
	private String name;

	public ItemSoda(String name, int hunger, float saturation, boolean isWolfFood) {
		super(hunger, saturation, isWolfFood);
		this.name = name;
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
			Item emptyCan = ((ItemSoda)stack.getItem()).getCanType();
			if(!player.inventory.addItemStackToInventory(new ItemStack(emptyCan))){
				player.dropItem(new ItemStack(emptyCan), false);
			}
        }
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	private Item getCanType(){
		switch(this.name){
			case "soda":
				return ModItems.CAN;
			//case "gold_soda":
			//	return ModItems.GOLD_CAN;
			//case "holy_soda":
			//	return ModItems.HOLY_CAN;
			//case "demonic_soda":
			//	return ModItems.DEMONIC_CAN;
			//case "ancient_soda":
			//	return ModItems.ANCIENT_CAN;
			default:
				return ModItems.CAN;
		}
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
			case "instant_damage_flavor_packet_level":
				return MobEffects.INSTANT_DAMAGE;
			case "jump_boost_flavor_packet_level":
				return MobEffects.JUMP_BOOST;
			case "cyan_flavor_packet_level":
				return MobEffects.NAUSEA;
			case "pink_flavor_packet_level":
				return MobEffects.REGENERATION;
			case "resistance_flavor_packet_level":
				return MobEffects.RESISTANCE;
			case "orange_flavor_packet_level":
				return MobEffects.FIRE_RESISTANCE;
			case "blue_flavor_packet_level":
				return MobEffects.WATER_BREATHING;
			case "invisibility_flavor_packet_level":
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
			case "health_boost_flavor_packet_level":
				return MobEffects.HEALTH_BOOST;
			case "absorption_flavor_packet_level":
				return MobEffects.ABSORPTION;
			case "saturation_flavor_packet_level":
				return MobEffects.SATURATION;
			case "glowing_flavor_packet_level":
				return MobEffects.GLOWING;
			case "levitation_flavor_packet_level":
				return MobEffects.LEVITATION;
			case "luck_flavor_packet_level":
				return MobEffects.LUCK;
			case "unluck_flavor_packet_level":
				return MobEffects.UNLUCK;
			default:
				return null;
		}
	}
}
