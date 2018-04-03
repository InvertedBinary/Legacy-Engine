package com.IB.SL.graphics;


public class font8x8 {

	public int spacing;
	
	private transient static SpriteSheet font = new SpriteSheet("/fonts/SquareFont8x8.png", 8);
	private transient static Sprite[] characters = Sprite.split(font);
	
	public transient boolean fixBg = false;
	private transient static String characterIndex = "ABCDEFGHIJKLM" + // 1
													 "NOPQRSTUVWXYZ" + // 2
													 "abcdefghijklm" + // 3
										   			 "nopqrstuvwxyz" + // 4
										   			 "0123456789.,'" + // 5
										   			 "'“”;:!@$%()-+"; // 6
	
	public font8x8() {
	}
	
	public void update() {
	}
	
	public void render(int x, int y, String text, Screen screen, boolean fixed, boolean background) {
	//	if (background) screen.drawFillRect(x, y, text.length(), 8, 0xff302B23, fixed);
			render(x, y, 0, 0, text, screen, fixed, background);
		}
	
	public void render(int x, int y, int color, String text, Screen screen, int useless, boolean fixed, boolean background) {
	//	if (background) screen.drawFillRect(x, y, text.length(), 8, 0xff302B23, fixed);
			render(x, y, 0, color, text, screen, fixed, background);
	}
	
	public void render(int x, int y, int spacing, String text, Screen screen, boolean fixed, boolean background) {
	//	if (background) screen.drawFillRect(x, y, text.length(), 8, 0xff302B23, fixed);
		render(x, y, spacing, 0, text, screen, fixed, background);
	}
	
	public Sprite render(int x, int y, int spacing, int color, String text, Screen screen, boolean fixed, boolean background) {
		Sprite sprite = null;
		if (background) {
			if (text.length() > 0) {
			if (fixBg) {
				screen.drawFillRect(x + 8, y, (text.length() * 8), 8, 0xff302B23, fixed);				
			} else {
				screen.drawFillRect(x + (8 + spacing) + 1, y, (text.length() * (8 + spacing)) + 1, 8, 0xff302B23, fixed);
				}
			}
		}
		
		this.spacing = spacing;

		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset += 8 + spacing; 
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
			screen.renderText(x + xOffset, y + line * 10 + yOffset, characters[index], color, fixed);
		sprite = characters[index];
		}
		return sprite;
	}
	
	public Sprite render(int x, int y, int spacing, int color, String text, Screen screen, boolean fixed, boolean background, int debug) {
		Sprite sprite = null;

		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset += 8 + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j' || currentChar == ',' || currentChar == '.') yOffset = 0;
			if (currentChar == ':') yOffset = 1;
			if (currentChar == '\n') {
				xOffset = 0;
				line++;
			}
			//System.out.print(currentChar + " ");
			int index = characterIndex.indexOf(currentChar);
			//System.out.print(index + " ");
			if (index == -1) continue;
			if (background) {
				screen.drawFillRect(xOffset + x, y + line * 10, (characters[index].getWidth()) + 1, 8, 0xff302B23, fixed);
			}
			screen.renderText(x + xOffset, y + line * 10 + yOffset, characters[index], color, fixed);
		sprite = characters[index];
		}
		return sprite;
	}
}


