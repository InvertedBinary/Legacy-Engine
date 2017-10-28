package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.equipables.head.VoidweaversHood;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_StygianScepter;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_VoidCrook;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.NullBolt;
import com.IB.SL.entity.projectile.NullWave;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class VoidBoss extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.VoidBoss_down, 64, 64, 2);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.VoidBoss_up, 64, 64, 2);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.VoidBoss_left, 64, 64, 2);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.VoidBoss_right, 64, 64, 2);

	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient int xx = 0;
	transient int yy = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public int fireRateNullBolt = 0;
	transient int fireRateTime = 0;
	transient int index = 0;
	transient private GUI gui;
	transient int pullTime = 0;

	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	transient private double speed = 0.5;

	public VoidBoss(int x, int y) {
		this.mobhealth = 4500;
		this.maxhealth = 1000;
		this.id = 1;
		this.name = "The Null";
		this.hostility = hostility.BOSS;
		this.x = x << 4;
		this.y = y << 4;
		sprite = animSprite.getSprite();
		this.gui = new GUI();
		this.effects = new ActiveEffects(7, this);
		//animSprite.setFrame(3);
	}
	
	
	private void move() {
	      List<Player> players = level.getPlayers(this, 200);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         double px = level.getPlayerAt(0).getX();
	         double py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
	            }
	         }
	      } else { 
	         if (time % (random.nextInt(50) + 30) == 0) {
	            xa = random.nextInt(3) - 1;
	            ya = random.nextInt(3) - 1;
	            if (random.nextInt(2) == 0) {
	               xa = 0;
	               ya = 0;
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
		if (time % 2 == 0) {
			this.hurt = false;
		}
		updatePull();
		NullBolt();
	//	updateShockwave();
		if (fireRate > 0) {
          fireRate--;
      }
		
		if (fireRateNullBolt > 0) {
	          fireRateNullBolt--;
	      }
		time++;
		if (pullTime > 250) {
		move();
		}
	
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
		sprite = animSprite.getSprite();
		this.xOffset = -35;
		this.yOffset = -30;
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), this);
	}
	
	public void renderGUI(Screen screen) {
		gui.font.render((int) (x - 65), (int) (y - 60), -5, 0xffFFFFFF, this.name, screen, true, false);
		if (level.getPlayers(this, 300).size() > 0)
		screen.renderSprite((Game.width  / 2) - 54, 2, Sprite.resize(gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), 1.5), false); // 130
	}
	
	
	private void updateNullWave() {
		List<Player> players = level.getPlayers(this, 120);
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			level.add(new NullWave(x, y, dir, this));
			level.add(new NullWave(x, y, dir + 45, this));
			level.add(new NullWave(x, y, dir + 90, this));
			level.add(new NullWave(x, y, dir + 135, this));
			level.add(new NullWave(x, y, dir + 180, this));
			level.add(new NullWave(x, y, dir + 225, this));
			level.add(new NullWave(x, y, dir + 270, this));
			level.add(new NullWave(x, y, dir + 315, this));

			level.getClientPlayer().incombat = true;
			//Sound.Play(Sound.Spell2, -8, false);
			fireRate = NullWave.FIRE_RATE;
			//	variance = 10;
			//	Optical(x + variance, y + 4 + variance, dir);

			/*if (fireRateTime >= 810) {
				fireRateTime = 0;
				Optical(x, y, dir + 10);
				Optical(x, y, dir + 20);
				//variance = 20;
				//Optical(x + variance, y + 4 + variance, dir);

			}*/
		}
	}
	
	private void NullBolt() {
		List<Player> players = level.getPlayers(this, 150);
		if ((players.size() > 0) && fireRateNullBolt <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			level.add(new NullBolt(x, y, dir, this));
			level.getClientPlayer().incombat = true;
			fireRateNullBolt = NullBolt.FIRE_RATE;
	
		}
	}
	
	public void death() {
		if (!removed) {
			
			Game.getGame().getLevel().add(new wand_VoidCrook((int)x, (int)y, 2000, 1, EquipableItem.slot_WEAPON));
			Game.getGame().getLevel().add(new wand_StygianScepter((int)x, (int)y, 2000, 1, EquipableItem.slot_WEAPON));
			Game.getGame().getLevel().add(new VoidweaversHood((int)x, (int)y, 2000, 1, EquipableItem.slot_WEAPON));
			
			Sound.Play(Sound.erie, false);
			
			Game.getGame().getLevel().addDoorTile((int)x / 16, (int)y / 16);
			}
		
	}


private void updatePull() {
	double var = 0.5;
	if (pullTime < 250) {
		var = 0.8;
	} else {
		var = 0.5;
	}
 	if (pullTime > 1000){
   		pullTime = 0;
   	}
	pullTime++;
	if (pullTime < 550) {
		updateNullWave();
   	List<Player> players = level.getPlayers(this, 160);
   	//List<Player> players2 = level.getPlayers(this, 20);
   	if (players.size() > 0) {
   		//var = 0.5;
   		/*if (players2.size() > 0) {
   			var = -20;
   			pullTime = 551;
   		}*/
   		if (pullTime == 50){
   			Sound.Play(Sound.Siphon1, false);
   			pullTime += 1;
   		}
    	  Game.getGame().getPlayer().pull(this, var);
			//level.add(new VoidParticleSpawner((int) (x - 4), (int) (y - 10), 10, 1, level));

   	} 
  
   }
}
}


