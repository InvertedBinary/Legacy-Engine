package com.IB.SL.graphics.UI;

import java.util.ArrayList;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.UI.part.UI_Button;
import com.IB.SL.graphics.UI.part.UI_Root;
import com.IB.SL.graphics.UI.part.UI_Sprite;

public class UI extends UI_Root{
	//TODO: ADD FLAG FOR ELEMENT TRANSPARENCY
	public ArrayList<UI_Root> ui_Global = new ArrayList<UI_Root>();
	public ArrayList<UI_Sprite> ui_Sprites = new ArrayList<UI_Sprite>();
	public ArrayList<UI_Button> ui_Buttons = new ArrayList<UI_Button>();
	
	public UI() {
	}

	public void update() {
		for (int i = 0; i < ui_Global.size(); i++) {
			if (ui_Global.get(i).enabled) {
			ui_Global.get(i).update();
			}
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < ui_Global.size(); i++ ) {
			if (ui_Global.get(i).enabled) {
			ui_Global.get(i).render(screen);
			}
		}
	}
	
	public void addUI(UI_Root ui) {
		if(ui instanceof UI_Sprite) {
			this.ui_Sprites.add((UI_Sprite) ui);
		} else if (ui instanceof UI_Button) {
			this.ui_Buttons.add((UI_Button) ui);
		}
		this.ui_Global.add(ui);
	}
	
	public void destroy() {
		this.ui_Global.remove(this);
	}
	
	
	public ArrayList<UI_Sprite> getSprites() {
		return ui_Sprites;
	}
	
	public ArrayList<UI_Root> getGlobal() {
		return ui_Global;
	}
	
}
