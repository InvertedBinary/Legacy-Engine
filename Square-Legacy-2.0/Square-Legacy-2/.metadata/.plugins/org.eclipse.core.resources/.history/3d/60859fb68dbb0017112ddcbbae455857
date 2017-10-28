package com.IB.SL.entity.mob.debug;

import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;

public class Shooting extends Mob{

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.UndeadCaster_down, 16, 16, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.UndeadCaster_up, 16, 16, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.UndeadCaster_left, 16, 16, 2);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.UndeadCaster_right, 16, 16, 2);
	
	private AnimatedSprite animSprite = down;
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	
	public Shooting(int x, int y) {
		this.mobhealth = 1;
		this.x = x << 4;
		this.y = y << 4;
		this.name = "Mob.testMob.Shooter01.name";
		this.mobhealth = 5000;
		sprite = Sprite.Dumby;
	}
	
	@Override
	public void update() {
		time++;
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
		Player p = level.getClientPlayer();
		double dx = p.getX() - x;
		double dy = p.getY() - y;
		double dir = Math.atan2(dy, dx);
		shootingtrackerSpam(x, y, dir);
	}

	@Override
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 8), (int) (y - 15),  this);
	}

}
