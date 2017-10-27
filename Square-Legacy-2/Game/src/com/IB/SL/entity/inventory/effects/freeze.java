package com.IB.SL.entity.inventory.effects;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class freeze extends Effect{
	int b = 80;
	int time = 0;
	public freeze(Entity e, int life) {
		this.life = life;
		this.e = e;
		this.name = "Frozen";
		this.sprite = Sprite.display_Frozen;
	}
	
	public void effectEquip() {
		e.speed = 0;
		System.out.println(e + " is now frozen!");
	}
	
	public void effectDequip() {
		e.speed = 1;
		System.out.println(e + " is no longer frozen!");
	}
	
	public void update() {
		super.update();
		time++;
		if (time % 30 == 0) {
			
		if (b > 40) {
			b -= 20;
		}
			time = 0;
		}
		
	}
	
	public void render(Screen screen) {
		if (e instanceof PlayerMP) {
			((PlayerMP)e).sprinting = false;
			((PlayerMP)e).input.Sprint = false;
			screen.renderLight((int) e.getX()- 200, (int) e.getY() - 200, 200, 10, 20, b + 80);
		}
	}
	
}
