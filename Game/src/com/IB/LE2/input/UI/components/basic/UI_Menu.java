package com.IB.LE2.input.UI.components.basic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import com.IB.LE2.Boot;
import com.IB.LE2.input.Commands;
import com.IB.LE2.input.Keyboard;
import com.IB.LE2.input.Mouse;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.Font16x;
import com.IB.LE2.media.graphics.Font8x;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.entity.projectile.Selector;

public class UI_Menu extends DefaultHandler implements KeyListener {
	
	public Sprite bg;
	public SpriteSheet s_bg;
	public UI_Container ui;
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
	
	public void addMenus() {
	}
	
	public void init(int x, int y) {
		this.x = x;
		this.y = y;
		ui = new UI_Container();
	}
	
	public void UpdateMacros() {
		if (getKey() != null) {
			if (getKey().Pause) {
				load(new TagMenu("TestMenu"), false);
				getKey().Pause = false;
			}
			
			if (getKey().console) {
				load(new TagMenu("CmdMenu"), false);
				getKey().console = false;
			}
		}
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
		
		if (Selector.selected != null)
			font8x8.render(x, Boot.height - 16, 1, 0xFFFFFF, Selector.selected.toString(), screen, false, false);
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
		
		Boot.get().addKeyListener(menu);
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
				Boot.get().removeKeyListener(menu);
				
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
	
	public void RunCommand(String text) {
		Commands.Execute(text, Boot.get().getPlayer());
	}
	
	public void addUI(UI_Root component) {
		this.ui.addUI(component);
	}
	
	public UI_Root GetElementById(String id) {
		for (UI_Root e : ui.getAll()) {
			if (e.GetID().equals(id))
				return e;
		}
		
		return null;
	}
	
	public boolean ElementExists(String id) {
		return GetElementById(id) != null;
	}
	
	public String GetElementText(String id) {
		return GetElementById(id).GetText();
	}
	
	public void SetElementText(String id, String text) {
		GetElementById(id).SetText(text);
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

	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (ui != null)
		for (UI_Keylistener element : ui.getFields()) {
			element.KeyPressed(e);
		}
		//System.out.println("Key Pressed: " + e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("Key Released: " + e.getKeyChar());
	}
	
}
