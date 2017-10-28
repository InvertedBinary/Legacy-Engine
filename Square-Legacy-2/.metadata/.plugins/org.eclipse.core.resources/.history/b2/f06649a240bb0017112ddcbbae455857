package com.IB.SL.entity.inventory.effects;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.Effect;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;



public class Resist extends Effect{
	int b = 80;
	int time = 0;
	int magnitude;
	
	static boolean fortified = false;
	
	public Resist(Entity e, int life, int mag) {
		this.life = life;
		this.e = e;
		this.name = "Resistant";
		this.sprite = Sprite.Shield;
		magnitude = mag;
	}
	
	public void effectEquip() {
		System.out.println("Resistance attempted: ");
		if (!fortified) {
			Game.getGame().getPlayer().stat_item_DEF += magnitude;
			fortified = true;
			System.out.println(e + " is now resistant!");
		}
		
		
	}
	
	public void effectDequip() {
		System.out.println("Resistance removal attempted: ");
		if (fortified) {
			Game.getGame().getPlayer().stat_item_DEF -= magnitude;
			fortified = false;
			System.out.println(e + " is no longer reisitant!");
		}
		
	}
	
	public void update() {
		super.update();
		time++;
		if (time % 30 == 0) {
			
		if (b > 0) {
			b -= 20;
		}
			time = 0;
		}
		
	}
	
	public void render(Screen screen) {
		if (e instanceof PlayerMP) {
			((PlayerMP)e).sprinting = false;
			((PlayerMP)e).input.Sprint = false;
			screen.renderLight((int) e.getX()- 200, (int) e.getY() - 200, 200, 20 + b, 20 + b, 20 + b);
		}
	}
	
}
