package com.vequinox.colacraft.blocks.machines.carbonizer;

import com.vequinox.colacraft.blocks.machines.carbonizer.slots.SlotCarbonizerFuel;
import com.vequinox.colacraft.blocks.machines.carbonizer.slots.SlotCarbonizerOutput;
import com.vequinox.colacraft.init.ModItems;

import com.vequinox.colacraft.items.ItemCan;
import com.vequinox.colacraft.items.ItemSolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCarbonizer extends Container{
	private final TileEntityCarbonizer tileEntity;
	private int cookTime, totalCookTime, burnTime, currentBurnTime;
	
	public ContainerCarbonizer(InventoryPlayer player, TileEntityCarbonizer tileEntity) {
		this.tileEntity = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 44, 22));
		this.addSlotToContainer(new Slot(tileEntity, 1, 62, 22));
		
		this.addSlotToContainer(new SlotCarbonizerFuel(tileEntity, 2, 53, 58));
		this.addSlotToContainer(new SlotCarbonizerOutput(player.player, tileEntity, 3, 123, 39));
		
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
			
			if(index == 3) {//if the index is the output slot
				if(!this.mergeItemStack(currentSlotStack, 4, 40, true)) {
					return ItemStack.EMPTY;
				}
				
				currentSlot.onSlotChange(currentSlotStack, stack);
			}else if(index != 2 && index != 1 && index != 0) {// if the index is in player's inventory, not carbonizer
				if(currentSlotStack.getItem() instanceof ItemSolution) {
					if(!this.mergeItemStack(currentSlotStack, 1, 2, false)) return ItemStack.EMPTY;
				}else if(currentSlotStack.getItem() instanceof ItemCan) {
					if(!this.mergeItemStack(currentSlotStack, 0, 1, false)) return ItemStack.EMPTY;
				}else if(TileEntityCarbonizer.isItemFuel(currentSlotStack)){
					if(!this.mergeItemStack(currentSlotStack, 2, 3, false)) return ItemStack.EMPTY;
				}else if(index >= 4 && index < 31){
					if(!this.mergeItemStack(currentSlotStack, 31, 40, false)) return ItemStack.EMPTY;
				}else if(index >= 31 && index < 40 && !this.mergeItemStack(currentSlotStack, 4, 31, false)){
					return ItemStack.EMPTY;
				}
			}else if(!this.mergeItemStack(currentSlotStack, 4, 40, false)) {
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
