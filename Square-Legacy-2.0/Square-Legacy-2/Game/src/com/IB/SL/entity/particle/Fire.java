package com.IB.SL.entity.particle;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class Fire extends Particle {

	public Fire(int x, int y, int life, int amount, double dir) {
		super(x,y,life,amount);

		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		sprite = Sprite.rotate(Sprite.fireParticle, dir);
		this.invulnerable = true;
		this.life = life + (random.nextInt(15) - 4);
	}
	
		public void update() {
			time++;
			if (time >= 7400) time = 0;
			if (time > life) remove();
		}

		
		public boolean collision(double x, double y) {
			boolean solidtwo = false;
			for(int c = 0; c < 4; c++) {
				double xt = (x - c % 2 * 16) / 16;
				double yt = (y - c / 2 * 16) / 16 + 10;
				int ix = (int) Math.ceil(xt);
				int iy = (int) Math.ceil(yt);
				if (c % 2 == 0) ix = (int)Math.floor(xt);
				if (c / 2 == 0) iy = (int)Math.floor(yt);
				if (level.getTile(ix, iy).solidtwo()) solidtwo = true;
			}
			return solidtwo;
		}
		

		public void render(Screen screen) {
			screen.renderParticle((int)x - 8, (int)y - 14, sprite, true);
		}

}

