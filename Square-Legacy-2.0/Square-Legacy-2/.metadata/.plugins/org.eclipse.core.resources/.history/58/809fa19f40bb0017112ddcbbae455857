package com.IB.SL.entity.inventory.item.consumables;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.util.Sound;

public class CoinBag extends Item{
	boolean added = false;
	
	public enum Type {
		POCKETCHANGE, SMALL, MEDIUM, LARGE, RANDOM;
	}

	public Type type;
	int randGen = 0;
	int contents;
	protected static java.util.Random Rand = new Random();
	
	public CoinBag(int x, int y, int life, int amount, Type type) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 1.0;
		
		basicInitialization(type);
	}
	
	public CoinBag(Type type) {
		basicInitialization(type);
	}
	
	public void basicInitialization(Type type) {
		this.sprite = Sprite.GoldenCoin;
		this.name = "Small Coin Bag";
		this.dropchance = 5;
		this.rarity = 3;
		this.price = "0";
		level = Game.getGame().getLevel();
		this.invulnerable = true;
		this.type = type;
		this.stackSize = 100;
		this.contents = 0;
		
		//if (this.type == this.type.RANDOM) {
		randGen = (Rand.nextInt((4 - 1) + 1) + 1);
	if (randGen == 4 || this.type.equals(Type.LARGE)) {
		this.type = Type.LARGE;
		this.name = "Large Coin Bag";
		contents = Game.getGame().getPlayer().Lvl * 100 + (int)(Math.random() * 500);
		this.price = String.valueOf(contents);

	} else if (randGen == 3 || this.type.equals(Type.MEDIUM)) {
		this.type = Type.MEDIUM;
		this.name = "Moderate Coin Bag";
		contents =Game.getGame().getPlayer().Lvl * 10 + (int)(Math.random() * 250);
		this.price = String.valueOf(contents);
		
	} else if (randGen == 2 || this.type.equals(Type.SMALL)) {
		this.type = Type.SMALL;
		this.name = "Small Coin Bag";
		contents = Game.getGame().getPlayer().Lvl * 5 + (int)(Math.random() * 100);
		this.price = String.valueOf(contents);
		
	} else if (randGen == 1 || this.type.equals(Type.POCKETCHANGE)) {
		this.type = Type.POCKETCHANGE;
		this.name = "Pocket Change";
		contents = Game.getGame().getPlayer().Lvl + (int)(Math.random() * 10);
		this.price = String.valueOf(contents);
	}
		//}
		this.desc = "Adds\n$" +this.price;
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
	
	
	public void generateEasyItems() {
	}
	
	public void generateNormalItems() {
	}
	
	private void generateHardItems() {
	}

	private void generateAdvancedItems() {
	}
	
	
	
	public void decideGeneration() {
		if (Game.getGame().getLevel() != null) {
			if (type == type.POCKETCHANGE) {
				generateEasyItems();
			} else if (type == type.SMALL) {
				generateNormalItems();
			} else if (type == type.MEDIUM) {
			generateHardItems();
			} else if (type == type.LARGE) {
				generateAdvancedItems();
			}
			//testItem();
		}
	}
	
	
	
	public boolean clickEvent() {
		boolean used = true;
		List<PlayerMP> players = level.getPlayers();
			level.getClientPlayer().money += contents;
			Sound.Play(Sound.coin, false);
		return used;
	}
	
			public void update() {
				decideGeneration();
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
				
				//List<Player> players1 = level.players;	
				
			/*for (int i = 0; i < players1.size(); i++) {
				if (players1.get(i).mobhealth >= 20) {
					maxhealth = true;
				} else {
					maxhealth = false;
				}
			}
				if (!maxhealth) {
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

