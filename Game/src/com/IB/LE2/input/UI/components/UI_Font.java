package com.IB.LE2.input.UI.components;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;

public class UI_Font {
	
	private transient final SpriteSheet sheet;
	private transient final Sprite[] chars;
	
	private transient final static String charmap = 
			 "ABCDEFGHIJKLM" + // 1
			 "NOPQRSTUVWXYZ" + // 2
			 "abcdefghijklm" + // 3
  			 "nopqrstuvwxyz" + // 4
  			 "0123456789.,'" + // 5
  			 "'“”;:!@$%()-+" ; // 6
	
	private transient int spacing;
	private transient int size;
	private transient int scale = 1;
	
	private static HashMap<String, UI_Font> fonts = new HashMap<>();
	
	public static UI_Font getFont(String fontname) {
		return fonts.get(fontname);
	}
	
	public static UI_Font LoadFont(String path) {
		try {
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(path);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Font");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element sheet = (Element) ((Element) nNode).getElementsByTagName("sheet").item(0);
					String sheetpath = path.substring(0, path.lastIndexOf('\\')) + "/" + sheet.getAttribute("src");
					int width = Integer.parseInt(sheet.getAttribute("charsize"));
					System.out.println("sheet: " + sheet.getTextContent() + " PATH: " + sheetpath + " width: " + width);
					fonts.put(sheet.getTextContent(), new UI_Font(sheetpath, width));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UI_Font(String charsheet, int charsize) {
		sheet = new SpriteSheet(charsheet, charsize);
		chars = Sprite.split(sheet);
		
		this.size = charsize;
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
			if (text.length() > 0)
				screen.DrawFillRect(x + size, y, (text.length() * size), size, 0xff302B23, fixed);				
		
		this.spacing = spacing;
		int xOffset = 0;
		int line = 0;
		for(int i = 0; i < text.length(); i++) {
			xOffset += size + spacing; 
			int yOffset = 0;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j' || currentChar == ',' || currentChar == '.') yOffset = 0;
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
