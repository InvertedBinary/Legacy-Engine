package com.IB.SL.graphics.UI.menu;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;

public class PauseMenu extends UI_Menu {

	//UI_Button btn_gears = new UI_Button(274, 5, Sprite.Gears);
	//UI_Button btn_Resume = new UI_Button(20, 20, Sprite.Car, true);

	public PauseMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
	//	ui.addUI(btn_gears);
	//	ui.addUI(btn_Resume);
	}
	
	public void update() {
		//	if (btn_gears.clicked) {
		//		Game.getGame().Menus.load(Game.getGame().Menus.settingsMenu);
	//	}
	}
	
	public void render(Screen screen) {
		//screen.renderAlphaSprite(bg, x, y);
	}
	

}
