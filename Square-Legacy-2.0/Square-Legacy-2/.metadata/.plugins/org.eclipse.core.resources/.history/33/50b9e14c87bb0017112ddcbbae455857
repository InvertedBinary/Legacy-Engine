package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Sprite;

public class Invisibility extends Effect{
	
	public Invisibility(Entity e, int life) {
		this.life = life;
		this.e = e;
		this.name = "Invis";
		this.sprite = Sprite.particle_def;
		//this.sprite = Sprite.rotate(new Sprite(8, 8, 0xff000000));
	}
	
	public void effectEquip() {
		System.out.println(e + " is now invisible!");
			toggleVis(true);
	}
	
	public void effectDequip() {
		System.out.println(e + " is now visible!");
		toggleVis(false);
	}
	
	private void toggleVis(boolean b) {
		if (e instanceof PlayerMP) {
			((PlayerMP) e).setInvisible = b;
			((PlayerMP) e).speed = 1;
			((PlayerMP) e).riding = false;
			((PlayerMP) e).ridingOn = null;
			
		}
	}
	
	public void update() {
		super.update();
		
		if (time % 35 == 0) {
			if (e != null) {
		/*	level.add(new FireParticleSpawner((int) (e.x), (int) (e.y - 8), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x + 8), (int) (e.y), 2, 2, level, time));
			level.add(new FireParticleSpawner((int) (e.x - 8), (int) (e.y), 2, 2, level, time));*/
			}
			
		}
	}
	
	public void render() {
		
	}
	
}
