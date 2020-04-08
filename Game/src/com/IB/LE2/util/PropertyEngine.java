package com.IB.LE2.util;

import java.util.HashMap;

public class PropertyEngine {
	HashMap<String, Boolean> properties = new HashMap<>();

	public boolean get(String s) {
		boolean b = false;
		if (properties.containsKey(s)) {
		b = properties.get(s);
		}

		return b;
	}
	
	public void set(String s, boolean val) {
		this.properties.put(s, val);
	}
}
