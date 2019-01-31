package com.vequinox.colacraft.blocks.machines.mixer;

import com.vequinox.colacraft.blocks.machines.mixer.slots.SlotMixerFuel;
import com.vequinox.colacraft.blocks.machines.mixer.slots.SlotMixerOutput;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.items.ItemFlavorPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerMixer extends Container{
	private final TileEntityMixer tileEntity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerMixer(InventoryPlayer player, TileEntityMixer tileEntity) {
		this.tileEntity = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 51, 19));
		this.addSlotToContainer(new Slot(tileEntity, 1, 74, 19));
		this.addSlotToContainer(new Slot(tileEntity, 2, 92, 19));
		this.addSlotToContainer(new Slot(tileEntity, 3, 110, 19));
		
		this.addSlotToContainer(new SlotMixerFuel(tileEntity, 4, 51, 56));
		this.addSlotToContainer(new SlotMixerOutput(player.player, tileEntity, 5, 106, 52));
		
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(player, x, 8 + x*18, 142));
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			
			if(this.cookTime != this.tileEntity.getField(2)) {
				listener.sendWindowProperty(this, 2, this.tileEntity.getField(2));
			}
			
			if(this.burnTime != this.tileEntity.getField(0)) {
				listener.sendWindowProperty(this, 0, this.tileEntity.getField(0));
			}
			
			if(this.currentBurnTime != this.tileEntity.getField(1)) {
				listener.sendWindowProperty(this, 1, this.tileEntity.getField(1));
			}
			
			if(this.totalCookTime != this.tileEntity.getField(3)) {
				listener.sendWindowProperty(this, 3, this.tileEntity.getField(3));
			}
		}
		
		this.cookTime = this.tileEntity.getField(2);
		this.burnTime = this.tileEntity.getField(0);
		this.currentBurnTime = this.tileEntity.getField(1);
		this.totalCookTime = this.tileEntity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileEntity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntity.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot currentSlot = (Slot)this.inventorySlots.get(index);
		
		if(currentSlot != null && currentSlot.getHasStack()) {
			ItemStack currentSlotStack = currentSlot.getStack();
			stack = currentSlotStack.copy();
			
			if(index == 5) {//if the index is the output slot
				if(!this.mergeItemStack(currentSlotStack, 6, 42, true)) {
					return ItemStack.EMPTY;
				}
				
				currentSlot.onSlotChange(currentSlotStack, stack);
			}else if(index != 4 && index != 3 && index != 2 && index != 1 && index != 0) {
				if(currentSlotStack.getItem() == ModItems.BASE_SOLUTION) {
					if(!this.mergeItemStack(currentSlotStack, 0, 1, false)) return ItemStack.EMPTY;
				}else if(currentSlotStack.getItem() instanceof ItemFlavorPacket || 
						currentSlotStack.getItem() == Items.SUGAR || 
						currentSlotStack.getItem() == Items.REDSTONE ||
						currentSlotStack.getItem() == Items.GUNPOWDER ||
						currentSlotStack.getItem() == Items.GLOWSTONE_DUST) {
					if(!this.mergeItemStack(currentSlotStack, 1, 4, false)) { 
						return ItemStack.EMPTY;
					}
				}else if(TileEntityMixer.isItemFuel(currentSlotStack)){
					if(!this.mergeItemStack(currentSlotStack, 4, 5, false)) return ItemStack.EMPTY;
				}else if(index >= 6 && index < 33){
					if(!this.mergeItemStack(currentSlotStack, 33, 42, false)) return ItemStack.EMPTY;
				}else if(index >= 33 && index < 42 && !this.mergeItemStack(currentSlotStack, 6, 33, false)){
					return ItemStack.EMPTY;
				}
			}else if(!this.mergeItemStack(currentSlotStack, 6, 42, false)) {
				return ItemStack.EMPTY;
			}
			
			if(currentSlotStack.isEmpty()) {
				currentSlot.putStack(ItemStack.EMPTY);
			}else {
				currentSlot.onSlotChanged();
			}
			
			if(currentSlotStack.getCount() == stack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			currentSlot.onTake(player, currentSlotStack);
				
		}
		
		return stack;
	}
	
	
	
}
