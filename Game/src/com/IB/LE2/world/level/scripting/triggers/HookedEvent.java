package com.IB.LE2.world.level.scripting.triggers;

import com.IB.LE2.world.level.scripting.LuaScript;

public class HookedEvent {
	
	private LuaScript script;
	private String function;
	
	public HookedEvent(LuaScript ls, String function) {
		this.script = ls;
		this.function = function;
	}
	
	public void invoke(Object[] obj) {
		script.call(function, obj);
	}

}
