package com.IB.LE2.world.level.scripting.triggers;

import java.util.ArrayList;
import java.util.HashMap;

import org.luaj.vm2.LuaValue;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.scripting.LuaScript;

public class EventVolume {

	public String name = "";
	public double x, y;
	public double width, height;
	
	private boolean was_tripped = false;
	
	private int delay = 0;
	private int trip_timer = -1; // -1 = Trip once, 0 = continually
	private int timer = 0;
	
	private String function = "";
	
	private Sprite display_bound;
	
	ArrayList<Object> lua_args = new ArrayList<Object>();
	
	public EventVolume(HashMap<String, String> props) {
			 this(props.get("name"), props.get("Lua Function"),
			 Double.parseDouble(props.get("x")), Double.parseDouble(props.get("y")),
			 Double.parseDouble(props.get("width")), Double.parseDouble(props.get("height"))
		);
	}
	
	public EventVolume(String function, double x, double y, double width, double height) {
		this("", function, x, y, width, height);
	}
	
	public EventVolume(String name, String function, double x, double y, double width, double height) {
		this.name = name;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.function = function;
		
		display_bound = new Sprite((int)width, (int)height, 0x3fFF0000);
	}
	
	// Call repeatedly while entity is within range
	public void Trip(LuaScript ls) {
		if (delay > 0) {
			delay--;
		}
		
		if (trip_timer == -1)
			if (!was_tripped)			
			Consequence(ls);
		else if (trip_timer == 0)
			Consequence(ls);
		else if (trip_timer > 0) {
			timer++;
			
			if (timer > trip_timer) {
				Consequence(ls);
				timer = 0;
			}
		}

		this.was_tripped = true;
	}
	
	public void SetDelay(int x) {
		this.delay = x;
	}
	
	public void SetTimer(int x) {
		this.timer = x;
		this.trip_timer = x;
	}
	
	private void Consequence(LuaScript ls) {
		ls.call(function);
	}
	
	public boolean Test(Entity e, LuaScript ls) {
		if (e.x() + e.getSprite().getWidth() > x && e.x() < x + width) {
			if ((e.y() + e.getSprite().getHeight()) > y && e.y() < y + height) {
				Trip(ls);
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
