package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.effects.onFire;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class FlameMace extends Projectile {
	public static int FIRE_RATE = 35;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;
	Item item;

	protected static Random Random = new Random();
	
	public FlameMace(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInit();
	}
	
	public FlameMace(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1 + mob.stat_ATC / 2;

		basicInit();
	}
	
	void basicInit() {
		range = random.nextInt(80) + 260;
		drop = (dropChance.nextInt((6 - 1) + 1) + 1);
		speed = 3;
		sprite = new Sprite(1, 0xffFFFFFF);	
		manaCost = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		item = new Item();
		this.id = 13;
		Sound.Play(Sound.VoidCrook,  false);
	}
	

	
	
	public void update() {
		List<Entity> es = level.getEntities(level.getClientPlayer(), 20);
//		List<Entity> es = level.getEntities((int)level.getClientPlayer().getX(), (int)level.getClientPlayer().getY() - 4, 16);

		if (es.size() > 0) {
				for (int i = 0; i < es.size(); i++) {
					if (es.get(i) instanceof Mob) {
					level.damage((int) x, (int) y, (Mob) es.get(i), Exp, damage, name, ExpV);
					doEffect((Mob)es.get(i));
				}
			}
		}
		remove();					
	}
	
	protected void move() {
		/*x += nx;
		y += ny;
		if (distance() > range) remove();*/
	}
	
	protected void doEffect(Entity e) {
		e.effects.addEffect(new onFire(e, 180));
	}

	public double distance() {
		double dist = 0;
		//dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}
	

	
	public void render(Screen screen) {
	//	screen.renderProjectile((int)x - 8,(int)y - 14, this);
		
		if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)x - 3, (int)y - 9, 5, 5, 0x0093FF, true);

	}
}