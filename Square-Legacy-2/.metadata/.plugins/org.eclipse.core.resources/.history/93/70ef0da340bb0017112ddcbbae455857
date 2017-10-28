package com.IB.SL.entity.inventory.effects;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class Stun extends Effect{
	int b = 80;
	
	public Stun(Entity e, int life) {
		this.life = life;
		this.e = e;
		this.name = "Stun";
		this.sprite = Sprite.display_Stunned;
	}
	
	public void effectEquip() {
		e.speed = 0;
		Game.getGame().getPlayer().isStunned = true;
		System.out.println(e + " is now stunned!");
	}
	
	public void effectDequip() {
		e.speed = 1;
		Game.getGame().getPlayer().isStunned = false;
		System.out.println(e + " is no longer stunned!");
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
			screen.renderLight((int) e.getX()- 200, (int) e.getY() - 200, 200, 10 + b, 10 + b, 20);
		}
	}
	
}
