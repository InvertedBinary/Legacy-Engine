package com.IB.LE2.world.inventory;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.TileCoord;

public class Item extends Entity {
	private static final long serialVersionUID = -7808848233998306038L;

	// - = - = - = - = - = - = - =
	public final byte SLOT_WEAPON = 7;
	//
	public final byte SLOT_HEAD = 0;
	public final byte SLOT_CHEST = 1;
	public final byte SLOT_ARMS = 2;
	public final byte SLOT_LEGS = 3;
	public final byte SLOT_FEET = 4;
	public final byte SLOT_MSC1 = 5;
	public final byte SLOT_MSC2 = 6;
	// - = - = - = - = - = - = - =
	
	private TagReader tags;
	
	public Item(String tag_name) {
		InitTags(tag_name);
	}

	public Item(String tag_name, double xi, double yi) {
		InitTags(tag_name);
		
		this.x(xi * TileCoord.TILE_SIZE);
		this.y(yi * TileCoord.TILE_SIZE);
	}
	
	public void InitTags(String tag_name) {
		Item e = this;
		this.tags = new TagReader(Assets.get(tag_name), "entity", new TagReadListener() {
			@Override
			public void TagsRead() {
				if (!processAllTags())
					Boot.log("Unable to recognize one or more tags- ensure you are writing tags for this version of Legacy Engine!", "Item", true);
			}
			
			@Override
			public void TagsError() {
				Boot.log("Error reading tags! -- Aborting", "Item", true);
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
			//
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
	
	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}

}
