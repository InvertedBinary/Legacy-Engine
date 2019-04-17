package com.IB.SL.graphics.UI;

import java.util.ArrayList;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.UI.components.UI_Sprite;
import com.IB.SL.graphics.UI.components.basic.UI_Clickable;
import com.IB.SL.graphics.UI.components.basic.UI_Root;

public class UI extends UI_Root{
	//TODO: ADD FLAG FOR ELEMENT TRANSPARENCY
	private ArrayList<UI_Root> ui_Global = new ArrayList<>();
	private ArrayList<UI_Sprite> ui_Statics = new ArrayList<>();
	private ArrayList<UI_Clickable> ui_Clickables = new ArrayList<>();
	
	public UI() {
	}

	public void update() {
		for (int i = 0; i < ui_Global.size(); i++) {
			UI_Root element = ui_Global.get(i);
			if (element != null) {					
				if (element.enabled) {
					element.update();
				}
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
			this.ui_Statics.add((UI_Sprite) ui);
		} else if (ui instanceof UI_Clickable) {
			this.ui_Clickables.add((UI_Clickable) ui);
		}
		this.ui_Global.add(ui);
	}
	
	public void destroy() {
		this.ui_Global.remove(this);
	}
	
	
	public ArrayList<UI_Clickable> getClickables() {
		return ui_Clickables;
	}
	
	public ArrayList<UI_Sprite> getStatics() {
		return ui_Statics;
	}
	
	public ArrayList<UI_Root> getAll() {
		return ui_Global;
	}
	
}
