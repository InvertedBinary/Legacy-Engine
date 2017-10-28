package com.IB.SL.entity.mob.npc;

import java.util.List;

import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.quests.q_Fetch;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;

public class TestQG extends Mob{
	
	private transient AnimatedSprite down = new AnimatedSprite(SpriteSheet.Villager01_down, 16, 16, 3);
	private transient AnimatedSprite up = new AnimatedSprite(SpriteSheet.Villager01_up, 16, 16, 3);
	private transient AnimatedSprite left = new AnimatedSprite(SpriteSheet.Villager01_left, 16, 16, 2);
	private transient AnimatedSprite right = new AnimatedSprite(SpriteSheet.Villager01_right, 16, 16, 2);
	final transient double x_mult = 2;
	final transient double y_mult = 1.5;
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
	double xa = 0;
	double ya = 0;
	
	public void onLoad(Entity e) {
		this.toGive = e.toGive;
	}
	
	public TestQG(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		
		/*this.xBound = 8;
		this.yBound = 6;*/

		this.id = 8;
		this.name = "Quest Giver";
		this.maxhealth = 20;
		this.mobhealth = maxhealth;
		this.font8x8 = new font8x8();
		sprite = Sprite.playerback;
		this.hostility = hostility.PASS;
		this.effects = new ActiveEffects(7, this);
		this.toGive = new q_Fetch();
	}
	
		
	public void update() {
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
		animSprite = down;
		animSprite.setFrame(0);
	}	
	}
	
	

	
	public void render(Screen screen) {
		
		sprite = animSprite.getSprite();
		this.xOffset = -8;
		this.yOffset = -16;
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		//CheckDialogue(screen);


	}
	
	public void renderGUI(Screen screen) {
		if (level.getPlayersBool(this, 20)) {
			this.displayQuest(screen);
		}
	}

}
