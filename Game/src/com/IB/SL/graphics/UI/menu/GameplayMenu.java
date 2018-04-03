package com.IB.SL.graphics.UI.menu;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.part.UI_Button;

public class GameplayMenu extends UI_Menu {

	UI_Button btn_back = new UI_Button(276, 7, Sprite.ArrowLeft);
	
	public GameplayMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
	
		ui.addUI(btn_back);
	}
	
	
	public void update() {
			if (btn_back.clicked) {
				navUp();
			}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
	}
	

}
