package com.vequinox.colacraft.util;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Credit: GirafiStudios **/

public class StackHelper {

    /**
     * Gets the NBTTagCompound from the ItemStack.
     * If the ItemStack does not have any NBTTagCompounds, a new empty one will be given
     *
     * @param stack the stack you wish to check the NBTTagCompound of
     * @return the stacks tag
     */
    public static NBTTagCompound getTag(@Nonnull ItemStack stack) {
        if (!hasTag(stack)) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    /**
     * Checks if the ItemStack have a NBTTagCompound associated with it
     *
     * @param stack the stack
     * @return whether or not the stack have a tag
     */
    public static boolean hasTag(@Nonnull ItemStack stack) {
        return stack.hasTagCompound();
    }

    /**
     * Checks if the ItemStack have the specified NBTTagCompound key
     *
     * @param stack  the stack
     * @param string the NBTTagCompound string key
     * @return whether the stack have the key or not
     */
    public static boolean hasKey(@Nonnull ItemStack stack, String string) {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey(string);
    }

}