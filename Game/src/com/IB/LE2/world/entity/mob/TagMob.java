package com.IB.LE2.world.entity.mob;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.AnimatedSprite;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.scripting.LuaScript;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class TagMob extends Mob
{
	private static final long serialVersionUID = 1L;

	protected String LuaPath;
	protected String path;
	protected LuaScript script;
	protected transient TagReader tags;
	public transient EntityContainer inventory;

	
	private int facing = 0;
	public transient AnimatedSprite
		idle_left  = (AnimatedSprite) Sprite.getNewAnim("PlayerIdleLeft"),
		idle_right = (AnimatedSprite) Sprite.getNewAnim("PlayerIdleRight"),
		idle_up = (AnimatedSprite) Sprite.getNewAnim("PlayerIdleUp"),
		idle_down = (AnimatedSprite) Sprite.getNewAnim("PlayerIdleDown"),
		
		up  = (AnimatedSprite) Sprite.getNewAnim("PlayerUp"),
		down  = (AnimatedSprite) Sprite.getNewAnim("PlayerDown"),
		left  = (AnimatedSprite) Sprite.getNewAnim("PlayerLeft"),
		right = (AnimatedSprite) Sprite.getNewAnim("PlayerRight");
	
	public transient AnimatedSprite anim = idle_right;
	
	public TagMob(String tag_name) {
		InitTags(tag_name);
	}

	public TagMob(String tag_name, double xi, double yi) {
		InitTags(tag_name);
		
		this.x(xi * TileCoord.TILE_SIZE);
		this.y(yi * TileCoord.TILE_SIZE);
	}
	
	public void InitTags(String tag_name) {
		this.path = Assets.get(tag_name);
		
		TagMob e = this;
		this.tags = new TagReader(Assets.get(tag_name), "entity", new TagReadListener() {
			@Override
			public void TagsRead() {
				if (!processAllTags())
					Boot.log("Unable to recognize one or more tags- ensure you are writing tags for this version of Legacy Engine!", "TagMob", true);
			}
			
			@Override
			public void TagsError() {
				Boot.log("Error reading tags! -- Aborting", "TagMob", true);
				e.remove();
			}
		});
		
		tags.start();
	}

	public boolean processAllTags() {
		boolean result = true;
		for (Tag i : tags.getTags()) {
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
			this.set("health", val);
			break;
		case "entity.props.speed":
			this.set("speed", val);
			break;
		case "entity.props.mass":
			this.set("mass", val);
			break;
		case "entity.props.script":
			this.LuaPath = this.path.substring(0, path.lastIndexOf('\\') + 1) + val;
			break;
			//
		case "entity.vars.dsp_index":
			this.despawn_index = (int) parseNum(val);
			break;
		case "entity.vars.essential":
			this.essential = parseBool(val);
			break;
		case "entity.vars.det_radius":
			this.detection_radius = parseNum(val);
			break;
		case "entity.vars.xp_min":
			break;
		case "entity.vars.xp_max":
			break;
		case "entity.vars.allegiance":
			break;
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
			
		case "entity.anims.down":
			this.down = Sprite.getNewAnim(val);
			break;
		case "entity.anims.up":
			this.up = Sprite.getNewAnim(val);
			break;
		case "entity.anims.left":
			this.left = Sprite.getNewAnim(val);
			break;
		case "entity.anims.right":
			this.right = Sprite.getNewAnim(val);
			break;
		case "entity.anims.idle":
		case "entity.anims.idle_right":
			this.idle_right = Sprite.getNewAnim(val);
			break;
		case "entity.anims.idle_left":
			this.idle_left = Sprite.getNewAnim(val);
			break;
		case "entity.anims.idle_up":
			this.idle_up = Sprite.getNewAnim(val);
			break;
		case "entity.anims.idle_down":
			this.idle_down = Sprite.getNewAnim(val);
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

	public void processEvent(int event_id) {
		// TODO: lua do
		return;
	}

	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}

	public void update() {
		if (VARS.do_possession && Selector.selected.equals((this))) {
		} else {
			((TiledLevel) level).TestEventVolumes(this);
			move();
		}
	}

	public void move() {
		double xa = 0, ya = 0;
		List<PlayerMP> players = level.getPlayers();

		if (players.size() > 0) {
			PlayerMP p = players.get(0);
			
				int track_xoffset = (p.sprite.getWidth()) + DrawXOffset + p.DrawXOffset;
				int track_yoffset = (p.sprite.getHeight()) + DrawYOffset + p.DrawYOffset;
				if ((int) x() <= (int) p.x() + track_xoffset && (int) x() >= (int) p.x() - track_xoffset) {
					// this.vel().x(0);
				} else {
					if ((int) x() < (int) p.x() + track_xoffset)
						this.vel().x(vel().x() + speed);
					if ((int) x() > (int) p.x() - track_xoffset)
						this.vel().x(vel().x() - speed);
				}

				if ((int) y() <= (int) p.y() + track_yoffset && (int) y() >= (int) p.y() - track_yoffset) {
					// this.vel().x(0);
				} else {
					if ((int) y() < (int) p.y() + track_yoffset)
						this.vel().y(vel().y() + speed);
					if ((int) y() > (int) p.y() - track_yoffset)
						this.vel().y(vel().y() - speed);
				}
		}
		
		ya = vel().y();
		xa = vel().x();

		if (!move(xa, ya)) {
			anim = getDirectionalIdleAnim();
			anim.setFrameRate(8);			
			walking = false;
		} else {
			anim.setFrameRate(6 - (int) this.speed / 2);
			walking = true;
		}
		
			if (this.vy() > 0) {
				vel().y(vel().y() - speed);
				if (vy() > 1)
				anim = this.up;
				this.facing = 1;
			} else if (this.vy() < 0) {
				if (vy() < -1)
				anim = this.down;
				vel().y(vel().y() + speed);
				this.facing = 3;
			}

			if (this.vx() > 0) {
				vel().x(vel().x() - speed);
				anim = this.right;
				this.facing = 0;
			} else if (this.vx() < 0) {
				vel().x(vel().x() + speed);
				anim = this.left;
				this.facing = 2;
			}

		anim.update();

		if (hurt > 0)
			hurt--;
	}
	
	public AnimatedSprite getDirectionalIdleAnim() {
		switch (this.facing) {
		case 1:
			return this.idle_up;
		case 2:
			return this.idle_left;
		case 3:
			return this.idle_down;
		default:
			return this.idle_right;
		}
	}
	
	public void render(Screen screen) {
		if (removed)
			return;
		
		this.sprite = anim.getSprite();

		screen.DrawEntity(this, (int) x() + DrawXOffset, (int) y() + DrawYOffset);
	}

	public void renderGUI(Screen screen) {
		if (Boot.drawDebug) {
			if (BottomBound != null) 
				BottomBound.drawLine(screen, true);

				Debug.drawRect(screen, (int) x() + DrawXOffset, (int) y() + DrawYOffset, sprite.getWidth(), sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, EntWidth, EntHeight, 0xff00FFFF, true);
		}
	}

}
