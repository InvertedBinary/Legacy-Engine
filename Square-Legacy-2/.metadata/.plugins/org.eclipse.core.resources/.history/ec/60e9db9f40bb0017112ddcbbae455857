package com.IB.SL.entity.inventory.item.equipables.rings;
	import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;

	public class CubeOfBefuddlement extends EquipableItem{
		int use = 0;
		public CubeOfBefuddlement(int x, int y, int life, int amount, int slot) {
			RNGGen();
			this.x = x;
			this.y = y;
			this.xx = x;
			this.yy = y;
			this.slot = slot;
			this.life = life + (random.nextInt(15) - 4);
			this.xa = random.nextGaussian();
			this.ya = random.nextGaussian();
			this.zz = random.nextFloat() + 1.0;
			
			basicInitialization(slot);
		}
		
		public CubeOfBefuddlement(int slot) {
			RNGGen();
			basicInitialization(slot);
		}
		
		public void basicInitialization(int slot) {
			this.sprite = Sprite.CubeOfBefuddlement;
			this.name = "Cube of Befuddlement";
			this.dropchance = -1;
			this.rarity = 0;
			this.price = "525";
			this.slot = slot;
			this.slot_Default = slot_UTILITY1;
			this.item_TYPE = type_ARMOR;
			this.desc = "+12 To\nMAT\n+9 To\nMDF";
			level = Game.getGame().level2;
		}
		
		protected void move(double x, double y) {
			/*List<Player> players = level.getPlayers(this, 28);
			List<Player> players1 = level.players;	*/
				if (collision(x, y)) {
					this.xa *= -0.5;
					this.ya *= -0.5;
					this.za *= -0.5;
				}
				this.xx += xa;
				this.yy += ya;
				this.zz += za;

		}
	
		public void equipEvent() {
			super.equipEvent();
		}
		
		public void dequipEvent() {
			super.dequipEvent();

		}
		
		public void RNGGen() {
			this.MAT = 12;
			this.MDF = 9;
		}
		
		public boolean clickEvent() {
			boolean used = false;
			List<PlayerMP> players = level.getPlayers();
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).equipment.Equip(this)) {
					used = true;
				}
			}
			return used;
		}
		
				public void update() {
					List<Player> players = level.getPlayers(this, 28);
					time++;
					if (time >= 7400) time = 0;
					if (time > life) remove();
					za -= 0.1;
					
					if (zz < 0) {
						zz = 0;
						za *= -0.55;
						xa *= 0.4;
						ya *= 0.4;
					}
					
					move(xx + xa, (yy + ya) + (zz + za));

					if (pickup(x,y, level, players)) {
					for (int i = 0; i < players.size(); i++) {
						players.get(i).addItem(this);
					}
				}

					}

			
			
				public boolean pickup(double xi, double yi, Level level, List<Player> players) {
					boolean remove = false;
					int xp = 0, yp = 0;
					for (int i = 0; i < players.size(); i++) {
						xp = (int) players.get(i).getX();
						yp = (int) players.get(i).getY();
							if (xp < (int) xx + sprite.getWidth() + 3
				            && xp > (int) xx - 3
				            && yp < (int) (yy - (int)zz) + sprite.getHeight() + 3
				            && yp > (((int)yy - (int)zz)) - 3
				           
				            ) {
						remove = true;
							}
					}
					return remove;
				}
			
			public void render(Screen screen) {
				screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
				
				if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)xx + 3, ((int)yy - (int)zz) + 5, sprite.getWidth() / 2 + 1, sprite.getHeight() / 2 + 1, 0xFF00FF,true);
			
			}

	}
