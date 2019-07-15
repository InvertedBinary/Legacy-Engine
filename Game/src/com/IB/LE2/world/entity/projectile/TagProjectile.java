package com.IB.LE2.world.entity.projectile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.TagMob;

public class TagProjectile extends Projectile {
	private static final long serialVersionUID = -8287209713181341484L;

	public int FireRate = 0;

	protected String PATH = "";
	protected String TAG = "";
	private InputStream tag_stream = null;
	protected boolean external_tag = false;

	private String reading_tag;
	private String current_tag;
	
	public HashMap<String, String> tags = new HashMap<>();


	public TagProjectile(double x, double y, String XML, Entity origin) {
		this.init(x, y, 0);
		this.angle = angle();
		this.origin = origin;
		LoadTags(XML);
	}

	public TagProjectile(double x, double y, double angle, String XML) {
		this.init(x, y, angle);
		LoadTags(XML);
	}

	public TagProjectile(double x, double y, double angle, String XML, Entity origin) {
		this.init(x, y, angle);
		this.origin = origin;
		LoadTags(XML);
	}

	public void init(double x, double y, double angle) {
		this.sprite = Sprite.get("Grass");
		this.xOrigin = x;
		this.yOrigin = y;
		x(x);
		y(y);
		this.angle = angle;

		Audio.Play("Explosion4");
	}

	public void update() {
		time++;

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
	
	private void LoadTags(String path) {

		this.range = 200;
		
		nx += speed * Math.cos(angle);
		ny += speed * Math.sin(angle);
	}

	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}

}
