package com.IB.SL.graphics.UI.menu;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.part.UI_Button;

public class SettingsMenu extends UI_Menu {

	UI_Button btn_back = new UI_Button(276, 7, Sprite.ArrowLeft);
	//UI_Button gameplay = new UI_Button(81, 47, Sprite.btn_Gameplay);
	//UI_Button audio = new UI_Button(81, 124, Sprite.btn_Audio);
	//UI_Button video = new UI_Button(81, 85, Sprite.btn_Video);
	
	public SettingsMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
	
		ui.addUI(btn_back);
		//ui.addUI(gameplay);
		//ui.addUI(audio);
		//ui.addUI(video);
	}
	
	
	public void update() {
		/*	if (gameplay.clicked) {
				this.load(gpMenu);
			}
			if (audio.clicked) {
				this.load(audMenu); 
			}
			if (video.clicked) {
				this.load(vidMenu);
			}*/
		
			if (btn_back.clicked) {
				navUp();
				Game.getGame().loadProp.savePrefs(Game.getGame());
			}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
	}
	

}
