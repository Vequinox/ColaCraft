package com.vequinox.colacraft.blocks.machines.carbonizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.vequinox.colacraft.init.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CarbonizerRecipes {
	private static final CarbonizerRecipes INSTANCE = new CarbonizerRecipes();
	private final Map<List<ItemStack>, ItemStack> smeltingList = Maps.<List<ItemStack>, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static CarbonizerRecipes getInstance() {
		return INSTANCE;
	}
	
	private CarbonizerRecipes() {
		addCarbonizingRecipe(
				new ItemStack(Items.CARROT),
				new ItemStack(Items.APPLE),
				new ItemStack(Items.GOLDEN_AXE),
				new ItemStack(Items.BAKED_POTATO),
				new ItemStack(Items.ARROW),
				5.0F
		);
	}
	
	
	public void addCarbonizingRecipe(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack result, float experience) {
		if(getCarbonizingResult(input1, input2, input3, input4) != ItemStack.EMPTY) {
			return;
		}
		
		List<ItemStack> ingredients = new ArrayList<ItemStack>();
		ingredients.add(input1);
		ingredients.add(input2);
		ingredients.add(input3);
		ingredients.add(input4);
		this.smeltingList.put(ingredients, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getCarbonizingResult(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4) {
		ItemStack result = ItemStack.EMPTY;
		
		for(List<ItemStack> recipeItemStacks : this.smeltingList.keySet()) {
			if(ItemStack.areItemsEqual(recipeItemStacks.get(0), input1) && 
					ItemStack.areItemsEqual(recipeItemStacks.get(1), input2) && 
					ItemStack.areItemsEqual(recipeItemStacks.get(2), input3) && 
					ItemStack.areItemsEqual(recipeItemStacks.get(3), input4)){
				
				result = this.smeltingList.get(recipeItemStacks);
			}
		}
		
		return result;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map<List<ItemStack>, ItemStack> getSmeltingList(){
		return this.smeltingList;
	}
	
	public float getCarbonizingExperience(ItemStack stack) {
		for(Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())){
				return ((Float)entry.getValue()).floatValue();
			}
		}
		
		return 0.0F;
	}
}
