package com.IB.SL.entity.inventory;

import com.IB.SL.entity.Entity;
import com.IB.SL.graphics.Screen;


public class ActiveEffects {

	public Effect[] effects;
	private Inventory inv;
	private int firstFree;
	private Entity e;
	
	public ActiveEffects(int i, Entity e, Inventory inv) {
		effects = new Effect[i];
		firstFree = 0;
		this.e = e;
		this.inv = inv;
	}
	
	public ActiveEffects(int i, Entity e) {
		effects = new Effect[i];
		firstFree = 0;
		this.e = e;
		this.inv = null;
	}
	
	public boolean addEffect(Effect effect) {
		
		//TODO: Initialize ActiveEffects for mobs
		
		for (int i = 0; i < effects.length; i++) {
			if (effects[i] != null) {
			if (effects[i].name.equalsIgnoreCase((effect.name))) {
				effect.life += (effects[i].life - effects[i].time);
				System.out.println("New Effect Life: " + effect.life + "Old Effect Life: " + (effects[i].life - effects[i].time) + " Total: " + (effects[i].life + (effects[i].life - effects[i].time)));
				effects[i].time = 0;
				//effects[i].life = effect.life;
				effects[i].remove();
				effects[i].time = 0;
				effects[i] = effect;
				e.level.add(effect);
				effect.effectEquip();
				return true;
				}
			}
		}
		
		if (firstFree == effects.length) {
			System.out.println("Could Not Add effect: " + effect.getName() + " You have too many active effects!");
			return false;
		} 
		
		
		for (int i = firstFree; i < effects.length; i++) {
			if (effects[i] == null) {
				firstFree = i;
				System.out.println("Effect Added: " + effect.getName() + " At Index: " + i);
				effects[firstFree] = effect;
				e.level.add(effect);
				effect.effectEquip();
				return true;				
			}
			
		}
		firstFree = effects.length;
		return true;
	}

	
	public void removeEffect(Effect effect) {
		for(int i = 0; i < effects.length; i++) {
			if (effects[i] != null) {
				
			if(effects[i].name == effect.name) {
				effects[i].effectDequip();
				effects[i] = null;
				System.out.println("Effect Ended " + effect.name + " At Index " + i);
				if (i < firstFree) {
					firstFree = i;
				}
				return;
			}
			}
		}
	}
	
	public void update() {
		
		}
	
	public void render(Screen screen) {
		if (e == e.level.getClientPlayer() && inv != null) {
			inv.renderEffects(screen, effects);			
		}
	}

	public void removeAll() {
		for (int i = 0; i < effects.length; i++) {
			removeEffect(effects[i]);
		}
	}

}










