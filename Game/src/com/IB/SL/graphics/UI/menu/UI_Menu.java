package com.IB.SL.graphics.UI.menu;

import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import com.IB.SL.Boot;
import com.IB.SL.audio.Audio;
import com.IB.SL.graphics.Font16x;
import com.IB.SL.graphics.Font8x;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.UI;
import com.IB.SL.graphics.UI.components.basic.UI_Clickable;
import com.IB.SL.graphics.UI.components.basic.UI_Root;
import com.IB.SL.input.Keyboard;
import com.IB.SL.input.Mouse;

public class UI_Menu extends DefaultHandler {
	
	public Sprite bg;
	public SpriteSheet s_bg;
	public UI ui;
	public Font8x font8x8 = new Font8x();
	public Font16x font = new Font16x();
	public int x;
	public int y;
	public boolean enabled = false;
	public static UI_Menu current;
	public static int index = 0;
	
	public static byte suspend = 0;
	public final static byte SUS_ALL = 2;
	public final static byte SUS_UPD = 1;
	public final static byte SUS_NON = 0;
	
	public static ArrayList<UI_Menu> history = new ArrayList<UI_Menu>();
	public static ArrayList<UI_Menu> menus = new ArrayList<UI_Menu>();
	
	public static ConsoleMenu ConsoleMenu;
	
	public void addMenus() {
		menus.add(ConsoleMenu = new ConsoleMenu(0, 0, Sprite.bgFade));
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		ui = new UI();
	}
	
	public void updateUnloaded() {
	}
	
	public void update() {
		if (current != null) {
			if (current.enabled) {
				current.update();
				if (current != null) {
					current.ui.update();
				}
				distribute_input();
			}
		}
	}
	
	private void ResetClickables() {
		if (focused != null)
		focused.UnsetHover();
		focused = null;
		pressed = false;
	}
	
	private static UI_Clickable focused = null; // If not static, hover() lua still called, real fix later
	private boolean pressed = false;
	
	private void distribute_input() {
		if (focused == null && UI_Menu.current != null) {
			for (UI_Clickable ele : current.ui.getClickables()) {
				if (ele.InBounds() && (Mouse.getButton() != 1)) {
					focused = ele;
					break;
				}
			}
		}

		if (focused != null) {
			if (!focused.InBounds()) {
				focused.UnsetHover();
				pressed = false;
				focused = null;
			} else {
				focused.Hovered();
				if (Mouse.getButton() == 1) {
					if (Mouse.dragX() != 0 || Mouse.dragY() != 0)
						focused.Dragged();
					
					if (!pressed) {
						focused.OnDownClick();
						pressed = true;
					}
				}
				
				if (pressed && Mouse.getButton() == -1) {
					focused.Clicked();
					Mouse.setMouseB(-1);
					pressed = false;
				}
			}
		} else {
			if (Mouse.getButton() != -1) {
				//Process world input
			}
		}
	}
	
	public void render(Screen screen) {
		if (current != null) {
			if (current.enabled) {
			current.render(screen);
			current.ui.render(screen);
			}
		}
	}
	
	public void navUp() {
		UI_Menu menu;
		if (history.size() > 0) {
		menu = history.get(history.size() - 1);
		index = history.size() - 1;
		} else {
		menu = history.get(0);
		index = 0;
		}
		if (menu != null) {
		if (current != null) {
			unloadMenu(current);
		}
		current = menu; 
		current.enabled = true;
		
		for (int i = index; i < history.size(); i++) {
			history.remove(i);
		}
		}
	}
	
	public void load(UI_Menu menu, boolean override) {
		System.out.println("Attempting to load: " + menu);
		if (current == null || override == true) {
			if (menu != null) {
				if (current != null) {
					unload(current);
				}
				current = menu;
				current.enabled = true;
				current.onLoad();
			}
		}
	}
	
	public void unloadCurrent() {
		unload(current);
	}

	public void unload(UI_Menu menu) {
		history.add(current);
		unloadMenu(menu);
	}
	
	private void unloadMenu(UI_Menu menu) {
		if (menu != null) {
			if (menu.enabled) {
				if (ui != null)
					if (ui.getAll() != null) {
						for (UI_Root element : ui.getAll()) {
							if (element != null) {
								element.unload();
							}
						}
					}
				
				menu.ResetClickables();
				
				menu.onUnload();
				menu.enabled = false;
				if (current == menu)
					current = null;
			}
		}
	}
	
	public void continueGame() {
		Boot.get().getMenu().unload(UI_Menu.current);
		if (!Boot.get().getLevel().players.contains(Boot.get().getPlayer())) {
		Boot.get().getPlayer().removed = false;
		Boot.get().getLevel().add(Boot.get().getPlayer());
		//Boot.get().getLevel().loadLua();
		}
	}
	
	public void SetVolume(float level) {
		System.out.println("Setting TO: " + level);
		Audio.SetVolume(level);
		
		//PlayPrevious();
	}
	
	public void PlayMusic(String name, String file) {
		Audio.PlayMusic(name, file, true);
	}
	
	public void PlayPrevious() {
		if (Audio.previous_music == null)
			Audio.StopMusic();
		else
			Audio.PlayMusic(
				Audio.previous_music.name, 
				Audio.previous_music.path
			);
	}
	
	public void addUI(UI_Root component) {
		this.ui.addUI(component);
	}
	
	public Keyboard getKey() {
		return Boot.get().key;
	}
	
	public void onLoad() {
		
	}
	
	public void onUnload() {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getBG() {
		return bg;
	}
}
