package com.IB.SL.states;

import java.util.Stack;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;

public class GSM {
	
	private Game game;
	private Stack<GameState> gameStates;
	
	public static final byte INGAME = 2;
	public static final byte MENU = 6;

	public GSM(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(INGAME);
	}
	
	public Game game() { return game; }
	
	public void update() {
		gameStates.peek().update();
	}
	
	public void render(Screen screen) {
		gameStates.peek().render(screen);
	}
	
	private GameState getState(int state) {
		if (state == INGAME) return new INGAME(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	public void popState() {
		GameState g = gameStates.pop();
	}
	
	
}
