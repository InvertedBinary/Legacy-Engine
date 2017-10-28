package com.IB.SL.entity.mob.peaceful;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class Alice extends Mob{
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Alice_down, 16, 16, 3);
	transient  private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Alice_up, 16, 16, 3);
	transient  private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Alice_left, 16, 16, 2);
	transient  private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Alice_right, 16, 16, 2);
	transient  private AnimatedSprite animSprite = down;
	
	transient  private int time = 0;
	transient double xa = 0;
	transient  double ya = 0;
	transient  private List<Node> path = null;
	transient  List<Entity> entities = null;
	public Alice(int x, int y) {
		this.maxhealth = 5;
		this.mobhealth = this.maxhealth;
		this.Exp = 4;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 0;
		this.name = "Alice";
		this.speed = 0.5;
		this.hostility = HOSTILITY.NEU;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
		new font8x8();
	}
	
	
	public void stab() {
		try {
			List <Entity> ent = level.getEntities(this, 20, entities);
			if (ent.get(0).hostility == hostility.AGR) {
			if (time % 30 == 0) {
			for (int i = 0; i < ent.size(); i++) {
				Game.getGame().getLevel().damage((int)x, (int)y, (Mob)ent.get(0), 0, 1, name, 0);
					}
				}
			}
		} catch (Exception e) {
			
		}
		}

	
	 private void move() {
	      if (entities.size() > 0) {
	         xa = 0;
	         ya = 0;
	         double px = entities.get(0).getX();
	         double py = (int) entities.get(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	      /*   if (time % 1 == 0) */path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
	            }
	         }
	      
	      }  else {
			if ((time % 2) == 0) { 
				if (time % (random.nextInt(50) + 30) == 0) {
					xa = (random.nextInt(3) - 1);
					ya = (random.nextInt(3) - 1);
					if (random.nextInt(2) == 0) {
						xa = 0;
						ya = 0;			
					}
				}
			}
	      }
	      if (xa != 0 || ya != 0) {
	         move(xa * speed, ya * speed);
	         walking = true;
	      } else {
	         walking = false;
	      	}
	      
}
	
	public void update() {
		if (time % 8 == 0) {
			this.hurt = false;
		}
		entities = level.getEntities(this, 150, HOSTILITY.AGR);
		stab();
		time++;
		move();
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
		if (ya < 0) {

			animSprite = up;
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = DIRECTION.RIGHT;
		}
		
	}
	
	public void render(Screen screen) {
		this.xOffset = -8;
		this.yOffset = -16;
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 24, Game.getGame().gui.renderMobHealthExperiment(this, 20), true);

		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		

	}

}
