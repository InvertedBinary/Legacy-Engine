package com.IB.SL.entity.mob;

import java.net.InetAddress;

import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.input.Keyboard;

public class PlayerMP extends Player {

	public InetAddress ipAddress;
	public int port;
	
	private transient  AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 16, 16, 3);
	private transient  AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 16, 16, 3);
	private transient  AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 16, 16, 3);
	private transient  AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 16, 16, 3);
	
	private transient AnimatedSprite animSprite = down; //TODO: Is safe to delete but doing so speeds up animation rate -- figure this out later
	
	public int attempt;
	
	public PlayerMP(int x, int y, Keyboard input, String username, String UUID, InetAddress ipAddress, int port) {
		super(x, y, input, username);
		this.ipAddress = ipAddress;
		this.port = port;
		this.setUID(UUID);
	}

	public PlayerMP(int x, int y, String username, String UUID, InetAddress ipAddress, int port) {
		super(x, y, null, username);
		this.ipAddress = ipAddress;
		this.port = port;
		this.setUID(UUID);
	}
	
	@Override
	public void update() {
		super.update();
		if (walking && !riding && !raycastDIR.hasCollided()) {
			animSprite.update();					
	} else {
		animSprite.setFrame(0);			
		}
	}
	
	@Override
	public void render(Screen screen) {
		if (this != level.getClientPlayer()) {
		
	if (this.getDir() == dir.UP) {
		animSprite = up;
	} else if (this.getDir() == dir.DOWN) {
		animSprite = down;
	}
	 if (this.getDir() == dir.LEFT) {
		 animSprite = left;
	} else if (this.getDir() == dir.RIGHT) {
		animSprite = right;
		}
		this.sprite = animSprite.getSprite();
		screen.renderMobSpriteUniversal((int) (x - 8), (int) (y - 15),  sprite);
		} else {
			super.render(screen);
		}
	}
	
}
