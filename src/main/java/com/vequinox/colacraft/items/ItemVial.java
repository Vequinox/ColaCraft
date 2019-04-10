package com.vequinox.colacraft.items;

import com.vequinox.colacraft.Main;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.util.IHasModel;
import com.vequinox.colacraft.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemVial extends Item implements IHasModel{

	private String name;

	public ItemVial(String name) {
		this.name = name;
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(Reference.TAB);
		ModItems.ITEMS.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (raytraceresult == null){
			return new ActionResult(EnumActionResult.PASS, itemstack);
		}else{
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK){
				BlockPos blockpos = raytraceresult.getBlockPos();

				if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack)){
					return new ActionResult(EnumActionResult.PASS, itemstack);
				}

				if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER){
					worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 2.0F);
					return new ActionResult(EnumActionResult.SUCCESS, this.fillVialWithWater(itemstack, playerIn));
				}
			}

			return new ActionResult(EnumActionResult.PASS, itemstack);
		}
	}

	private ItemStack fillVialWithWater(ItemStack emptyVial, EntityPlayer player){
		emptyVial.shrink(1);
		ItemStack filledVial = new ItemStack(getFilledVial());

		if (emptyVial.isEmpty()){
			return filledVial;
		}else{
			if (!player.inventory.addItemStackToInventory(filledVial)){
				player.dropItem(filledVial, false);
			}

			return emptyVial;
		}
	}

	private Item getFilledVial(){
		Item vial = ModItems.SMALL_VIAL_WATER;
		if(this.name.equalsIgnoreCase("empty_vial")){
			vial = ModItems.VIAL_WATER;
		}else if(this.name.equalsIgnoreCase("empty_large_vial")){
			vial = ModItems.LARGE_VIAL_WATER;
		}

		return vial;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
