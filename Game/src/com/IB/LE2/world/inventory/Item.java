package com.IB.LE2.world.inventory;

import java.awt.List;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.TagMob;
import com.IB.LE2.world.level.scripting.LuaScript;

public class Item extends TagMob {
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
		super(tag_name);
		LoadLua();
	}

	public Item(String tag_name, double xi, double yi) {
		super(tag_name, xi, yi);
		LoadLua();
	}
	
	public void render(Screen screen) {
		screen.DrawEntity(this, (int) (x() + DrawXOffset), (int) (y() + DrawYOffset));
	}
	
	public void collidedWithPlayer(Player p) {
		if (p != null) {
			script.call("PlayerCollided", p, this);
		}
	}
	
	public void collidedWith(Entity e) {
		if (e instanceof Player) {
			collidedWithPlayer((Player)e);
			return;
		}
	}
	
	public void update() {
		Player p = CollidesPlayerSimple(this, Boot.getLevel().getPlayers());
		collidedWithPlayer(p);
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
	
	public boolean processTag(Tag tag) {
		boolean result = true;
		
		String val = tag.value;
		
		switch (tag.uri) {
		case "entity.props.name":
			this.name = val;
			break;
		default:
			super.processTag(tag);
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
