package com.IB.SL.entity.inventory.item.consumables;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;





public class AbstractMatter extends Item{
	boolean added = false;
	
	public enum Tier {
		SHARD, SMALL, MEDIUM, LARGE, RANDOM;
	}

	public Tier tier;
	int randGen = 0;
	
	protected static java.util.Random Rand = new Random();
	
	public AbstractMatter(int x, int y, int life, int amount, Tier tier) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 1.0;
		
		basicInitialization(tier);
	}
	
	public AbstractMatter(Tier tier) {
		basicInitialization(tier);
	}
	
	public void basicInitialization(Tier tier) {
		this.sprite = Sprite.NetherRecrement;
		this.name = "Abstract Matter";
		this.dropchance = 5;
		this.rarity = 3;
		this.price = "0";
		level = Game.getGame().getLevel();
		this.invulnerable = true;
		this.tier = tier;
		this.stackSize = 100;
		
		//if (this.tier == this.tier.RANDOM) {
		randGen = (Rand.nextInt((4 - 1) + 1) + 1);
	if (randGen == 4 || this.tier.equals(Tier.LARGE)) {  
		this.tier = Tier.LARGE;
		this.name = "Nether Recrement";

	} else if (randGen == 3 || this.tier.equals(Tier.MEDIUM)) {
		this.tier = Tier.MEDIUM;
		this.name = "Nether Whole";
		
	} else if (randGen == 2 || this.tier.equals(Tier.SMALL)) {
		this.tier = Tier.SMALL;
		this.name = "Nether Shard";
		
	} else if (randGen == 1 || this.tier.equals(Tier.SHARD)) {
		this.tier = Tier.SHARD;
		this.name = "Nether Fragment";
	}
		//}
		this.desc = "Give\nTo A\nSiphon\nTo\nIncite";
		
		
		this.recipe.add("Stick");
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
			if (tier == tier.SHARD) {
				generateEasyItems();
			} else if (tier == tier.SMALL) {
				generateNormalItems();
			} else if (tier == tier.MEDIUM) {
			generateHardItems();
			} else if (tier == tier.LARGE) {
				generateAdvancedItems();
			}
			//testItem();
		}
	}
	
	
	
	public boolean clickEvent() {
		return false;
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

