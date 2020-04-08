package com.IB.LE2.world.level.scripting.triggers;

import java.util.ArrayList;
import java.util.HashMap;

import com.IB.LE2.world.level.scripting.LuaScript;

public class EventDispatcher {

	private static HashMap<String, ArrayList<HookedEvent>> callbacks = new HashMap<>();

	public static void SubmitAction(String event_name, Object... arg) {
		ArrayList<HookedEvent> queue = callbacks.get(event_name);
		for (HookedEvent register : queue) {
			register.invoke(arg);
		}
	}
	
	public static void RegisterHook(LuaScript luaScript, String event_name, String function) {
		ArrayList<HookedEvent> list = (callbacks.containsKey(event_name)) ? callbacks.get(event_name) : callbacks.put(event_name, new ArrayList<>());
		
		list.add(new HookedEvent(luaScript, function));
	}

}
