package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class NullBolt extends Projectile{
	
	public static int FIRE_RATE = 50;
	public static int time = 0;
	public NullBolt(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(120) + 160;
		speed = 3;
		damage = 5;
		sprite = Sprite.rotate(Sprite.NullBolt, angle);
		//this.collisionSound = Sound.Rock;
		this.breakParticle = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 7002;
	}
	
	public void update() {
		List<PlayerMP> players = level.players;
		List<Projectile> projectiles = level.Projectiles;
		PlayerCollision(players, this); 
		
		/*if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			//level.add(new RockShatterSpawner((int) (x + nx), (int) (y + ny), 20, 4, level));
			//Sound.Play(Sound.Rock, false);
			 remove();
	}*/
	
		move();
	}
/*	public void updateMobCollision(List<Player> players) {	
	for (int i = 0; i < players.size(); i++) {
		if (x < players.get(i).getX() + 5
				&& x > players.get(i).getX() - 5
				&& y <  players.get(i).getY() + 5
				&& y >  players.get(i).getY() - 5
				) {
			remove();
			Player.incombat = true;
			players.get(i).mobhealth -= 3;
			level.add(new BleedSpawner((int) (x + nx), (int) (y + ny), 10, 8, level));
			level.add(new RockShatterSpawner((int) (x + nx), (int) (y + ny), 20, 4, level));
			Sound.Play(Sound.Rock, false);
			if (players.get(i).mobhealth <= 0){
				Game.gameState = 6;
				System.out.println("Player " + players.get(i) + " Died");
				Game.Dead = true;
				time++;
					//Player.respawn = true;
				}
			}
		}
	}*/

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) {
			//level.add(new RockSpawner((int) (x + nx), (int) (y + ny), 40, 1, level));
			remove();
		}
		
	}




	
	
	

	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x - 8,(int)y - 14, this);
	}
}