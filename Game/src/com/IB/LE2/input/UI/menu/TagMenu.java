package com.IB.LE2.input.UI.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.LE2.Boot;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.components.UI_Button;
import com.IB.LE2.input.UI.components.UI_Canvas;
import com.IB.LE2.input.UI.components.UI_Label;
import com.IB.LE2.input.UI.components.UI_Slider;
import com.IB.LE2.input.UI.components.UI_Sprite;
import com.IB.LE2.input.UI.components.UI_TextField;
import com.IB.LE2.input.UI.components.UI_Toggle;
import com.IB.LE2.input.UI.components.listeners.UI_ButtonListener;
import com.IB.LE2.input.UI.components.listeners.UI_SliderListener;
import com.IB.LE2.input.UI.components.listeners.UI_TextInputListener;
import com.IB.LE2.input.UI.components.listeners.UI_UnloadListener;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.level.scripting.LuaScript;

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
	
	public UI_UnloadListener UnloadListener;
	
	public TagMenu(String tag_name)
	{
		this.TAG = tag_name;
		this.PATH = "/Tags/Menu/" + tag_name;
		
		this.external_tag = false;
	}

	public TagMenu(String path, boolean external)
	{
		this.PATH = path;
		this.TAG = path.substring(path.lastIndexOf('/') + 1, path.length());

		this.external_tag = external;
	}

	public void update() {
		if (Boot.get().key.map && Mouse.getButton() == 2) {
			UI_Manager.UnloadCurrent();
			UI_Manager.Load(new TagMenu(PATH, external_tag));
			Mouse.setMouseB(-1);
		}
	}
	
	public String GetTagName() {
		return TAG;
	}
	
	public void UpdateUnloaded() {
	}
	
	public void render(Screen screen) {
		if (bg != null)
			screen.DrawAlphaSprite(bg, x, y);
		
		this.ui.render(screen);
	}

	public void OnLoad() {
		init();
	}
	
	public void OnUnload() {
		if (this.UnloadListener != null)
			this.UnloadListener.onUnload();
		
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

		initLua();
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
						this.bg = Sprite.get(val);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				break;			
			case "global.audioloop":
				break;
			case "global.onUnload":
				String onUnloadFunc = val;
				
				this.UnloadListener = new UI_UnloadListener() {
					@Override
					public void onUnload() {
						ls.call(onUnloadFunc);
					}
				};
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
				Sprite spr = Sprite.get("Grass");

				try {					
					int hex = Long.decode(imagpth).intValue();
					spr = new Sprite(w, h, hex);
				} catch (NumberFormatException e) {
					if (!imagpth.endsWith(".png")) {
						try {
							spr = Sprite.get(imagpth);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				
				if (imagpth.equals("")) {
					btn = new UI_Button(x, y, w, h);
				} else if (!trueAnim) {
					btn = new UI_Button(x, y, spr, transAnim);
				} else {
					SpriteSheet animSheet = new SpriteSheet(new SpriteSheet("/Tags/Menu/global_assets/" + imagpth, w, h * 2), 0, 0, 1, 2, w, h);
					AnimatedSprite animSpr = new AnimatedSprite(animSheet, 1, 1, 1);
					btn = new UI_Button(x, y, animSpr);
					//AnimatedSprite aspr = new AnimatedSprite();
					//addUI(new UI_Button(x, y, animSpr));
				}
				
				btn.addListener(new UI_ButtonListener() {
					@Override
					public void ButtonClick() {
						if (ls == null) return;
				        try {
				        	ls.call(onClickFunc);
				        } catch (LuaError e) {
				        	e.printStackTrace();
				        }
					}

					@Override
					public void ButtonHover() {
						if (ls == null) return;
				        try {
				        	ls.call(onHoverFunc);
				        } catch (LuaError e) {
				        	e.printStackTrace();
				        }
				    }
				});
				
				addUI(btn);
				
				break;
			case "components.label":
				int lblx = (int)parseNum(pullAttrib("x", "0"));
				int lbly = (int)parseNum(pullAttrib("y", "0"));
				int lblhex = Long.decode(pullAttrib("color", "0")).intValue();
				int lblhvhex = Long.decode(pullAttrib("hvcolor", "" + lblhex)).intValue();
				String hyperlink = pullAttrib("hyperlink", "");

				UI_Label uilbl = new UI_Label(lblx, lbly, val);
				uilbl.setDefaultColor(lblhex);
				uilbl.fallback_color = uilbl.color;
				uilbl.hyperlink = hyperlink;
				uilbl.hover_color = lblhvhex;
				uilbl.spacing = -2;
				uilbl.font_size = 8;
				
				addUI(uilbl);
				
				break;
			case "components.image":
				int imagx = (int)parseNum(pullAttrib("x", "0"));
				int imagy = (int)parseNum(pullAttrib("y", "0"));
				int imagw = (int)parseNum(pullAttrib("w", "0"));
				int imagh = (int)parseNum(pullAttrib("h", "0"));
				double imagr = Math.toRadians(parseNum(pullAttrib("r", "0")));
				String imagimagpth = val;
				
				Sprite imagspr = Sprite.get("Grass");

				try {					
					int hex = Long.decode(imagimagpth).intValue();
					imagspr = Sprite.rotate(new Sprite(imagw, imagh, hex), imagr);
				} catch (NumberFormatException e) {
					try {
						imagspr = Sprite.rotate(Sprite.get(imagimagpth), imagr);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				UI_Sprite uis = new UI_Sprite(imagx, imagy, imagspr);
				addUI(uis);
				
				break;
			case "components.slider":
				UI_Slider uiSlider;

				int	sliderx  = (int)parseNum(pullAttrib("x",  "0"));
				int slidery  = (int)parseNum(pullAttrib("y",  "0"));
				int sliderw  = (int)parseNum(pullAttrib("w",  "0"));
				int sliderxo = (int)parseNum(pullAttrib("xo", "" + sliderw/2));
				String onPosUpdated = pullAttrib("onPosUpdated", "");

				uiSlider = new UI_Slider(sliderx, slidery, sliderw, sliderxo);
				uiSlider.railCol = Long.decode(pullAttrib("railColor", "" + uiSlider.railCol)).intValue();
				uiSlider.slideCol = Long.decode(pullAttrib("slideCol", "" + uiSlider.slideCol)).intValue();


				uiSlider.addListener(new UI_SliderListener() {
					@Override
					public void PositionChanged() {
				        ls.call(onPosUpdated, LuaValue.valueOf(uiSlider.pos));
					}
				});
				addUI(uiSlider);
				break;
			
			case "components.textfield":
				int	fieldx  = (int)parseNum(pullAttrib("x",  "0"));
				int fieldy  = (int)parseNum(pullAttrib("y",  "0"));
				int	maxchars  = (int)parseNum(pullAttrib("max",  "24"));
				boolean scrollable = parseBool(pullAttrib("scrollable", "true"));
				boolean numeric = parseBool(pullAttrib("numeric_only", "false"));
				boolean sensitive = parseBool(pullAttrib("sensitive_input", "false"));
				
				
				UI_TextField field = new UI_TextField(fieldx, fieldy, maxchars, scrollable, numeric, sensitive);
				field.SetID(pullAttrib("id", field.GetID()));
				field.prompt_text = val;
				
				String onSubmit = pullAttrib("onSubmit", "");
				String onKeyed = pullAttrib("onKeyed", "");
				field.addListener(new UI_TextInputListener() {
					@Override
					public void SubmitInput(String input) {
				        ls.call(onSubmit, LuaValue.valueOf(input));
					}

					@Override
					public void KeyEntered(char c, boolean filtered) {
				        ls.call(onKeyed, LuaValue.valueOf(c));
					}
				});
				
				addUI(field);
				
				break;
			case "components.toggle":
				System.out.println("Toggles are not fully implemented.");
				UI_Toggle toggle = new UI_Toggle(0, 0, false, null);
				addUI(toggle);
				break;
				
			case "components.canvas":
				int	canvasx = (int)parseNum(pullAttrib("x",  "0"));
				int canvasy = (int)parseNum(pullAttrib("y",  "0"));
				int canvasw = (int)parseNum(pullAttrib("width",  "0"));
				int canvash = (int)parseNum(pullAttrib("height",  "0"));
				int drawcol = Long.decode(pullAttrib("drawcol", "0")).intValue();
				int bgcol = Long.decode(pullAttrib("bgcol", "0")).intValue();

				UI_Canvas canvas = new UI_Canvas(canvasx, canvasy, canvasw, canvash);
				canvas.SetColor(drawcol);
				canvas.SetBackground(bgcol);
				addUI(canvas);
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
		ls.AddGeneralGlobals();
		
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
