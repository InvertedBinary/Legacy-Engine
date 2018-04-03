package com.IB.SL.graphics;

import java.util.HashMap;

public class font {

	public HashMap<String, Boolean> settings;
	public int spacing;
	
	private static SpriteSheet font = new SpriteSheet("/fonts/SquareFont.png", 16);
	private static Sprite[] characters = Sprite.split(font);
	
	private static String characterIndex = "ABCDEFGHIJKLM" + // 1
										   "NOPQRSTUVWXYZ" + // 2
										   "abcdefghijklm" + // 3
										   "nopqrstuvwxyz" + // 4
										   "0123456789.,'" + // 5
										   "'“”;:!@$%()-+"; // 6
	
	public font() {
	}
	
	public void update() {
	}
	
	public void render(int x, int y, String text, Screen screen, boolean fixed, boolean background) {
			render(x, y, 0, 0, text, screen, fixed, background);
		}
	
	public void render(int x, int y, int color, String text, Screen screen, int useless, boolean fixed, boolean background) {
			render(x, y, 0, color, text, screen, fixed, background);
	}
	
	public void render(int x, int y, int spacing, String text, Screen screen, boolean fixed, boolean background) {
		render(x, y, spacing, 0, text, screen, fixed, background);
	}

	
	public void render(int x, int y, int spacing, int color, String text, Screen screen, boolean fixed, boolean background) {
		if (background) {
		screen.drawFillRect(x, y, text.length() * 16, 16, 0xff302B23, fixed);
	}

		this.spacing = spacing;
		
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset += 16 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j' || currentChar == ',' || currentChar == '.') yOffset = 0;
			if (currentChar == ':') yOffset = 2;
			if (currentChar == '\n') {
				xOffset = 0;
				line++;
			}
			//System.out.print(currentChar + " ");
			int index = characterIndex.indexOf(currentChar);
			//System.out.print(index + " ");
			if (index == -1) continue;
			screen.renderText(x + xOffset, y + line * 20 + yOffset, characters[index], color, fixed);
		}
	}
	
	public void render(int x, int y, int spacing, int color, String text, Screen screen, boolean fixed, boolean background, int size) {
		if (background) {
		screen.drawFillRect(x, y, text.length() * 16, 16, 0xff302B23, fixed);
	}

		this.spacing = spacing;
		
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset += 16 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j' || currentChar == ',' || currentChar == '.') yOffset = 0;
			if (currentChar == ':') yOffset = 2;
			if (currentChar == '\n') {
				xOffset = 0;
				line++;
			}
			//System.out.print(currentChar + " ");
			int index = characterIndex.indexOf(currentChar);
			//System.out.print(index + " ");
			if (index == -1) continue;
			screen.renderSprite(x + xOffset, y + line * 20 + yOffset, Sprite.resize(characters[index], size), fixed);
		}
	}
}


