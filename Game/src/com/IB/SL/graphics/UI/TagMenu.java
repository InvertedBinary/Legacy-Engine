package com.IB.SL.graphics.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.luaj.vm2.LuaValue;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.SL.Boot;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.UI.menu.UI_Menu;
import com.IB.SL.graphics.UI.part.UI_Button;
import com.IB.SL.graphics.UI.part.UI_ButtonListener;
import com.IB.SL.graphics.UI.part.UI_Sprite;
import com.IB.SL.input.Mouse;
import com.IB.SL.util.LuaScript;

public class TagMenu extends UI_Menu
{
	protected String PATH = "";
	protected String TAG = "";
	protected String ROOT_ELEMENT = "uiset";
	
	private InputStream tag_stream = null;
	protected boolean external_tag = false;
	private Attributes current_attribs;

	private String reading_tag;
	private String current_tag;
	
	public TagMenu(String tag_name)
	{
		this.TAG = tag_name;
		this.PATH = "/XML/Menu/" + tag_name;
		
		this.external_tag = false;
		init();
	}

	public TagMenu(String path, boolean external)
	{
		this.PATH = path;
		this.TAG = path.substring(path.lastIndexOf('/') + 1, path.length());


		this.external_tag = external;
		init();
	}

	public void update() {
		if (Boot.get().key.map && Mouse.getButton() == 2) {
			this.unloadCurrent();
			this.load(new TagMenu(PATH, external_tag), true);
			Mouse.setMouseB(-1);
		}
	}
	
	public void render(Screen screen) {
		screen.renderAlphaSprite(bg, x, y);
		this.ui.render(screen);
	}

	public void onLoad() {
		initLua();
	}
	
	public void onUnload() {
		killLua();
	}
	
	public void init()
	{
		super.init(0, 0);
		
		try {
			if (!external_tag) {
				tag_stream = TagMenu.class.getResourceAsStream(PATH + ".xml");
			} else {
				tag_stream = new FileInputStream(new File(PATH + ".xml"));
			}

			if (tag_stream == null) {
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		readTags();

		try {
			tag_stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readTags()
	{
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser sp;

		System.out.println("Loading A Tag Menu..");
		try {
			sp = parserFactory.newSAXParser();
			sp.parse(tag_stream, this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (reading_tag == null) reading_tag = "";

		if (current_tag == null) current_tag = "";
		
		if (current_attribs == null) current_attribs = attributes;

		if (!qName.equals(ROOT_ELEMENT) && qName != null) {
			reading_tag += (qName + ".");
			current_tag = qName;
		}
	}

	public String pullAttrib(String key, String defaultVal) {
		if (current_attribs.getIndex(key) != -1)
			return current_attribs.getValue(key);
		
		return defaultVal;
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (reading_tag.equals("") || current_tag.equals("")) return;

		String val = (new String(ch, start, length));
		if (!(val.trim()).equals("")) {
			if (reading_tag.endsWith(".")) {
				reading_tag = reading_tag.substring(0, reading_tag.length() - 1);
			}

			//System.out.println("TAG::VAL=> " + reading_tag + " :: " + val);
			
			switch (reading_tag) {
			case "global.title":
				break;
			case "global.background":
				System.out.println("BG: " + val);
				try {					
					int hex = Long.decode(val).intValue();
					this.bg = new Sprite(Boot.width, Boot.height, hex);
				} catch (NumberFormatException e) {
					try {
						Field field = Sprite.class.getField(val);
						this.bg = (Sprite) field.get(field.getType());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				break;			
			case "global.audioloop":
				break;
			case "components.button":
				/*for (int i = 0 ; i < current_attribs.getLength(); i++) {
					System.out.println(current_attribs.getQName(i) + " :: " + current_attribs.getValue(i));
				}*/
				int x = (int)parseNum(pullAttrib("x", "0"));
				int y = (int)parseNum(pullAttrib("y", "0"));
				int w = (int)parseNum(pullAttrib("w", "0"));
				int h = (int)parseNum(pullAttrib("h", "0"));
				boolean transAnim = parseBool(pullAttrib("transAnim", "false"));
				boolean trueAnim = parseBool(pullAttrib("anim", "false"));
				String imagpth = pullAttrib("image", "");
				String onClickFunc = pullAttrib("onClick", "");
				String onHoverFunc = pullAttrib("onHover", "");
				
				UI_Button btn = null;
				Sprite spr = Sprite.Grass;

				try {					
					int hex = Long.decode(imagpth).intValue();
					spr = new Sprite(w, h, hex);
				} catch (NumberFormatException e) {
					try {
						Field field = Sprite.class.getField(imagpth);
						spr = (Sprite) field.get(field.getType());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				if (imagpth.equals("")) {
					btn = new UI_Button(x, y, w, h);
				} else if (!trueAnim) {
					btn = new UI_Button(x, y, spr, transAnim);
				} else {
					btn = new UI_Button(x, y, w, h);
					//AnimatedSprite aspr = new AnimatedSprite();
					//addUI(new UI_Button(x, y, aspr));
				}
				
				btn.addListener(new UI_ButtonListener() {
					@Override
					public void ButtonClick() {
				        LuaValue ClickCall = ls.globals.get(onClickFunc);
				        ClickCall.call();
					}

					@Override
					public void ButtonHover() {
				        LuaValue HoverCall = ls.globals.get(onHoverFunc);
				        HoverCall.call();
				    }
				});
				
				addUI(btn);
				
				break;
			case "components.label":
				break;
			case "components.image":
				int imagx = (int)parseNum(pullAttrib("x", "0"));
				int imagy = (int)parseNum(pullAttrib("y", "0"));
				int imagw = (int)parseNum(pullAttrib("w", "0"));
				int imagh = (int)parseNum(pullAttrib("h", "0"));
				String imagimagpth = val;
				
				Sprite imagspr = Sprite.Grass;

				try {					
					int hex = Long.decode(imagimagpth).intValue();
					imagspr = new Sprite(imagw, imagh, hex);
				} catch (NumberFormatException e) {
					try {
						Field field = Sprite.class.getField(imagimagpth);
						imagspr = (Sprite) field.get(field.getType());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				UI_Sprite uis = new UI_Sprite(imagx, imagy, imagspr);
				addUI(uis);
				
				break;
			default:
				System.out.println(" - !! Unknown Menu Component !!: " + reading_tag);
				break;
			}
			
			reading_tag = reading_tag.replaceAll(current_tag, "");
		}
	}
	
	public void killLua() {
		this.loadedLua = false;
		try {
			luaThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean runningLua() {
		return this.loadedLua;
	}
	
	public void initLua() {
		loadLua();
	}
	
	public Thread luaThread;
	LuaScript ls;
	boolean loadedLua = false;
	public void loadLua() {
		try {
		String luaString = this.PATH + ".lua";
		ls = new LuaScript(luaString);
		ls.addGlobal("level", this);
		ls.addGlobal("g", Boot.get());
		ls.addGlobal("mainmenu", MainMenu);
		//ls.addGlobal("key", Boot.get().getInput());
		//ls.addGlobal("key", Boot.get()); <= Crashes lua when used
		
		luaThread = new Thread(ls, "Menu LUA: " + luaString);
		luaThread.start();
		loadedLua = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		reading_tag = reading_tag.replace(qName + ".", "");
	}

	public double parseNum(String val)
	{
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val)
	{
		return Boolean.parseBoolean(val);
	}
	
}
