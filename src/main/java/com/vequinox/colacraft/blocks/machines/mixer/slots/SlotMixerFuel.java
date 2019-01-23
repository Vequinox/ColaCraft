package com.vequinox.colacraft.blocks.machines.mixer.slots;

import com.vequinox.colacraft.blocks.machines.mixer.TileEntityMixer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMixerFuel extends Slot{
	public SlotMixerFuel(IInventory inventory, int index, int x, int y){
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityMixer.isItemFuel(stack);
	}
	
	@Override 
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
