package com.IB.LE2.input.UI;

import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;

public class GUI extends CheckBounds {
	private static final long serialVersionUID = 1L;

	transient public Font16x font;
	transient public Font8x font8x8;

	public GUI() {
		font = new Font16x();
		font8x8 = new Font8x();
	}

	public void update() {
		UI_Manager.UpdateMacros();
		UI_Manager.update();
	}

	public void render(Screen screen) {
		UI_Manager.render(screen);
	}
	
}
