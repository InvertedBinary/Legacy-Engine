package com.IB.SL.graphics.Weather;

import java.util.ArrayList;

import com.IB.SL.graphics.Screen;

public class Rain {
	
	ArrayList<RainPart> rains = new ArrayList<RainPart>();

	
	public Rain() {
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 0));
		}
		
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 25));
		}
		
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 50));
		}
		
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 75));
		}
		
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 100));
		}
	
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 125));
		}
		
		for (int i = 0; i < 50; i++) {
			rains.add(new RainPart(-180 + (i*10), 150));
		}
	
	}
	
	public void update() {
		for (int i = 0; i < rains.size(); i++) {
			rains.get(i).update();
		}
	}
	
	public void update(boolean in_render) {
		for (int i = 0; i < rains.size(); i++) {
			rains.get(i).update(in_render);
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < rains.size(); i++) {
			rains.get(i).render(screen);
		}
	}
	
	
}
