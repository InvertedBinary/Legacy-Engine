package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class MustyTome extends Projectile {
	public static int FIRE_RATE = 35;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;
	Item item;

	protected static Random Random = new Random();
	
	public MustyTome(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInit();
	}
	
	public MustyTome(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1 + mob.stat_ATC / 5;

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
	

	
	
double parx = 0;
double pary = 0;
	public void update() {

		for (int i = 0; i < Math.PI * 2; i++) {
			for (int j = 0; j < Math.PI * 2; j++) {
			 parx = 256 * Math.cos(i) + x;
			 pary = 256 * Math.sin(j) + y;
			 level.add(new WallParticleSpawner((int) (parx / 16) - 8, (int) (pary / 16) - 8, 10, 1, level));
			 System.out.println("adding new particles at: " + parx + " , " + pary);
			}
		}
		
		//List<Entity> es = level.getEntities(Mouse.getX() / Game.scale, Mouse.getY() / Game.scale, 50);
		List<Entity> es = (level.AOEFull(null, (int)(x), (int)(y), 16, false, 0xffffffff));//		List<Entity> es = level.getEntities((int)level.getClientPlayer().getX(), (int)level.getClientPlayer().getY() - 4, 16);
		if (es.size() > 0) {
				for (int i = 0; i < es.size(); i++) {
					if (es.get(i) instanceof Mob && !(es.get(i) instanceof Player)) {
					level.damage((int) x, (int) y, (Mob) es.get(i), Exp, damage, name, ExpV);					
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

	public double distance() {
		double dist = 0;
		//dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}
	

	
	public void render(Screen screen) {
		//level.AOEFull(screen,  (int)(x), (int)(y), 16, true, 0xffffffff);//
		if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)x - 3, (int)y - 9, 5, 5, 0x0093FF, true);

	}
}