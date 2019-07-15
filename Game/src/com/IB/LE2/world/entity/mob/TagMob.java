package com.IB.LE2.world.entity.mob;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class TagMob extends Mob
{
	private static final long serialVersionUID = 1L;

	private TagReader tags;
	public EntityContainer inventory;

	public transient AnimatedSprite
		idle = Sprite.getNewAnim("PlayerIdle"),
		up = Sprite.getNewAnim("PlayerDown"),
		down = Sprite.getNewAnim("PlayerDown"),
		left = Sprite.getNewAnim("PlayerLeft"),
		right = Sprite.getNewAnim("PlayerRight");
	
	public transient AnimatedSprite anim = idle;
	
	public TagMob(String tag_name) {
		InitTags(tag_name);
	}

	public TagMob(String tag_name, double xi, double yi) {
		InitTags(tag_name);
		
		this.x(xi * TileCoord.TILE_SIZE);
		this.y(yi * TileCoord.TILE_SIZE);
	}
	
	public void InitTags(String tag_name) {
		TagMob e = this;
		this.tags = new TagReader(tag_name, "entity", new TagReadListener() {
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
		case "props.name":
			this.name = val;
			break;
		case "props.health":
			this.health = parseNum(val);
			break;
		case "props.speed":
			this.speed = parseNum(val);
			break;
		case "props.mass":
			this.mass = (int) parseNum(val);
			break;
		case "props.script":
			break;
			//
		case "vars.dsp_index":
			this.despawn_index = (int) parseNum(val);
			break;
		case "vars.essential":
			this.essential = parseBool(val);
			break;
		case "vars.det_radius":
			this.detection_radius = parseNum(val);
			break;
		case "vars.xp_min":
			break;
		case "vars.xp_max":
			break;
		case "vars.allegiance":
			break;
			//
		case "sprite.xOffset":
			this.DrawXOffset = (int) parseNum(val);
			break;
		case "sprite.yOffset":
			this.DrawYOffset = (int) parseNum(val);
			break;
		case "sprite.static":
			this.sprite = Sprite.get(val);
			break;
		case "sprite.display":
			this.display = Sprite.get(val);
			break;
			
		case "anims.down":
			this.down = Sprite.getNewAnim(val);
			break;
		case "anims.up":
			this.up = Sprite.getNewAnim(val);
			break;
		case "anims.left":
			this.left = Sprite.getNewAnim(val);
			break;
		case "anims.right":
			this.right = Sprite.getNewAnim(val);
			break;
		case "anims.idle":
			this.idle = Sprite.getNewAnim(val);
			break;
			//
		case "hitbox.begin-x":
			this.xOffset = (int) parseNum(val);
			break;
		case "hitbox.begin-y":
			this.yOffset = (int) parseNum(val);
			break;
		case "hitbox.width":
			this.EntWidth = (int) parseNum(val);
			break;
		case "hitbox.height":
			this.EntHeight = (int) parseNum(val);
			break;
			//
		default:
			if (tag.uri.startsWith("vars.")) {
				String var_name = tag.uri.substring(5);
				set(var_name, val);
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
			int track_offset = (p.sprite.getWidth()) + DrawXOffset + p.DrawXOffset;
			if ((int) x() <= (int) p.x() + track_offset && (int) x() >= (int) p.x() - track_offset) {
				// this.vel().x(0);
			} else {
				if ((int) x() < (int) p.x() + track_offset) this.vel().x(vel().x() + speed);
				if ((int) x() > (int) p.x() - track_offset) this.vel().x(vel().x() - speed);
			}
		}

		PVector Gravity = new PVector();
		Gravity.y(VARS.Ag);

		this.vel().add(Gravity);

		ya = vel().y();
		xa = vel().x();

		move(xa, ya);

		anim.update();

		if (walking) {
			anim.setFrameRate(6 - (int) this.speed / 2);
		} else {
			anim = idle;
			anim.setFrameRate(8);
		}
		
		if (this.vx() > 0) {
			vel().x(vel().x() - speed);
			anim = this.right;
		} else
			if (this.vx() < 0) {
				vel().x(vel().x() + speed);
				anim = this.left;
			}

		if (xa != 0) {
			walking = true;
		} else {
			walking = false;
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
			if (BottomBound != null) {
				BottomBound.drawLine(screen, true);

				Debug.drawRect(screen, (int) x() + DrawXOffset, (int) y() + DrawYOffset, sprite.getWidth(),
						sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, EntWidth, EntHeight, 0xff00FFFF, true);
			}
		}
	}

}
