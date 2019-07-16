package com.IB.LE2.world.entity.projectile;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.entity.Entity;

public class TagProjectile extends Projectile {
	private static final long serialVersionUID = 1L;

	private TagReader tags;

	public TagProjectile(double x, double y, String path, Entity origin) {
		super(x, y);
		this.init(x, y, angle, path);
		this.origin = origin;
	}

	public TagProjectile(double x, double y, double angle, String path, Entity origin) {
		super(x, y);
		this.init(x, y, angle, path);
		this.origin = origin;
	}

	public void init(double x, double y, double angle, String path) {
		this.sprite = Sprite.get("Grass");
		
		this.angle = angle;
		nx += speed * Math.cos(angle);
		ny += speed * Math.sin(angle);

		Audio.Play("Explosion4");
		
		tags = new TagReader(path, "entity", new TagReadListener() {
			@Override
			public void TagsRead() {
				
			}

			@Override
			public void TagsError() {
				Boot.log("An error occurred reading tags for a projectile", "TagProjectile", true);
			}
		});
	}

	public void update() {
		if (CollidesLevel(this)) {
			remove();
		}
		
		this.moveSimple();
	}
	
	public void render(Screen screen) {
		screen.DrawEntity(this, (int)x() + DrawXOffset,(int)y() + DrawYOffset);
		if (Game.devModeOn) screen.DrawRect((int)x() - 3, (int)y() - 9, 5, 5, 0x0093FF, true);
	}
	
	public void renderGUI(Screen screen) {
		if (Boot.drawDebug) {
			if (BottomBound != null) {
				BottomBound.drawLine(screen, true);

				Debug.drawRect(screen, (int) x() + DrawXOffset, (int) y() + DrawYOffset, sprite.getWidth(),
						sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, EntWidth, EntHeight, 0xff00FFFF, true);
			}
		}
	}
	
	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}

}
