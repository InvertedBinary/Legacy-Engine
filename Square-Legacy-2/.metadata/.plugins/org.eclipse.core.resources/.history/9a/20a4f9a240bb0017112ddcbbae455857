package com.IB.SL.entity.inventory.item.consumables;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.util.Sound;

public class StaminaPot extends Item{
	public StaminaPot(int x, int y, int life, int amount) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
		basicInitialization();
	}
	
	public StaminaPot() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.sprite = Sprite.StaminaPotion;
		this.name = "Stamina Potion";
		this.dropchance = 2;
		this.rarity = 1;
		this.price = "5";
		this.desc = "Adds\nUp to\n5 SP";
		this.stackSize = 5;
		this.item_TYPE = type_STAFF;
		level = Game.getGame().level2;

	}
	
	protected void move(double x, double y) {
			if (collision(x, y)) {
				this.xa *= -0.5;
				this.ya *= -0.5;
				this.za *= -0.5;
			}
			this.xx += xa;
			this.yy += ya;
			this.zz += za;

		
			}
	
	
	public boolean clickEvent() {
		boolean used = true;
		List<PlayerMP> players = level.getPlayers();

		for (int i = 0; i < players.size(); i++) {
			double maxstamina1 = players.get(i).maxstamina - 1;
			double maxstamina2 = players.get(i).maxstamina - 2;
			double maxstamina3 = players.get(i).maxstamina - 3;
			double maxstamina4 = players.get(i).maxstamina - 4;
			double maxstamina5 = players.get(i).maxstamina - 5;
			if (players.get(i).stamina >= players.get(i).maxstamina) {
				used = false;
			} else {
				used = true;
			}
		if (players.get(i).stamina < players.get(i).maxstamina) {
			if (players.get(i).stamina <= maxstamina5) {
				System.out.println("maxstamina5");
				players.get(i).stamina += 5;
			}
			if (players.get(i).stamina == maxstamina4) {
				System.out.println("maxstamina4");

				players.get(i).stamina += 4;
			}
			if (players.get(i).stamina == maxstamina3) {
				System.out.println("maxstamina3");

				players.get(i).stamina += 3;
			}
			if (players.get(i).stamina == maxstamina2) {
				System.out.println("maxstamina2");

				players.get(i).stamina += 2;
			}
			if (players.get(i).stamina == maxstamina1) {
				System.out.println("maxstamina1");

				players.get(i).stamina += 1;
			}

			Sound.Play(Sound.Potion, false);

			}
		}
		return used;
	}
			public void update() {
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
				
				List<Player> players = level.getPlayers(this, 28);
				//List<Player> players1 = level.players;	
				
			/*for (int i = 0; i < players1.size(); i++) {
				if (players1.get(i).stamina >= 20) {
					maxstamina = true;
				} else {
					maxstamina = false;
				}
			}
			xa = 0;
			ya = 0;
				if (!maxstamina) {
				if (players.size() > 0) {
					Player player = players.get(0);
					if ((int)x < (int)player.getX()) xa+= 1.5;
					if ((int)x > (int)player.getX()) xa-= 1.5;
					if ((int)y < (int)player.getY()) ya+= 1.5;
					if ((int)y > (int)player.getY()) ya-= 1.5;
					}
				}*/
			if (pickup(x,y, level, players)) {
				for (int i = 0; i < players.size(); i++) {
					
				//	remove();
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

