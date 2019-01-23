package com.vequinox.colacraft.util;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionEffectWrapper{
	private PotionEffect potionEffect;
	private int durationProgress;
	private int amplifierProgress;
	
	public PotionEffectWrapper(PotionEffect potionEffect) {
		this.potionEffect = potionEffect;
		this.durationProgress = 0;
		this.amplifierProgress = 0;
	}
	
	public int getDurationProgress() {
		return this.durationProgress;
	}
	
	public int getAmplifierProgress() {
		return this.amplifierProgress;
	}
	
	public int getPotionEffectDuration() {
		return this.potionEffect.getDuration();
	}
	
	public int getPotionEffectAmplifier() {
		return this.potionEffect.getAmplifier();
	}
	
	public Potion getPotionEffectPotion() {
		return this.potionEffect.getPotion();
	}
	
	public PotionEffect getPotionEffect() {
		return this.potionEffect;
	}
	
	public void setPotionEffectDuration(int newDuration) {
		this.potionEffect = new PotionEffect(getPotionEffectPotion(), newDuration, getPotionEffectAmplifier());
	}
	
	public void setPotionEffectAmplifier(int newAmplifier) {
		this.potionEffect = new PotionEffect(getPotionEffectPotion(), getPotionEffectDuration(), newAmplifier);
	}
	
	public void addDurationProgress(int amount) {
		this.durationProgress += amount;
	}
	
	public void addAmplifierProgress(int amount) {
		this.amplifierProgress += amount;
	}

}
