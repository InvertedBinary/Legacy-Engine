package com.IB.SL.entity.abilities;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class RadialBlast extends Ability{
	
	int ExpV = (random.nextInt(1) + 3);
	final double x_mult = 2;
	final double y_mult = 1.5;
	double tick = 0;
	double vx, vy;
	double time = 0;
	
	public RadialBlast(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(20) + 42;
		this.name = "Radial_Blast";
		damage = 3 + (mob.stat_MAT * 0.2);
		
		basicInitialization();
	}
	
	public RadialBlast(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(20) + 42;
		this.name = "Radial_Blast";
		damage = 3;

		basicInitialization();
	}
	
	
	public void basicInitialization() {
		speed = 0.5;
		sprite = Sprite.RadialBlast;
		unlock = 7;
		FIRE_RATE = 80;
		manaCost = 10;
		//activator = KeyEvent.VK_3;
		this.displaySprite = Sprite.RadialBlast_Display;
		nx = speed * Math.cos(angle) ;
		ny = speed * Math.sin(angle);
		this.id = 10;
	}
	
	/*p = new RadialBlast(origin.getX(), origin.getY(), angle() + 5, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 10, origin);
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 15, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 20, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 25, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 30, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 35, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 40, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 45, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 50, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 55, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 60, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 65, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 70, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 75, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 80, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 85, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 90, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 95, origin);	
		super.use(p, ability, origin);

		p = new RadialBlast(origin.getX(), origin.getY(), angle() + 100, origin);
		super.use(p, ability, origin);
		*/
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		for (int i = -10; i < 10; i++) {
            //int d = (int) Math.sqrt((i - 360) * (i - 360) + (i - 360) * (i - 360));
			double d = i * (18 * 360) + angle();
			//Game.getGame().getLevel().add(new WallParticleSpawner((int) Math.cos(d), (int) Math.cos(d), 10, 25, Game.getGame().getLevel()));
            p = new RadialBlast(origin.getX(), origin.getY(), d, origin);
            super.use(p, ability, origin);
            
		}
		return true;
	}
	
	public void update() {
		List<Entity> entities = level.entities;
		Collision(this, entities);
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
			remove();
	}	
		move();
	}
		public void move2(double xa, double ya) {
			if (xa != 0 && ya != 0) {
				move2(xa, 0);
				move2(0, ya);
				return;}
			}
		
	protected void move() {
		tick += 0.05;
		if (tick > 360) {
			tick = 0;
		}
		
		//vx = Math.sin(tick) * (x_mult);
		//vy = Math.cos(tick) * (y_mult);
		
		
		
		
		x += /*1/*/nx/*+ vx*/;
		y += ny /*+ vy*/;
		
/*if (Game.getGame().gameState == Game.getGame().gameState.ingame_A) {
	      List<Entity> players = level.getEntities(this, 200);
	      if (players.size() > 0) {
	         nx = 0;
	         ny = 0;
	         double px = level.getPlayerAt(0).getX();
	         double py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) nx++;
	               if (x > vec.getX() << 4) ny--;
	               if (y < vec.getY() << 4) nx++;
	               if (y > vec.getY() << 4) ny--;*/
	            /*   Game.getGame().getGraphics().drawLine((int)vec.getX() / 16, (int)vec.getY() / 16, (int)xa / 16, (int)ya /16);
	              Game.getGame().getGraphics().drawRect(vec.getX() / 16, vec.getY() / 16, (int)xa / 16, (int)ya / 16);
	              Game.getGame().getGraphics().drawRect(16, 16, 16 ,16);*/
	            
	       /*  if (time % (random.nextInt(50) + 30) == 0) {
	            nx = random.nextInt(3) - 1;
	            ny = random.nextInt(3) - 1;
	            if (random.nextInt(2) == 0) {
	               nx = 0;
	               ny = 0;
	         }
	      }
	      if (nx != 0 || ny != 0) {
	       //  move2(nx * speed, ny * speed);
	      }
		*/
		
		if (distance() > range) {
			remove();
		level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 3, 17, level));
		//Sound.Play(Sound.Explosion4, false);
		}

		
		if (Game.devModeInfoOn) {
		System.out.println("Distance (In Tiles): " + distance() / 16);
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