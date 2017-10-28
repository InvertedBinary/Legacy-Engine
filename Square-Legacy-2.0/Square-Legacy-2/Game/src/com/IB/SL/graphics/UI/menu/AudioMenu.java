package com.IB.SL.graphics.UI.menu;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.part.UI_Button;

public class AudioMenu extends UI_Menu {

	UI_Button btn_back = new UI_Button(276, 7, Sprite.ArrowLeft);
	UI_Button btn_muteMus = new UI_Button(10, 40, Sprite.ArrowLeft);
	public boolean musMuted = false;
	
	public AudioMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
		ui.addUI(btn_back);
		ui.addUI(btn_muteMus);
	}
	
	
	public void update() {
		if (btn_muteMus.clicked) {
			musMuted = !musMuted;
			if (musMuted) {
				//Sound.muteMus = true;
				//Sound.PlayOgg(Sound.currentOgg, false, 0);
			} else {
				//Sound.muteMus = false;
				//Sound.PlayOgg(Sound.currentOgg, true, 0.8);
			}
		}

		if (btn_back.clicked) {
			navUp();
		}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
	}
	

}
