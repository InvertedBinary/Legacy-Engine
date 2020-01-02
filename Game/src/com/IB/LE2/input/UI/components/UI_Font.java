package com.IB.LE2.input.UI.components;

import java.util.HashMap;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;

public class UI_Font {

	private transient SpriteSheet sheet;
	private transient Sprite[] chars;

	private transient static final String STANDARD_CHARMAP = 
			"ABCDEFGHIJKLM" + // 1
			"NOPQRSTUVWXYZ" + // 2
			"abcdefghijklm" + // 3
			"nopqrstuvwxyz" + // 4
			"0123456789.,'" + // 5
			"'\";:!@$%()-+"; // 6
	private transient String charmap = STANDARD_CHARMAP;

	private transient String name;
	private transient String path;
	
	private transient TagReader tags;
	
	private transient int spacing = 0;
	private transient int size = 8;
	private transient int scale = 1;
	
	private static HashMap<String, UI_Font> fonts = new HashMap<>();

	public UI_Font(String path) {
		this.path = path;
		InitTags(path);
		
		fonts.put(name, this);
	}
	
	public static UI_Font getFont(String fontname) {
		return fonts.get(fontname);
	}
	
	public static UI_Font GenFont(String path) {
		return new UI_Font(path);
	}
	
	public void InitTags(String path) {
		UI_Font ref = this;
		this.tags = new TagReader(path, "font", new TagReadListener() {
			@Override
			public void TagsRead() {
				if (!processAllTags())
					Boot.log("Unable to recognize one or more tags- ensure you are writing tags for this version of Legacy Engine!", "UI_Font", true);
			}
			
			@Override
			public void TagsError() {
				Boot.log("Error reading tags! -- Aborting", "UI_Font", true);
				fonts.remove(ref.name);
			}
		});
		
		tags.start();
	}
	
	String tmpmap = "";
	public boolean processAllTags() {
		boolean result = true;
		for (Tag i : tags.getTags()) {
			if (!processTag(i)) result = false;
		}
		
		System.out.println(charmap);
		this.charmap = tmpmap;
		System.out.println(charmap);
		
		return result;
	}

	public boolean processTag(Tag tag) {
		boolean result = true;
		
		String val = tag.value;
		
		switch (tag.uri) {
		case "font.sheet":
			this.name = val;
			this.size = tag.get("charsize", 8);
			
			String imgpath = path.substring(0, path.lastIndexOf('\\')) + "\\" + tag.get("src", "SquareFont8x8.png");
			
			this.sheet = new SpriteSheet(imgpath, size);
			this.chars = Sprite.split(sheet);
			break;
		case "font.charmap.row":
			tmpmap += val;
			break;
		default:
			break;
		}
		
		return result;
	}

	public static void Write(String font, String text, int x, int y, Screen screen, int color, boolean fixed, boolean background) {

	}

	public void render(int x, int y, String text, Screen screen, boolean fixed) {
		render(x, y, 0, 0, text, screen, fixed, false);
	}

	public void render(int x, int y, int color, String text, Screen screen, boolean fixed) {
		render(x, y, 0, color, text, screen, fixed, false);
	}

	public Sprite render(int x, int y, int spacing, int color, String text, Screen screen, boolean fixed, boolean background) {
		Sprite sprite = null;

		if (background)
			if (text.length() > 0) screen.DrawFillRect(x + size, y, (text.length() * size), size, 0xff302B23, fixed);

		this.spacing = spacing;
		int xOffset = 0;
		int line = 0;
		for (int i = 0; i < text.length(); i++) {
			xOffset += size + spacing;
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p'
					|| currentChar == 'j' || currentChar == ',' || currentChar == '.')
				yOffset = 0;
			if (currentChar == ':') yOffset = 2;
			if (currentChar == '\n') {
				xOffset = 0;
				line++;
			}
			int index = charmap.indexOf(currentChar);
			if (index == -1) continue;
			screen.DrawText(x + xOffset, y + line * (2 + size) + yOffset, chars[index], color, fixed);
			sprite = chars[index];
		}
		return sprite;
	}

}
