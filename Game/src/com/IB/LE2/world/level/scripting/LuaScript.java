package com.IB.LE2.world.level.scripting;

import java.net.URL;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.world.entity.mob.TagMob;

public class LuaScript implements Runnable {

	public final String path;
	public final Globals globals;
	public final URL script;
	LuaValue chunk;

	
	public LuaScript(String path) {
		this.path = path;
		this.script = LuaScript.class.getResource(path);
		if (script == null) {
			Boot.log("Missing script at: " + path, "LuaScript.java", true);
		}
		this.globals = JsePlatform.standardGlobals();
		chunk = globals.loadfile(path);
	}
	
	//public static String TAGMOB = "com.IB.LE2.world.entity.mob.TagMob";
	public void AddGeneralGlobals() {
		addGlobal("level", Boot.getLevel());
		addGlobal("g", Boot.get());
		addGlobal("GAME", Game.class);
		addGlobal("menu", UI_Manager.Current());

		addGlobal(" ", this.getClass());
//		addGlobal("level", Boot.get().getLevel());
//		addGlobal("g", Boot.get());
		//addGlobal("key", Boot.get().getInput());
		//addGlobal("key", Boot.get()); <= Crashes lua when used
	}
	
	public void addGlobal(String name, Object obj) {
		globals.set(name, CoerceJavaToLua.coerce(obj));
	}
	
	public void run() {
		if (script != null) {
			chunk.call();
		}
	}
}
