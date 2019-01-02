package com.vequinox.colacraft.blocks.machines.carbonizer.slots;

import com.vequinox.colacraft.blocks.machines.carbonizer.TileEntityCarbonizer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCarbonizerFuel extends Slot{
	public SlotCarbonizerFuel(IInventory inventory, int index, int x, int y){
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityCarbonizer.isItemFuel(stack);
	}
	
	@Override 
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
