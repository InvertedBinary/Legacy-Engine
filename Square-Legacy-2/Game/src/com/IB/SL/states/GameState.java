package com.IB.SL.states;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;

public abstract class GameState {
	
	protected GSM gsm;
	protected Game game;
	
	protected GameState(GSM gsm) {
		this.gsm = gsm;
	}
	
	public abstract void update();
	public abstract void render(Screen screen);

}
