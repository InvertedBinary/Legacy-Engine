package com.IB.SL.entity.particle;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class DefaultParticle extends Particle {

	transient private Sprite sprite;
	//private Particle particle;

	public DefaultParticle(int x, int y, int life, int amount, int hex) {
		super(x, y, life, amount);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		sprite = new Sprite(1, hex);
		this.invulnerable = true;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	public DefaultParticle(int x, int y, int life, int amount, int hex, int size) {
		super(x, y, life, amount);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		sprite = new Sprite(size, hex);
		this.invulnerable = true;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	public DefaultParticle(int x, int y, int life, int amount, Sprite spr) {
		super(x, y, life, amount);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		sprite = spr;
		this.invulnerable = true;
		this.life = life + (random.nextInt(15) - 4);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
		public void update() {
			time++;
			if (time >= 7400) time = 0;
			if (time > life) remove();
			za -= 0.9;
			
			if (zz < 0) {
				zz = 0;
				za *= -0;
				xa *= 0;
				ya *= 0;
			}
			
			move(xx + xa, (yy + ya) + (zz + za));
		}
		
		private void move(double x, double y) {
				if (collision(x, y)) {
					this.xa *= -0.2;
					this.ya *= -0.2;
					this.za *= -0.2;
				}
				this.xx += xa;
				this.yy += ya;
				this.zz += za;
	
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
			screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);
		}

}

