package com.IB.SL.graphics.Weather;

import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class RainPart {

	private int ix, iy;
	private int x = -2;
	private int y = -2;
	private int col = 0x034aec;
	private int time = 0;
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	int sy = (random.nextInt(5) + 0);
	
	public RainPart(int x, int y) {
		init(x, y);
	}
	
	public RainPart(int x, int y, int color) {
		init(x, y);
		this.col = color;
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y - sy;
		this.ix = x;
		this.iy = y;
	}
	
	public void update() {
		time++;
		if (time > 100) {
			time = 0;
		}
		
		if (this.y != Game.getGame().getHeight()) {
			if (time % 10 == 0) {
				x++;
				y++;
			}
		} 
		
		if (this.y >= Game.getGame().height){
			x = ix;
			y = -2 - sy;
		}
	}
	
	public void update(boolean in_render) {
		if (in_render) {
		time++;
		if (time > 100) {
			time = 0;
			}
		}
		
		if (this.y != Game.getGame().getHeight()) {
			System.out.println("Is update");
			if (time % 10 == 0 || in_render == false) {
				if (Game.getGame().getPlayer().walking) {
					switch(Game.getGame().getPlayer().getDir()) {
					case UP:
						x+= 1;
						y+= 2 * Game.getGame().getPlayer().speed;
						break;
					case LEFT:		
						x+= 2 * Game.getGame().getPlayer().speed;
						y+= 1;
						break;
					case RIGHT: 
						x += 0.5;
						y += 1;
						break;
					case DOWN:
						x += 1;
						y += 0.5;
						break;
						default: 
							x+= 1;
							y+= 1;
							break;
					}
				
			} else {
				x+= 1;
				y+= 1;
			}
			}
		} 
		
		if (this.y >= Game.getGame().height){
			x = ix;
			y = -2 - sy;
		}
	}
	
	public void render(Screen screen) {
		screen.renderSprite(x, y, new Sprite(1, 1, col), false);
			screen.renderSprite(x + 1, y + 1, new Sprite(1, 1, col), false);
	}
	
}
