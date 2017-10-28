package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.equipables.chestpiece.CarvedArmor;
import com.IB.SL.entity.inventory.item.equipables.staves.wand_Pulsefire;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.projectile.Optical;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.util.Debug;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class CopperGuardian extends Mob {

	transient private AnimatedSprite Main = new AnimatedSprite(SpriteSheet.CopperGuardian_Main, 128, 96, 4);
/*	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Occulus_up, 32, 32, 0);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Occulus_left, 32, 32, 0);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Occulus_right, 32, 32, 0);*/

	transient private AnimatedSprite animSprite = Main;
	transient double xa = 0;
	transient double ya = 0;
	transient int xx = 0;
	transient int yy = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public int stoneRate = 0;
	transient public int copperRate = 600;
	//private int healTime = 0;
	transient int fireRateTime = 0;
	transient int index = 0;
	transient private font font;
	transient private int spin = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient private GUI gui;

	public CopperGuardian(int x, int y) {
		this.mobhealth = 1000;
		this.maxhealth = 1000;
		this.id = 1;
		this.name = "Copper Guardian";
		this.hostility = hostility.BOSS;
		this.x = x << 4;
		this.y = y << 4;
		sprite = Main.getSprite();
		font = new font();
		this.effects = new ActiveEffects(7, this);
		this.gui = new GUI();
		//animSprite.setFrame(3);
	}
	
	private void move() {
	      List<Player> players = level.getPlayers(this, 200);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         double px = level.getPlayerAt(0).getX();
	         double py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> 4, (int)getY() >> 4);
	         Vector2i destination = new Vector2i(px / 16, py / 16);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
	            /*   Game.getGame().getGraphics().drawLine((int)vec.getX() / 16, (int)vec.getY() / 16, (int)xa / 16, (int)ya /16);
	              Game.getGame().getGraphics().drawRect(vec.getX() / 16, vec.getY() / 16, (int)xa / 16, (int)ya / 16);
	              Game.getGame().getGraphics().drawRect(16, 16, 16 ,16);*/

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
	        // move(xa * speed, ya * speed);
	         walking = true;
	      } else {
	         walking = false;
	      }
	   }
	
	
	public void update() {
		if (time % 2 == 0) {
			this.hurt = false;
		}
		List<Player> players = level.getPlayers(this, 300);
		if (players.size() > 0) {
		fireRateTime++;
		//updateShooting();
        if (fireRate > 0) {
            fireRate--;
        }
        if (stoneRate > 0) {
        	stoneRate--;
        }
        if (copperRate > 0) {
        	copperRate--;
        }
		time++;
		move();
		updateCopper();
		updateStone();
		if (time % 15 == 0) {
		animSprite.update();
		}
		if (animSprite.getFrame() == 0) {
			updateShockwave();
			
			spin++;
		} else {
			spin = 0;
		}
		} else {
			animSprite.setFrame(3);
		}
		/*if (walking)
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
		}*/
		
	}
	
	public void render(Screen screen) {
		xOffset = + xx - 21;
		yOffset = + yy - 127;
		sprite = animSprite.getSprite();
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), this);
		font.render((int) (x - 65), (int) (y - 110), -5, 0, this.name, screen, true, false);
		//circle();
		List<Player> players2 = level.getPlayers(this, 300);
		if (Game.getGame().gameState == Game.getGame().gameState.INGAME_A) {
		//System.out.println("WIDTH: " + sprite.getWidth());
			Debug.drawRect(screen, (int)x + xOffset, (int)y + yOffset, (int)(sprite.getWidth()), (int)(sprite.getHeight()), true);
			//Debug.drawRect(screen, (int)x + xOffset, (int)y + yOffset, 128, 128, 0xff00ff, true);
		}
		if (players2.size() > 0) {
			
		//	screen.renderSprite((int)x - screen.xOffset, (int)y - screen.yOffset, sprite.BottomChair, true);
		}
	}
	
	public void renderGUI(Screen screen) {
		if (level.getPlayers(this, 250).size() > 0)
		screen.renderSprite((Game.width  / 2) - 54, 2, Sprite.resize(gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), 1.5), false); // 130
	}
	
	private void updateShooting() {
		List<Player> players = level.getPlayers(this, 150);
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			Optical(x, y, dir);
			level.getClientPlayer().incombat = true;
			//Sound.Play(Sound.Spell2, -8, false);
			fireRate = Optical.FIRE_RATE;
			if (fireRateTime >= 800) {
				fireRate = 1;
				Optical(x, y, dir + 10);
				Optical(x, y, dir + 20);
			//	variance = 10;
			//	Optical(x + variance, y + 4 + variance, dir);
			}
			if (fireRateTime >= 810) {
				fireRateTime = 0;
				Optical(x, y, dir + 10);
				Optical(x, y, dir + 20);
				//variance = 20;
				//Optical(x + variance, y + 4 + variance, dir);
			}
		}
	}
	
	private void updateCopper() {
		List<Player> players = level.getPlayers(this, 150);
		if ((players.size() > 0) && copperRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			copperShockwave(x, y, dir);
			level.getClientPlayer().incombat = true;
			copperRate = 800;
			}
	}
	
	private void updateStone() {
		List<Player> players = level.getPlayers(this, 150);
		if ((players.size() > 0) && stoneRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			stoneSpike(x, y, dir);
			level.getClientPlayer().incombat = true;
			stoneRate = 65;

			}
	}
	
	private void circle() {
		Game.getGame().getScreen().renderLight((int)(x + xx - 21), (int) (y + yy - 147), 50 , 20, 20, 40, true);
		List<PlayerMP> players = level.getPlayersFixed((int)(x + xx - 8), (int)(y + yy - 140), 50);
			
		if (players.size() > 0) {
			System.out.println("Inside the circle");
		}
	}
	
	
	
	private void updateShockwave() {
		List<Player> players = level.getPlayers(this, 300);
		
			/*if (players2.size() > 0) {
				Sound.switchMusic(Sound.menuMusOgg, 0.8f);
			}
			if (players2.size() <= 0) {
				Sound.switchMusic(Sound.HopeOgg, 0.5f);
			}*/
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
		//	Optical(x, y, dir);
			level.getClientPlayer().incombat = true;
			//Sound.Play(Sound.Spell2, -8, false);
			fireRate = Optical.FIRE_RATE;
			if (fireRateTime >= 800) {
				fireRate = 7;
				RedShockwave(x, y, (dir * spin) + 5);	
				RedShockwave(x, y, (dir * spin) + 10);
				RedShockwave(x, y, (dir * spin) + 15);	
				RedShockwave(x, y, (dir * spin) + 20);	
				RedShockwave(x, y, (dir * spin) + 25);	
				RedShockwave(x, y, (dir * spin) + 30);	
				RedShockwave(x, y, (dir * spin) + 35);	
				RedShockwave(x, y, (dir * spin) + 40);	
				RedShockwave(x, y, (dir * spin) + 45);	
				RedShockwave(x, y, (dir * spin) + 50);	
				RedShockwave(x, y, (dir * spin) + 55);	
				RedShockwave(x, y, (dir * spin) + 60);	
				RedShockwave(x, y, (dir * spin) + 65);	
				RedShockwave(x, y, (dir * spin) + 70);	
				RedShockwave(x, y, (dir * spin) + 75);	
				RedShockwave(x, y, (dir * spin) + 80);	
				RedShockwave(x, y, (dir * spin) + 85);	
				RedShockwave(x, y, (dir * spin) + 90);	
				RedShockwave(x, y, (dir * spin) + 95);	
				RedShockwave(x, y, (dir * spin) + 100);
				Sound.toggleSound(Sound.CopperGuardianSword, false);
				
				// variance = 10;
				// Optical(x + variance, y + 4 + variance, dir);

			}
		}
	}

	public void death() {
		if (!removed) {
		level.add(new wand_Pulsefire((int)x, (int)y, 2000, 1, EquipableItem.slot_WEAPON));
		level.add(new CarvedArmor((int)x, (int)y, 2000, 1, EquipableItem.slot_CHEST));

		}
	}

}

