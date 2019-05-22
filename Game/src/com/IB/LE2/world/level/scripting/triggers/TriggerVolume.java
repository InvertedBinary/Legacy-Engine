package com.IB.LE2.world.level.scripting.triggers;

import java.util.ArrayList;
import java.util.HashMap;

import org.luaj.vm2.LuaValue;

import com.IB.LE2.world.level.scripting.LuaScript;

public class TriggerVolume {

	public String name = "";
	public double x, y;
	public double width, height;
	
	private boolean tripped = false;
	private String function = "";
	
	ArrayList<Object> lua_args = new ArrayList<Object>();
	
	public TriggerVolume(HashMap<String, String> props) {
		this(props.get("name"), props.get("Lua Function"),
			 Double.parseDouble(props.get("x")), Double.parseDouble(props.get("y")),
			 Double.parseDouble(props.get("width")), Double.parseDouble(props.get("height"))
		);
	}
	
	public TriggerVolume(String function, double x, double y, double width, double height) {
		this("", function, x, y, width, height);
	}
	
	public TriggerVolume(String name, String function, double x, double y, double width, double height) {
		this.name = name;
		this.function = function;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void Trigger(LuaScript ls) {
		this.tripped = true;
		
        LuaValue PosCall = ls.globals.get(function);
        PosCall.call();
	}
}
