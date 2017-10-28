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

public class HealthPot extends Item{
	public HealthPot(int x, int y, int life, int amount) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 1.0;
		
		basicInitialization();
	}
	
	public HealthPot() {
		basicInitialization();
	}
	
	public void basicInitialization() {
		this.sprite = Sprite.HealthPotion;
		this.name = "Health Potion";
		this.dropchance = 5;
		this.rarity = 3;
		this.price = "25";
		this.desc = "Heals\nUp to\n5 HP\n";
		this.item_TYPE = type_STAFF;
		//this.stackSize = 5;
		level = Game.getGame().getLevel();
	}
	
	protected void move(double x, double y) {
		super.move(x, y);
			}
	
	public boolean clickEvent() {
		boolean used = true;
		List<PlayerMP> players = level.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			double maxhealth1 = players.get(i).maxhealth - 1;
			double maxhealth2 = players.get(i).maxhealth - 2;
			double maxhealth3 = players.get(i).maxhealth - 3;
			double maxhealth4 = players.get(i).maxhealth - 4;
			double maxhealth5 = players.get(i).maxhealth - 5;

			if (players.get(i).mobhealth >= players.get(i).maxhealth) {
				used = false;
			} else {
				used = true;
			}
		if (players.get(i).mobhealth < players.get(i).maxhealth) {
			if (players.get(i).mobhealth <= maxhealth5) {
				System.out.println("maxhealth5");
				players.get(i).mobhealth += 5;
			}
			if (players.get(i).mobhealth == maxhealth4) {
				System.out.println("maxhealth4");

				players.get(i).mobhealth += 4;
			}
			if (players.get(i).mobhealth == maxhealth3) {
				System.out.println("maxhealth3");

				players.get(i).mobhealth += 3;
			}
			if (players.get(i).mobhealth == maxhealth2) {
				System.out.println("maxhealth2");

				players.get(i).mobhealth += 2;
			}
			if (players.get(i).mobhealth == maxhealth1) {
				System.out.println("maxhealth1");

				players.get(i).mobhealth += 1;
			}
			
			Sound.Play(Sound.Potion, false);
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
		
	
		public void render(Screen screen) {
			screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
			if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)xx + 3, ((int)yy - (int)zz) + 5, sprite.getWidth() / 2 + 1, sprite.getHeight() / 2 + 1, 0xFF00FF,true);
		
		}

}

