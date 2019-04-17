package com.IB.SL.util;

import java.net.URL;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.IB.SL.Boot;

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
	
	public void addGlobal(String name, Object obj) {
		globals.set(name, CoerceJavaToLua.coerce(obj));
	}
	
	public void run() {
		if (script != null) {
			chunk.call();
		}
	}
}
