package com.vequinox.colacraft.blocks.machines.mixer;

import javax.annotation.Nonnull;

import com.vequinox.colacraft.blocks.machines.carbonizer.TileEntityCarbonizer;
import com.vequinox.colacraft.init.ModBlocks;
import com.vequinox.colacraft.init.ModItems;
import com.vequinox.colacraft.items.ItemFlavorPacket;
import com.vequinox.colacraft.items.ItemSolution;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import com.vequinox.colacraft.util.StackHelper;

public class TileEntityMixer extends TileEntity implements IInventory, ITickable{

	private NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);
	private List<Item> allowedDusts = getAllowedDusts();
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;

	private final int HYDRODUST_PER_WATER_LEVEL = 4;
	
	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime = 200;
	
	private int tier;

	private List<Item> getAllowedDusts(){
		List<Item> dusts = new ArrayList<>();
		dusts.add(Items.GUNPOWDER);
		dusts.add(Items.REDSTONE);
		dusts.add(Items.SUGAR);
		dusts.add(ModItems.CONDENSED_REDSTONE);
		dusts.add(ModItems.CONDENSED_SUGAR);
		dusts.add(ModItems.CRYSTALLIZED_REDSTONE);
		dusts.add(ModItems.CRYSTALLIZED_SUGAR);
		dusts.add(ModItems.HYDRODUST);
		dusts.add(ModItems.GHOST_HYDRODUST);
		dusts.add(ModItems.GHOST_REDSTONE);
		dusts.add(ModItems.GHOST_SUGAR);

		return dusts;
	}
	
	public TileEntity setTier(int tier) {
		this.tier = tier;
		return this;
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.mixer";
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.size();
	}
	
	@Override
	public boolean isEmpty() {
		for(ItemStack stack : this.inventory) {
			if(!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return (ItemStack)this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.inventory, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = (ItemStack)this.inventory.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		this.inventory.set(index, stack);

		if(stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if(index == 0 && index + 1 == 1 && index + 2 == 2 && index + 3 == 3 && !flag) {
			this.cookTime = 0;
			this.markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.inventory);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentBurnTime = TileEntityFurnace.getItemBurnTime((ItemStack)this.inventory.get(4));

		if(compound.hasKey("CustomName", 8)) {
			this.setCustomName(compound.getString("CustomName"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.burnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		ItemStackHelper.saveAllItems(compound, this.inventory);

		if(this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}

		return compound;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityMixer tileEntity) {
		return tileEntity.getField(0) > 0;
	}
	
	@Override
	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		
		if(this.isBurning()) {
			--this.burnTime;
		}
		
		if(!this.world.isRemote) {
			ItemStack fuel = this.inventory.get(4);//fuel slot
			
			if(this.isBurning() || !fuel.isEmpty() && !this.inventory.get(0).isEmpty() && (!this.inventory.get(1).isEmpty() || !this.inventory.get(2).isEmpty() || !this.inventory.get(3).isEmpty())){
				if(!this.isBurning() && this.canSmelt()) {
					this.burnTime = TileEntityFurnace.getItemBurnTime(fuel);
					this.currentBurnTime = this.burnTime;

					if(this.isBurning()) {
						flag1 = true;
						
						if(!fuel.isEmpty()) {
							Item fuelItem = fuel.getItem();
							fuel.shrink(1);
	
							if(fuel.isEmpty()) {
								ItemStack item1 = fuelItem.getContainerItem(fuel);
								this.inventory.set(4, item1);
							}
						}
					}
				}
				
				if(this.isBurning() && this.canSmelt()) {
					++this.cookTime;
					
					if(this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				}else {
					this.cookTime = 0;
				}
			}else if(!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			
			if(flag != this.isBurning()) {
				flag1 = true;
				BlockMixer.setState(this.isBurning(), this.world, this.pos, getMixerType());
			}
		}
		
		if(flag1) {
			this.markDirty();
		}
	}
	
	private Block getMixerType() {
		return ModBlocks.MIXER;
	}

	private boolean canSmelt() {
		boolean canSmelt = true;
		if((this.inventory.get(0)).isEmpty() || 
				((this.inventory.get(1)).isEmpty() && this.inventory.get(2).isEmpty() && this.inventory.get(3).isEmpty())) {
			canSmelt = false;
		}else if(!(this.inventory.get(0).getItem() instanceof ItemSolution)){
			canSmelt = false;
		}else if(StackHelper.hasKey(this.inventory.get(0), "powder_parts") && getPotentialPowderCount() > ((ItemSolution)this.inventory.get(0).getItem()).getMaxPowderParts() - StackHelper.getTag(this.inventory.get(0)).getInteger("powder_parts")) {
			canSmelt = false;
		}else if(!StackHelper.hasKey(this.inventory.get(0), "powder_parts") && getPotentialPowderCount() > ((ItemSolution)this.inventory.get(0).getItem()).getMaxPowderParts()){
			canSmelt = false;
		}else if(StackHelper.hasKey(this.inventory.get(0),"water_parts") && getFlavorPacketCount() > StackHelper.getTag(this.inventory.get(0)).getInteger("water_parts")) {
			canSmelt = false;
		}else if(!StackHelper.hasKey(this.inventory.get(0), "water_parts") && getFlavorPacketCount() > ((ItemSolution)this.inventory.get(0).getItem()).getStartingWaterParts()) {
			canSmelt = false;
		}else if(!this.inventory.get(5).isEmpty()){
			List<ItemStack> ingredients = Arrays.asList(this.inventory.get(1), this.inventory.get(2), this.inventory.get(3));
			ItemStack potentialResult = getNewSolution(this.inventory.get(0), ingredients);
			if(!ItemStack.areItemStackTagsEqual(potentialResult, this.inventory.get(5))){
				canSmelt = false;
			}
		}else {

			for(int i = 1; i < 4; i++) {
				ItemStack ingredient = this.inventory.get(i);
				if(!ingredient.isEmpty()) {
					if(!(ingredient.getItem() instanceof ItemFlavorPacket) && !allowedDusts.contains(ingredient.getItem())) {
						canSmelt = false;
					}
				}
			}
		}
		
		return canSmelt;
	}

	private int getHydrodustTotal(){
		int hydrodustAmount = StackHelper.getTag(this.inventory.get(0)).getInteger("hydrodust_amount");
		for(int i = 1; i < 4; i++){
			if(this.inventory.get(i).getItem() == ModItems.HYDRODUST){
				hydrodustAmount += this.inventory.get(i).getCount();
			}
		}
		return hydrodustAmount;
	}
	
	private int getPotentialPowderCount() {
		int powderCount = 0;
		for(int i = 1; i < 4; i++) {
			Item inventoryItem = this.inventory.get(i).getItem();
			if(allowedDusts.contains(inventoryItem)) {
				if(inventoryItem == ModItems.CONDENSED_REDSTONE || inventoryItem == ModItems.CONDENSED_SUGAR || inventoryItem == ModItems.CRYSTALLIZED_SUGAR || inventoryItem == ModItems.CRYSTALLIZED_REDSTONE){
					powderCount += (this.inventory.get(i).getCount() * 8);
				}else if(inventoryItem == ModItems.HYDRODUST){
					powderCount += (this.inventory.get(i).getCount() * 4);
				}else if(inventoryItem != ModItems.GHOST_REDSTONE && inventoryItem != ModItems.GHOST_SUGAR && inventoryItem != ModItems.GHOST_HYDRODUST){
					powderCount += this.inventory.get(i).getCount();
				}
			}
		}

		return powderCount;
	}

	private int getFlavorPacketCount() {
		int flavorPacketCount = 0;
		for(int i = 1; i < 4; i++) {
			if(this.inventory.get(i).getItem() instanceof ItemFlavorPacket) {
				flavorPacketCount += this.inventory.get(i).getCount();
			}
		}

		return flavorPacketCount;
	}
	
	private ItemStack getNewSolution(ItemStack solution, List<ItemStack> ingredients) {
		ItemStack sol = solution.copy();
		sol.setCount(1);
		int sugarAmount = 0;
		int redstoneAmount = 0;
		int gunpowderAmount = 0;
		int hydrodustAmount = 0;
		int totalPowderAmount = 0;
		Map<ItemFlavorPacket, Integer> packets = new HashMap<>();
		
		NBTTagCompound solutionTag = StackHelper.getTag(sol);
		if(!StackHelper.hasKey(sol, "water_parts")) {
			solutionTag.setInteger("water_parts", ((ItemSolution) sol.getItem()).getStartingWaterParts());
		}
		
		if(!StackHelper.hasKey(sol, "powder_parts")) {
			solutionTag.setInteger("powder_parts", 0);
		}
		
		for(ItemStack ingredient : ingredients) {
			if(!ingredient.isEmpty()) {
				if(ingredient.getItem() instanceof ItemFlavorPacket) {
					packets.put((ItemFlavorPacket)ingredient.getItem(), ingredient.getCount());
				}else if(ingredient.getItem() == Items.SUGAR) {
					sugarAmount += ingredient.getCount();
					totalPowderAmount += ingredient.getCount();
				}else if(ingredient.getItem() == ModItems.CONDENSED_SUGAR){
					sugarAmount += (ingredient.getCount() * 8);
					totalPowderAmount += (ingredient.getCount() * 8);
				}else if(ingredient.getItem() == ModItems.CRYSTALLIZED_SUGAR) {
					sugarAmount += (ingredient.getCount() * 16);
					totalPowderAmount += (ingredient.getCount() * 8);
				}else if(ingredient.getItem() == ModItems.GHOST_SUGAR){
					sugarAmount += ingredient.getCount();
				}else if(ingredient.getItem() == Items.REDSTONE) {
					redstoneAmount += ingredient.getCount();
					totalPowderAmount += ingredient.getCount();
				}else if(ingredient.getItem() == ModItems.CONDENSED_REDSTONE) {
					redstoneAmount += (ingredient.getCount() * 8);
					totalPowderAmount += (ingredient.getCount() * 8);
				}else if(ingredient.getItem() == ModItems.CRYSTALLIZED_REDSTONE) {
					redstoneAmount += (ingredient.getCount() * 40);
					totalPowderAmount += (ingredient.getCount() * 8);
				}else if(ingredient.getItem() == ModItems.GHOST_REDSTONE){
					redstoneAmount += ingredient.getCount();
				}else if(ingredient.getItem() == Items.GUNPOWDER) {
					gunpowderAmount += ingredient.getCount();
					totalPowderAmount += ingredient.getCount();
				}else if(ingredient.getItem() == ModItems.HYDRODUST) {
					hydrodustAmount += ingredient.getCount();
					totalPowderAmount += ingredient.getCount() * 4;
				}else if(ingredient.getItem() == ModItems.GHOST_HYDRODUST){
					hydrodustAmount += ingredient.getCount();
				}
			}
		}

		int totalWaterParts = getTotalWaterParts(sol, solutionTag);

		for(ItemFlavorPacket packet : packets.keySet()) {
			String packetName = packet.getName();
			solutionTag.setInteger(packetName, solutionTag.getInteger(packetName) + packets.get(packet));
			solutionTag.setInteger("water_parts", solutionTag.getInteger("water_parts") - packets.get(packet));
		}

		solutionTag.setInteger("sugar_amount", solutionTag.getInteger("sugar_amount") + sugarAmount);
		solutionTag.setInteger("redstone_amount", solutionTag.getInteger("redstone_amount") + redstoneAmount);
		solutionTag.setInteger("gunpowder_amount", solutionTag.getInteger("gunpowder_amount") + gunpowderAmount);
		solutionTag.setInteger("hydrodust_amount", solutionTag.getInteger("hydrodust_amount") + hydrodustAmount);
		
		solutionTag.setInteger("powder_parts", solutionTag.getInteger("powder_parts") + totalPowderAmount);

		int newTotalWaterParts = getTotalWaterParts(sol, solutionTag);
		solutionTag.setInteger("water_parts", solutionTag.getInteger("water_parts") + (newTotalWaterParts - totalWaterParts));
		
		return sol;
	}

	private int getTotalWaterParts(ItemStack solution, NBTTagCompound solutionTag){
		return ((ItemSolution)solution.getItem()).getStartingWaterParts() + solutionTag.getInteger("hydrodust_amount") / HYDRODUST_PER_WATER_LEVEL;
	}
	
	public void smeltItem() {
		if(this.canSmelt()) {
			ItemStack solution = this.inventory.get(0);
			ItemStack ingredient1 = this.inventory.get(1);
			ItemStack ingredient2 = this.inventory.get(2);
			ItemStack ingredient3 = this.inventory.get(3);
			ItemStack output = this.inventory.get(5);
			List<ItemStack> ingredients = Arrays.asList(ingredient1, ingredient2, ingredient3);
			ItemStack result = getNewSolution(solution, ingredients);
			
			if(output.isEmpty()) {
				this.inventory.set(5, result);
			}else{
				this.inventory.get(5).grow(1);
			}
			
			for(ItemStack ingredient : ingredients) {
				if (!ingredient.isEmpty()) {
					ingredient.shrink(ingredient.getCount());
				}
			}
			solution.shrink(1);
		}
	}
	
	public static boolean isItemFuel(ItemStack fuel) {
		return TileEntityFurnace.getItemBurnTime(fuel) > 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX()+0.5D, (double)this.pos.getY()+0.5D, (double)this.pos.getZ()+0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 5) {
			return false;
		}else if(index != 4) {
			if(index == 0 && stack.getItem() instanceof ItemSolution) {
				return true;
			}else if((index == 1 || index == 2 || index == 3) && (allowedDusts.contains(stack.getItem()) || stack.getItem() instanceof ItemFlavorPacket)) {
				return true;
			}else {
				return false;
			}
		}else {
			return isItemFuel(stack);
		}
	}
	
	@Nonnull
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player) {
		return new ContainerMixer(playerInventory, this);
	}

	public String getGuiID() {
		return "colacraft:mixer";
	}

	public int getField(int id) {
		switch(id) {
		case 0:
			return this.burnTime;
		case 1:
			return this.currentBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}
	
	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}
	
}
