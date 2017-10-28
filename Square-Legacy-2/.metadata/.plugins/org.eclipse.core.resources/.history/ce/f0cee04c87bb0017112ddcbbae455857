package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.particle.DefaultParticle;
import com.IB.SL.graphics.Sprite;

public class WellRested extends Effect{
	
	public WellRested(Player e, int life) {
		this.life = life;
		this.e = e;
		this.name = "Rested";
		this.sprite = Sprite.WellRested;
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
			if (b) {
				((PlayerMP) e).expModif = 1.1;
				e.level.add(new DefaultParticle((int)e.x, (int)e.y, 30, 50, sprite));
			} else {
				((PlayerMP) e).expModif = 1;
				e.level.add(new DefaultParticle((int)e.x, (int)e.y, 30, 50, sprite));
			}
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
