package com.IB.SL.graphics.UI.menu;

import java.util.concurrent.ThreadLocalRandom;

import com.IB.SL.Boot;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.part.UI_Button;

public class MainMenu extends UI_Menu {

	public static SpriteSheet TitleCont = new SpriteSheet(new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/ContinueGame.png", 111, 30), 0, 0, 1, 2, 111, 15);
	public transient  AnimatedSprite Title_Cont_anim = new AnimatedSprite(TitleCont, 1, 1, 1);
	
	public static SpriteSheet TitleChar = new SpriteSheet(new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/Characters.png", 111, 30), 0, 0, 1, 2, 111, 15);
	public transient  AnimatedSprite Title_Char_anim = new AnimatedSprite(TitleChar, 1, 1, 1);

	UI_Button btn_Continue = new UI_Button(150, 128, Title_Cont_anim);
	UI_Button btn_Characters = new UI_Button(150, 148, Title_Char_anim);

	boolean setScrolls = false;
	
	public int yOffset = 0;
	public boolean start_menu_anim = false;
	boolean firstRun = true;
	
	public MainMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
		ui.addUI(btn_Continue);
		ui.addUI(btn_Characters);
		
		btn_Continue.render = false;
		btn_Characters.render = false;
	}
	
	public void onLoad() {
		suspend = this.SUS_ALL;
		if (start_menu_anim != false) {
		Boot.get().getPlayer().remove();
		}
		
		if (setScrolls) {
			Boot.get().xScroll = 0;
			Boot.get().yScroll = 0;
		}
		
		if (!firstRun) {
			btn_Continue.render = true;
			btn_Characters.render = true;
		}
	}
	
	public void onUnload() {
		suspend = this.SUS_NON;
		firstRun = false;
	}
	
	private int wander_Time = 0;
	double rx, ry;
	
	private boolean cont_pressed = false;
	
	public void update() {
		
		if (btn_Continue.clicked) {
			if (wander_Time <= 140) {
				continueGame();
			}
			if (!firstRun) {
				continueGame();
			} else {
				cont_pressed = true;
				start_menu_anim = false;
			}
		}
		
		if (firstRun) {
		if (Boot.get() != null && !setScrolls) {
		Boot.get().xScroll = 680;
		Boot.get().yScroll = 600;
		setScrolls = true;
		}
		
		if (start_menu_anim && !cont_pressed) {
			yOffset = (yOffset < 168) ? yOffset += 6 : 168;
			wander_Time++;
		} else {
			if (yOffset > 0) {
				yOffset -= 6;
			}
			if (firstRun) {
			btn_Continue.render = false;
			btn_Characters.render = false;
			}
		}
		
		if (yOffset == 168 || start_menu_anim == true) {
			btn_Continue.render = true;
			btn_Characters.render = true;
		}

		if (!cont_pressed) {
			if (wander_Time % 30 == 0) {
				rx = ThreadLocalRandom.current().nextDouble(-0.5, 0.5 + 1);
				ry = ThreadLocalRandom.current().nextDouble(-0.5, 0.5 + 1);
			}

			if (wander_Time > 140) {
				Boot.get().xScroll += rx;
				Boot.get().yScroll += ry;
			}
		}
		
		if(!start_menu_anim) {
		if (Boot.get().xScroll <= Boot.get().playerSpawn.x() - Boot.get().width / 2) {
			Boot.get().xScroll+= 5;
		}
		if (Boot.get().yScroll <= Boot.get().playerSpawn.y() - Boot.get().height / 2) {
			Boot.get().yScroll+= 5;
		}
		
		if (Boot.get().xScroll >= Boot.get().playerSpawn.x() - Boot.get().width / 2) {
			Boot.get().xScroll -= 5;
		}
		if (Boot.get().yScroll >= Boot.get().playerSpawn.y() - Boot.get().height / 2) {
			Boot.get().yScroll -= 5;
		}

		
		if (Boot.get().yScroll >= (Boot.get().playerSpawn.y() - Boot.get().height / 2) - 5 && Boot.get().yScroll <= (Boot.get().playerSpawn.y() - Boot.get().height / 2) + 5) {
			if (Boot.get().xScroll >= (Boot.get().playerSpawn.x() - Boot.get().width / 2) - 5 && Boot.get().xScroll <= (Boot.get().playerSpawn.x() - Boot.get().width / 2) + 5) {
				if (cont_pressed == true) {
					if (yOffset <= 0) {
						continueGame();
					}
				}
				start_menu_anim = true;
			}
		}
		}
	} else {
		yOffset = 168;
	}
	}
	
	public void continueGame() {
		Boot.get().getMenu().unload(Boot.get().getMenu().current);
		if (Boot.get().level.players.size() == 0) {
		Boot.get().getPlayer().removed = false;
		Boot.get().getLevel().add(Boot.get().getPlayer());
		}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y - (168 - yOffset));
	}
	

}
