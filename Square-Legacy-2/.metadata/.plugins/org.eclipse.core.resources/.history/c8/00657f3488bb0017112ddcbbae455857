package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class FrostShard extends Projectile{
	
	public static int FIRE_RATE = 25;
	public static int time = 0;
	int slowed = 0;
	boolean boolSlowed = false;
	public FrostShard(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(100) + 300;
		speed = 3;
		damage = 25;
		sprite = Sprite.rotate(Sprite.FrostShard, angle);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 2;

	}
	
	public void update() {
		time++;
		
	
	/*	if (boolSlowed) {
			slowed++;
			System.out.println(slowed);
		}
		if (slowed > 10) {
			boolSlowed = false;
			slowed = 0;
			for (int i = 0; i < players.size(); i++) {
			players.get(i).speed = 1;
		}
		}*/
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this);   	  	
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {		
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
		//	level.add(new ParticleSpawner((int) level.getPlayerAt(0).getX(), (int)level.getPlayerAt(0).getY(), 2, 2, level));
			 remove();
	}

		move();
		}	

	protected void move() {
		
		x += nx;
		y += ny;
		if (distance() > range) {
			//level.add(new slimeParticleSpawner((int) (x + nx), (int) (y + ny), 40, 1, level));
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