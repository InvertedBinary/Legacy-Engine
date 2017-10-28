package com.IB.SL.entity.mob.npc;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.ChestInventory;
import com.IB.SL.entity.inventory.item.consumables.HealthPot;
import com.IB.SL.entity.inventory.item.consumables.InvisPot;
import com.IB.SL.entity.inventory.item.consumables.ManaPot;
import com.IB.SL.entity.inventory.item.consumables.StaminaPot;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.interactables.Shop;

public class Waiter extends Shop {
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Barmaid_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Barmaid_up, 16, 16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Barmaid_left, 16, 16, 2);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Barmaid_right, 16, 16, 2);
	transient final double x_mult = 2;
	transient final double y_mult = 1.5;
	transient double tick = 0;
	transient double vx, vy;
	/*private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 16, 16, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 16, 16, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 16, 16, 2);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 16, 16, 2);*/
	transient private AnimatedSprite animSprite = down;
	
	transient private font8x8 font8x8;
	transient private String dialogue;
	transient private int dialogueTime = 0;
	transient private int time = 0;
	transient double xa = 0;
	transient double ya = 0;
	
//	boolean added = false;
	transient boolean addedNormal = false;
	transient private GUI gui;
	
	public Waiter(int x, int y) {
		super(x, y);
		this.x = x << 4;
		this.y = y << 4;
		
		/*this.xBound = 8;
		this.yBound = 6;*/

		this.id = 8;
		this.name = "Barmaid";
		this.maxhealth = 20;
		this.mobhealth = maxhealth;
		this.font8x8 = new font8x8();
		sprite = Sprite.playerback;
		this.hostility = hostility.PASS;
		this.effects = new ActiveEffects(7, this);
		this.ChestInventory = new ChestInventory(16, this);
		ChestInventory.setType(ChestInventory.chestType.Shop);
	}
	
	
	public boolean CheckDialogue(Screen screen) {
		dialogueTime++;
		
	/*	if (Game.getGame().getGui().checkBounds((int)(x / 16) - 8, (int)(y / 16) - 16, 16, 16, false)) {
			Game.setMouseIcon("/Textures/Sheets/abilitybox.png");
			System.out.println("TRUE --- OVER VILLAGER");
		} else {
			//Game.setMouseIcon("/Textures/cursor.png");
		}*/
		
		if (dialogueTime > 250) {
			dialogueTime = 0;
		}
		boolean Transport = false;
	if (time % 120 == 0) {
		if (dialogueTime < 50) {
			dialogue = "Stop Occulos!";
		} else if (dialogueTime > 50 && dialogueTime < 100){
			dialogue = "zzZZZzz";
		} else if (dialogueTime > 100 && dialogueTime < 150){
			dialogue = "Yawn";
		} else if (dialogueTime > 200 && dialogueTime < 250) {
			time = 0;
		}
	}
					if (level.getPlayersBool(this, 20)) {
			//	if (Game.getGame().getPlayer().input.generalActivator) {

			//	}
				
			
		}
		return Transport;
	}
	
	
	public void decideGeneration() {
		if (!added) {
			ChestInventory.add(new HealthPot());
			ChestInventory.add(new ManaPot());
			ChestInventory.add(new StaminaPot());
			ChestInventory.add(new InvisPot());
			added = true;
		}
	}
	
	
	
	public void update() {
		decideGeneration();
		if (time % 8 == 0) {
			this.hurt = false;
		}
		time++;
		
	/*	tick += 0.05;
		if (tick > 360) {
			tick = 0;
		}
		
		vx = Math.sin(tick) * x_mult;
		vy = Math.cos(tick) * y_mult;
		
		move(vx, vy);*/
		
		List<Player> players = level.getPlayers(this, 20);
		
		if (players.size() <= 0) {
		if ((time % 2) == 0) { 
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = (random.nextInt(3) - 1);
			ya = (random.nextInt(3) - 1);
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;			
			}
		}
		
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
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
			if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}else {
			walking = false;
		}
	}
	} else {
		int dir = oppositeDir(Game.getGame().getPlayer().direction);
		if (dir == 0)
			animSprite = down;
		if (dir == 1)
			animSprite = up;
		if (dir == 2)
			animSprite = left;
		if (dir == 3)
			animSprite = right;
			
		animSprite.setFrame(0);
	}	
	}
	
	

	
	public void render(Screen screen) {
		this.xoff = -4;
		this.yoff = -4;
		sprite = animSprite.getSprite();
		this.xOffset = -8;
		this.yOffset = -16;
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), sprite);
		CheckDialogue(screen);

	}
	
	public void renderGUI(Screen screen) {
		//this.CheckDialogue(screen);
		OpenChest(screen);
	}

}
