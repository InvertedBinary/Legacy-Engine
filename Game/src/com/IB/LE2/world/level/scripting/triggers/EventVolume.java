package com.IB.LE2.world.level.scripting.triggers;

import java.util.HashMap;

import com.IB.LE2.Game;
import com.IB.LE2.asset.graphics.Screen;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.scripting.LuaScript;

public class EventVolume {

	public String name = "";
	public double x, y;
	public double width, height;
	
	private boolean was_tripped = false;
	
	private final int trip_timer; // -1 = Trip once, 0 = continually
	private int timer = 0;
	private int delay = 0;
	
	private String function = "";
	private Sprite display_bound;
	
	public boolean player_only = true;
	
	public EventVolume(HashMap<String, String> props) {
			 this(props.get("name"), props.get("function"), 
			 Integer.parseInt(props.get("timer")), Boolean.parseBoolean(props.get("player-only")), 
			 Double.parseDouble(props.get("x")), Double.parseDouble(props.get("y")),
			 Double.parseDouble(props.get("width")), Double.parseDouble(props.get("height"))
		);
	}
	
	public EventVolume(String function, int timer, boolean player_only, double x, double y, double width, double height) {
		this("", function, timer, player_only, x, y, width, height);
	}
	
	public EventVolume(String name, String function, int timer, boolean player_only, double x, double y, double width, double height) {
		this.name = name;
		this.function = function;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.trip_timer = timer;
		this.timer = trip_timer;
		
		this.player_only = player_only;
		
		display_bound = new Sprite((int)width, (int)height, 0x3fFF0000);
	}
	
	// Call repeatedly while entity is within range
	public void Trip(LuaScript ls, Entity e) {
		if (delay > 0) {
			delay--;
		}
		
		if (trip_timer == -1) {
			if (!was_tripped) Consequence(ls, e);
		} else if (trip_timer == 0)
			Consequence(ls, e);
		else if (trip_timer > 0) {
			timer++;
			
			if (timer > trip_timer) {
				Consequence(ls, e);
				timer = 0;
			}
		}

		this.was_tripped = true;
	}
	
	public void SetDelay(int x) {
		this.delay = x;
	}
	
	private void Consequence(LuaScript ls, Entity e) {
		if (ls == null) {
			System.out.println("Attempted signalling trigger consequence but script was corrupt!");
			return;
		}
		ls.call(function, e);
	}
	
	public boolean Test(Entity e, LuaScript ls) {
		if (e.x() + e.xOffset + e.EntWidth > x && e.x() + e.xOffset < x + width) {
			if ((e.y() + e.yOffset + e.EntHeight) > y && e.y() + e.yOffset < y + height) {
				Trip(ls, e);
				return true;
			}
		}
		
		return false;
	}
	
	public void render(Screen s) {
		s.DrawRect((int)x, (int)y, (int)width, (int)height, 0xffFF0000, true);
		s.DrawAlphaSprite(display_bound, (int)x, (int)y, true);
		
		if (!this.name.equals("")) 
			Game.font8bit.render((int)x, (int)y + 5, -2, 0xffFF3f3f, "EV: " + name, s, true, false);
		
	}
}
