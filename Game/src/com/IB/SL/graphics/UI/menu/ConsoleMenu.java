package com.IB.SL.graphics.UI.menu;

import com.IB.SL.Boot;
import com.IB.SL.entity.projectile.Selector;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.part.TextBox;
import com.IB.SL.input.Commands;

public class ConsoleMenu extends UI_Menu {

	public transient TextBox cmd_box;
	public transient Commands cmds;

	public ConsoleMenu(int x, int y, Sprite bg) {
		this.bg = bg;
		init(x, y);
	}
	
	public void onLoad() {
		Boot.get().getPlayer().input.suspendInput();

		if (cmd_box == null) {
			cmd_box = new TextBox(5, 5, 266, 19, Boot.get().key, -1, false);
			cmd_box.desc = "Execute Command:";
			cmd_box.useCmds = true;
			cmd_box.acceptable.add("!");
			cmd_box.acceptable.add(",");
			cmd_box.acceptable.add(".");
		}
		
		if (cmds == null) {
			cmds = new Commands();
		}
		
		cmd_box.focused = true;
		cmd_box.update();
	}
	
	public void onUnload() {
		Boot.get().getPlayer().input.resumeInput();
		cmd_box = null;
	}
	
	public void updateUnloaded() {
		if (enabled == false) {
			if (getKey() != null) {
				if (getKey().console) {
					load(this, false);
					getKey().console = false;
				}
			}
		}
	}
	
	public void update() {
		cmd_box.update();
		if (getKey() != null) {
			if (getKey().console) {
				unload(this);
			}
		}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
		cmd_box.render(screen);
		font8x8.render(x, y + 20, 1, 0xFFFFFF, cmd_box.history, screen, false, false);
		if (Selector.selected != null)
			font8x8.render(x, Boot.height - 16, 1, 0xFFFFFF, Selector.selected.toString(), screen, false, false);
		else
			font8x8.render(x, Boot.height - 16, 1, 0xFFFFFF, "No Selection", screen, false, false);
	}
}
