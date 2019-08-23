package com.IB.LE2.input.UI;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.components.basic.UI_Clickable;
import com.IB.LE2.input.UI.components.basic.UI_Root;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.input.UI.menu.UI_Menu;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.world.entity.projectile.Selector;

public class UI_Manager {
	
	private static UI_Menu current = null;
	
	public static UI_Menu Current() {
		return current;
	}
	
	public static void SetCurrent(UI_Menu menu) {
		if (current != null) {
			Boot.get().removeKeyListener(current);
		}
		
		current = menu;
	}
	
	public static void update() {
		UpdateMacros();
		
		if (Current() != null) {
			if (Current().enabled) {
				Current().update();
				Current().ui.update();
				distribute_input();
			}
		} else {
			if (Mouse.getMouseB() != Mouse.MOUSE_RELEASED)
				if (Boot.get().getLevel().players.size() > 0)
					Boot.get().getLevel().getClientPlayer().MouseClicked(Mouse.getButton());
		}
	}
	
	public static void render(Screen screen) {
		if (Current() != null) {
			if (Current().enabled) {
				Current().render(screen);
				if (Current().ui != null)
					Current().ui.render(screen);
			}
		}
		
		if (Selector.selected != null)
			Game.font8bit.render(12, Boot.height - 16, 1, 0xFFFFFF, Selector.selected.toString(), screen, false, false);
	}
	
	public static void UpdateMacros() {
		if (Boot.get().key != null) {
			if (Boot.get().key.Pause) {
				UI_Manager.Load(new TagMenu("TestMenu"));
				Boot.get().key.Pause = false;
			}
			
			if (Boot.get().key.console) {
				UI_Manager.Load(new TagMenu("CmdMenu"));
				Boot.get().key.console = false;
			}
		}
	}
	
	public void navUp() {
	/*	UI_Menu menu;
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
		}*/
	}

	public static void Load(UI_Menu menu) {
		System.out.println("Attempting to load: " + menu);
		if (menu != null) {
			SetCurrent(menu);
			Current().enabled = true;
			Current().OnLoad();
		}
		
		Boot.get().addKeyListener(menu);
	}
	
	public static void UnloadAll() {
		UnloadCurrent();
	}
	
	public static void UnloadCurrent() {
		UI_Menu c = Current();
		SetCurrent(null);
		if (c != null) {
			if (c.enabled) {
				Boot.get().removeKeyListener(c);
				
				if (c.ui != null)
					if (c.ui.getAll() != null) {
						for (UI_Root element : c.ui.getAll()) {
							if (element != null) {
								element.unload();
							}
						}
					}
				
				ResetClickables();
				
				c.OnUnload();
				c.enabled = false;
				
			}
		}
	}
	
	
	private static void ResetClickables() {
		if (focused != null)
			focused.UnsetHover();
		
		focused = null;
		pressed = false;
	}
	
	public static UI_Clickable focused = null; // If not static, hover() lua still called, real fix later
	private static boolean pressed = false;
	
	private static void distribute_input() {
		if (focused == null && Current() != null) {
			for (UI_Clickable ele : Current().ui.getClickables()) {
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
				if (Boot.get().getLevel().players.size() > 0)
					Boot.get().getLevel().getClientPlayer().MouseClicked(Mouse.getButton());
			}
		}
	}

	
}
