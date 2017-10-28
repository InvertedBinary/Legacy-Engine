package com.IB.SL.entity.mob.debug;

import java.util.List;

import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.level.Node;
import com.IB.SL.util.Vector2i;

public class AStarTracker extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.UndeadCaster_down,
			16, 16, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.UndeadCaster_up, 16,
			16, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.UndeadCaster_left,
			16, 16, 2);
	private AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.UndeadCaster_right, 16, 16, 2);

	private AnimatedSprite animSprite = down;
	double xa = 0;
	double ya = 0;
	double time = 0;
	private List<Node> path = null;

	private double speed = 0.5;

	public AStarTracker(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.playerback;
		this.name = "Undead Caster";
	}

	private void move() {
	      List<Player> players = level.getPlayers(this, 100);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         int px = (int) level.getPlayerAt(0).getX();
	         int py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> 4, (int)getY() >> 4);
	         Vector2i destination = new Vector2i(px >> 4, py >> 4);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
	            }
	         }
	      } else {
	         if (time % (random.nextInt(50) + 30) == 0) {
	            xa = random.nextInt(3) - 1;
	            ya = random.nextInt(3) - 1;
	            if (random.nextInt(2) == 0) {
	               xa = 0;
	               ya = 0;
	            }
	         }
	      }
	      if (xa != 0 || ya != 0) {
	         move(xa * speed, ya * speed);
	         walking = true;
	      } else {
	         walking = false;
	      }
	   }
	public void update() {
		time++;
		move();
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
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
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 8), (int) (y - 15), this);
	}

}
