package com.IB.LE2.world.entity.projectile;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.asset.graphics.lighting.DynamicLight;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.scripting.LuaScript;

public class TagProjectile extends Projectile {
	private static final long serialVersionUID = 1L;

	private String path;
	private String LuaPath;

	private LuaScript script;
	private TagReader tags;
	
	private DynamicLight light;

	public TagProjectile(double x, double y, String name, Entity origin) {
		super(x, y);
		this.angle = CalcAngle();
		this.path = Assets.get(name);
		this.origin = origin;
		this.init(x, y, angle, path);
	}

	public TagProjectile(double x, double y, double angle, String path, Entity origin) {
		super(x, y);
		this.path = path;
		this.origin = origin;
		this.init(x, y, angle, path);
	}

	public void init(double x, double y, double angle, String path) {
		light = new DynamicLight(40, -1, 0.1);
		
		tags = new TagReader(path, "entity", new TagReadListener() {
			@Override
			public void TagsRead() {
				if (!processAllTags())
					Boot.log("One or more tags failed to be processed.", "TagProjectile", true);
			}

			@Override
			public void TagsError() {
				Boot.log("An error occurred reading tags for a projectile", "TagProjectile", true);
			}
		});
		
		tags.start();
		
		this.angle = angle;
		nx += speed * Math.cos(angle);
		ny += speed * Math.sin(angle);
		
		LoadLua();
	}
	
	public void LoadLua() {
		try {
			if (!LuaPath.endsWith(".lua"))
				LuaPath += ".lua";
			
			script = new LuaScript(LuaPath);
			script.AddGeneralGlobals();
			
			script.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean processAllTags() {
		boolean result = true;
		for (Tag i : tags.getTags()) {
			if (i.holdsData())
			if (!processTag(i)) result = false;
		}

		return result;
	}

	public boolean processTag(Tag tag) {
		boolean result = true;
		String val = tag.value;
		
		switch (tag.uri) {
		case "entity.props.name":
			this.name = val;
			break;
		case "entity.props.health":
			this.health = parseNum(val);
			break;
		case "entity.props.speed":
			this.speed = parseNum(val);
			break;
		case "entity.props.mass":
			this.mass = (int) parseNum(val);
			break;
		case "entity.props.script":
			this.LuaPath = this.path.substring(0, path.lastIndexOf('\\') + 1) + val;
			break;
			//
		/*case "entity.vars.range":
			break;
		case "entity.vars.firerate":
			break;
		case "entity.vars.damage":
			break;
		case "entity.vars.do-rotation":
			break;*/
			//
		case "entity.sprite.xOffset":
			this.DrawXOffset = (int) parseNum(val);
			break;
		case "entity.sprite.yOffset":
			this.DrawYOffset = (int) parseNum(val);
			break;
		case "entity.sprite.static":
			this.sprite = Sprite.get(val);
			this.master = sprite;
			this.display = sprite;
			break;
		case "entity.sprite.display":
			this.display = Sprite.get(val);
			break;
		case "entity.hitbox.begin-x":
			this.xOffset = (int) parseNum(val);
			break;
		case "entity.hitbox.begin-y":
			this.yOffset = (int) parseNum(val);
			break;
		case "entity.hitbox.width":
			this.EntWidth = (int) parseNum(val);
			break;
		case "entity.hitbox.height":
			this.EntHeight = (int) parseNum(val);
			break;
			//
		default:
			if (tag.uri.startsWith("entity.vars.")) {
				set(tag.name, val);
			} else {
				result = false;
			}
			break;
		}
		
		return result;
	}

	public void update() {
		light.update(x(), y());
		
		if (CollidesLevel(this)) {
			script.call("LevelCollided", this);
		}
		
		Entity e = CollidesEntity(this, Boot.getLevel().all);
		if (e != null) {
			script.call("EntityCollided", e, this);
		}
		
		this.moveSimple();
	}
	
	public void render(Screen screen) {
		screen.DrawEntity(this, (int)x() + DrawXOffset,(int)y() + DrawYOffset);
		if (Boot.drawDebug) screen.DrawRect((int)x() - 3, (int)y() - 9, 5, 5, 0x0093FF, true);
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
