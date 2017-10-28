package com.IB.SL.entity.inventory.item.consumables;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class RoomKey extends Item{
	
	public int rm;
	
	public RoomKey(int x, int y, int life, int amount, int rm) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 1.0;
		
		basicInitialization(rm);
	}
	
	public RoomKey(int rm) {
		basicInitialization(rm);
	}
	
	public void basicInitialization(int rm) {
		this.sprite = Sprite.RoomKey;
		this.name = "Room Key: " + rm;
		//this.dropchance = 5;
		//this.rarity = 3;
		this.price = "15";
		this.item_TYPE = type_STAFF;
		this.desc = "Use to\nsleep\nat\nan inn\nID:" + rm;
		//this.stackSize = 5;
		level = Game.getGame().level2;
	}
	
	protected void move(double x, double y) {
		super.move(x, y);
			}
	
	public boolean clickEvent() {
	/*	boolean used = true;
		List<PlayerMP> players = level.getPlayers();
		for (int i = 0; i < players.size(); i++) {
		
			
			Sound.Play(Sound.HealthPotion, false);
			}*/
		return false;
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

