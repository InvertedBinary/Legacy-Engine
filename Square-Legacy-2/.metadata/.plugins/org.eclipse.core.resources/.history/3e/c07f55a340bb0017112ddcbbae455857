package com.IB.SL.entity.inventory.item.equipables.secondary;
	import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.item.equipables.Weapon;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.projectile.ThrowingKnife;
import com.IB.SL.entity.projectile.WizardProjectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;

	public class ThrowingDagger extends Weapon {
		
		public ThrowingDagger(int x, int y, int life, int amount, int slot) {
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
		
		public ThrowingDagger(int slot) {
			RNGGen();
			basicInitialization(slot);
		}
		
		public void basicInitialization(int slot) {
			this.sprite = Sprite.ThrowingDagger;
			this.name = "Throwing Dagger";
			this.dropchance = -1;
			this.rarity = 0;
			this.price = "10";
			this.slot = slot;
			this.id = 4;
			this.desc = "+" + (int)AGI + "AGI";
			this.item_TYPE = this.type_STAFF;
			this.FIRE_RATE = 5;
			level = Game.getGame().level2;
			this.recipe.add("Stick");
			this.recipe.add("Stick");
		}

		@Override
		public void secondary(Projectile p, Weapon w, Mob origin) {
			double x = origin.getX(), y = origin.getY();
			p = new ThrowingKnife(x, y, this.angle(), origin);
			super.secondary(p, this, origin);		

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
			this.AGI = 10;

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

			@Override
			public void attack() {
				// TODO Auto-generated method stub
				
			}

	}
