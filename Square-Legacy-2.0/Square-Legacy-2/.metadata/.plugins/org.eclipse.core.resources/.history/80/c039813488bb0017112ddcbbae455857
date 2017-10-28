package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.effects.Poison;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.particle.slimeParticle;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class PoisonGoo extends Projectile{
	
	public static int FIRE_RATE = 70;
	public static int time = 0;
	
	public PoisonGoo(double x, double y, double dir, double speed) {
		super(x, y, dir);
		range = random.nextInt(30) + 64;
		this.speed = speed;
		damage = 1;
		sprite = Sprite.PoisonGoo;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 1;

	}	
	int time2= 0;
	public void update() {
		time++;
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this); 
		time2++;
		if (time2 % 10 == 0) { 
			sprite = Sprite.rotate(sprite, Math.PI / 20.0 + random.nextInt());
			level.add(new slimeParticle((int) (x + nx), (int) (y + ny - 4), 20, 4));
		}
		
		if (time2 % 30 == 0) {
			sprite = Sprite.resize(sprite, 0.8);
			time2 = 0;
		}
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {		
			//level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
		//	Sound.Play(Sound.walkingSand, false);
		//	level.add(new ParticleSpawner((int) level.getPlayerAt(0).getX(), (int)level.getPlayerAt(0).getY(), 2, 2, level));
			 remove();
			 
			 

	}

		move();
		}

	protected void move() {
		
		x += nx;
		y += ny;
		if (distance() > range) remove();
		
		
		if (Game.devModeInfoOn) {
		System.out.println("Distance (In Tiles): " + distance() / 16);
	}
	}




	
	public void addEffect(PlayerMP player) {
		player.effects.addEffect(new Poison(player, 120, 26));
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



/*	for (int i = 0; i < Level.players.size(); i++) {
	if (x < Level.players.get(i).getX() + 5
        && x > Level.players.get(i).getX() - 5
        && y <  Level.players.get(i).getY() + 5
        && y >  Level.players.get(i).getY() - 5
        ) {
        remove();
        Level.players.get(i).mobhealth -= 1;
        Game.Health =  Level.players.get(i).mobhealth;
        level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 6, 2, level));
        level.add(new BleedSpawner((int) (x + nx), (int) (y + ny), 10, 8, level));
        
		if (Level.players.get(i).mobhealth <= 0){
			Level.players.get(i).remove();
			level.add(new ParticleSpawner((int) (x + nx), (int) (y + ny), 300000, 200, level));
				System.out.println("YOU DIED");
    			Game.Dead = false;
    			time++;
    			if (Level.players.get(i).isRemoved()) {
    				Player.respawn = true;
    		}
    	}
	}
}*/