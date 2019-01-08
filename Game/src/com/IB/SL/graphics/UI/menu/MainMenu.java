package com.IB.SL.graphics.UI.menu;

import com.IB.SL.Boot;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.part.UI_Button;
import com.IB.SL.level.Level;

public class MainMenu extends UI_Menu {

	public static SpriteSheet TitleCont = new SpriteSheet(new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/ContinueGame.png", 111, 30), 0, 0, 1, 2, 111, 15);
	public transient  AnimatedSprite Title_Cont_anim = new AnimatedSprite(TitleCont, 1, 1, 1);
	
	public static SpriteSheet TitleChar = new SpriteSheet(new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/Characters.png", 111, 30), 0, 0, 1, 2, 111, 15);
	public transient  AnimatedSprite Title_Char_anim = new AnimatedSprite(TitleChar, 1, 1, 1);

	UI_Button btn_Continue = new UI_Button(150, 128, Title_Cont_anim);
	UI_Button btn_Characters = new UI_Button(150, 148, Title_Char_anim);

	public int yOffset = 0;
	
	public MainMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
		ui.addUI(btn_Continue);
		ui.addUI(btn_Characters);
	}
	
	public void onLoad() {
		suspend = UI_Menu.SUS_ALL;
		if (Boot.get() != null) {
			if (Boot.get().getLevel() != null) {
				if (Boot.get().getLevel().players.size() > 0) {
					Boot.get().getPlayer().remove();
				}
			}
		}

	}
	
	public void onUnload() {
		suspend = UI_Menu.SUS_NON;
	}
	
	public void updateUnloaded() {
		if (enabled == false) {
			if (getKey() != null) {
				if (getKey().Pause) {
					load(this, false);
					getKey().Pause = false;
				}
			}
		}
	}
	
	public void update() {
		if (btn_Continue.clicked) {
			this.continueGame();
		}
		
		if (getKey() != null) {
			if (getKey().Pause) {
				unload(this);
			}
		}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
	}
	

}
