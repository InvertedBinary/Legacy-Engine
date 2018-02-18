package com.IB.SL.entity.mob;

import java.net.InetAddress;

import com.IB.SL.Boot;
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
		this.setUUID(UUID);
	}

	public PlayerMP(int x, int y, String username, String UUID, InetAddress ipAddress, int port) {
		super(x, y, null, username);
		this.ipAddress = ipAddress;
		this.port = port;
		this.setUUID(UUID);
	}
	
	public PlayerMP(double x, double y, String username, String id) {
		super(x, y, null, username);
		this.setUUID(id);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		Boot.get().font.render((int)x(), (int)y(), 0xffFFFFFF, "ID: " + UUID, screen, 0, true, false);
	}
	
}
