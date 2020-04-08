package com.IB.LE2.world.entity.projectile;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.util.shape.LineSegment;
import com.IB.LE2.util.shape.Vertex;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.worlds.TiledLevel;

public abstract class Projectile extends Entity {
	private static final long serialVersionUID = 1L;
	
	protected double xOrigin, yOrigin;
	protected double nx, ny;
	public double angle;
	
	public Entity origin;
	
	public Projectile (double x, double y) {
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = CalcAngle();
		x(x);
		y(y);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void ApplyStatusEffect(Entity e) {}
	
	public Vector2i getCollisionPoint() {
		if (sprite != null) {
			return new Vector2i((int) x() + (sprite.SIZE / 2), (int) y() + (sprite.SIZE / 2));
		} else {
			return null;
		}
	}
	
	public boolean CollidesLevel(Projectile p) {
		if (((TiledLevel) Boot.getLevel()).solid_geometry == null) {
			return false;
		}
		
		int c = this.getSpriteSize() / 2;
		LineSegment bound = new LineSegment(
				new Vertex((float)x() + c - 2, (float)y() + c - 2), 
				new Vertex((float)x() + c + 2, (float)y() + c + 2)
		);

		for (int i = 0; i < ((TiledLevel) Boot.getLevel()).solid_geometry.size(); i++) {
			if(bound.CollidesWithLine(((TiledLevel) Boot.getLevel()).solid_geometry.get(i), false)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Entity CollidesEntity(Projectile p, List<Entity> entities) {
		int pw = p.getSprite().getWidth();
		int ph = p.getSprite().getHeight();
		int px = (int)p.x() + pw / 2;
		int py = (int)p.y() + ph / 2;
		
		
		for (Entity e : entities) {
			if (e != null && e.getSprite() != null)
			if (!(e instanceof Projectile) && !e.equals(p.origin)) {
				int entityX = (int)e.x();
				int entityY = (int)e.y();
				
				Sprite s = e.getSprite();
				
				if (px > (entityX + e.DrawXOffset) && px < (entityX + e.DrawXOffset + s.getWidth())) {
					if (py > (entityY + e.DrawYOffset) && py < (entityY + e.DrawYOffset + s.getHeight())) {
						int pixel_x = (int)(px - entityX - e.DrawXOffset);
						int pixel_y = (int)(py - entityY - e.DrawYOffset);
						
						if (s.pixels[(pixel_x) + (pixel_y) * s.getWidth()] != Screen.ALPHA_COL)
							return e;
						
					}
				}
			}
		}

		return null;
	}
	
	public double CalcAngle() {
		//TODO: Convert projectile x,y to PVector!
		double dx = Mouse.getX() - ((x() - Boot.get().xScroll) * 2);
		double dy = Mouse.getY() - ((y() - Boot.get().yScroll) * 2);
		///double dx = Mouse.getX() - Game.getWindowWidth() / 2;
		///double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double dir = Math.atan2(dy + 0, dx + 0);
		return dir;
	}
	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x()) * (xOrigin - x()) + (yOrigin -y()) * (yOrigin - y())));
		return dist;
	}
	
	protected void move() {}
	
	protected void moveSimple() {
		this.x(x() + nx);
		this.y(y() + ny);
		//drag y down with mass
		if (distance() > nvar("range")) {
			remove();
		}
	}

	protected void moveArc() {
		/*this.range = 1000;
		//if (y + ny < yOrigin) {
		za += (VARS.Ag);
		if (zz < 0f) {
			zz = 0f;
			za *= -0f;
		}
		
		if (master_sprite == null) master_sprite = sprite;
		double deltaX = this.x() - (this.x() + this.nx);
		double deltaY = (this.y() - (this.y() + this.ny)) - (zz + za) / 900;
		this.angle = Math.atan2(deltaY, deltaX);
		this.x(x() + nx);
		this.y(y() + ny + (zz + za));
		this.sprite = Sprite.rotate(this.master_sprite, this.angle);	
		if (distance() > range) remove();
		//if (this.yOrigin < this.y) remove();
		if (time > 300) {
			remove();
		}
		//} else {
		//	moveSimple();
		//}*/
	}
}