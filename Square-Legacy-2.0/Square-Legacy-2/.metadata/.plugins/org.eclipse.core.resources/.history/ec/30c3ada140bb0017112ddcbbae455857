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

public class ManaPot extends Item{
	
	public ManaPot(int x, int y, int life, int amount) {
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
	
	public ManaPot() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.sprite = Sprite.ManaPotion;
		this.name = "Mana Potion";
		this.dropchance = 3;
		this.rarity = 2;
		this.price = "15";
		this.desc = "Adds\nUp to\n5 MP";
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
				double maxmana1 = players.get(i).maxmana - 1;
				double maxmana2 = players.get(i).maxmana - 2;
				double maxmana3 = players.get(i).maxmana - 3;
				double maxmana4 = players.get(i).maxmana - 4;
				double maxmana5 = players.get(i).maxmana - 5;
				if (players.get(i).mana >= players.get(i).maxmana) {
					used = false;
				} else {
					used = true;
				}
			if (players.get(i).mana < players.get(i).maxmana) {
				if (players.get(i).mana <= maxmana5) {
					System.out.println("maxmana5");
					players.get(i).mana += 5;
				}
				if (players.get(i).mana == maxmana4) {
					System.out.println("maxmana4");

					players.get(i).mana += 4;
				}
				if (players.get(i).mana == maxmana3) {
					System.out.println("maxmana3");

					players.get(i).mana += 3;
				}
				if (players.get(i).mana == maxmana2) {
					System.out.println("maxmana2");

					players.get(i).mana += 2;
				}
				if (players.get(i).mana == maxmana1) {
					System.out.println("maxmana1");

					players.get(i).mana += 1;
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
				if (players1.get(i).mana >= 20) {
					maxmana = true;
				} else {
					maxmana = false;
				}
			}
			xa = 0;
			ya = 0;
				if (!maxmana) {
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

