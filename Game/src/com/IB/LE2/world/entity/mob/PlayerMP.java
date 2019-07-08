package com.IB.LE2.world.entity.mob;

import java.net.InetAddress;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;

public class PlayerMP extends Player {

	public InetAddress ipAddress;
	public int port;
	
	/*private transient  AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 16, 16, 3);
	private transient  AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 16, 16, 3);
	private transient  AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 16, 16, 3);
	private transient  AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 16, 16, 3);*/
	
	private transient AnimatedSprite animSprite = down; //TODO: Is safe to delete but doing so speeds up animation rate -- figure this out later
	
	public int attempt;
	
	public PlayerMP(double x, double y, Keyboard input, String username, String UUID) {
		super(x, y, input, username);
		this.setUUID(UUID);
		this.name = username;
	}

	public PlayerMP(double x, double y, String username, String id) {
		this(x, y, null, username, id);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		super.render(screen);
		if (Boot.isConnected)
			Game.font8bit.render((int)x() - ((name.length() / 2) * 6) + 22, (int)y() - 5, -2, 0xffFFFFFF, name, screen, true, false);
	}
	
}
