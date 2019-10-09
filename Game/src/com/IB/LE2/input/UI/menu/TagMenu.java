package com.IB.LE2.input.UI.menu;

import java.awt.event.KeyEvent;

import org.luaj.vm2.LuaError;

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
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.level.scripting.LuaScript;

public class TagMenu extends UI_Menu
{
	public TagReader tags;
	public LuaScript script;
	public boolean loadedLua = false;
	public UI_UnloadListener UnloadListener;
	
	private String path;
	
	public TagMenu(String name) {
		this.path = Assets.get(name);

		tags = new TagReader(path, "uiset", new TagReadListener() {
			@Override
			public void TagsRead() {
				if (!processAllTags())
					Boot.log("Unable to recognize one or more tags- ensure you are writing tags for this version of Legacy Engine!", "TagMob", true);
			}

			@Override
			public void TagsError() {
				Boot.log("An error occurred attempting to read the tags", "TagMenu.java", true);
			}
		});
	}
	
	public void update() {
		if (Boot.get().key.r && Boot.get().key.ctrl) {
			UI_Manager.UnloadCurrent();
			UI_Manager.Load(new TagMenu(tags.getPath()));
		}
	}
	
	public void UpdateUnloaded() {
	}
	
	public void render(Screen screen) {
		if (bg != null)
			screen.DrawAlphaSprite(bg, x, y);
		
		if (ui != null)
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
		
		if (ui.getAll().size() == 0)
			tags.start();
		
		initLua();
	}
	
	public boolean processAllTags() {
		boolean result = true;
		for (Tag i : tags.getTags()) {
			if (i.holdsData())
				if (!processTag(i)) result = false;
		}

		return result;
	}

	public boolean processTag(Tag t) {
		boolean result = true;
		String val = t.value;
		
		switch (t.uri) {
		case "uiset.global.title":
			break;
		case "uiset.global.background":
			System.out.println("BG: " + val);
			if (val == null) break;
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
		case "uiset.global.audioloop":
			break;
		case "uiset.global.onUnload":
			String onUnloadFunc = val;
			
			this.UnloadListener = new UI_UnloadListener() {
				@Override
				public void onUnload() {
					script.call(onUnloadFunc);
				}
			};
			break;
			
		case "uiset.components.button":
			int x = (int)parseNum(t.get("x", "0"));
			int y = (int)parseNum(t.get("y", "0"));
			int w = (int)parseNum(t.get("w", "0"));
			int h = (int)parseNum(t.get("h", "0"));
			boolean transAnim = parseBool(t.get("transAnim", "false"));
			boolean trueAnim = parseBool(t.get("anim", "false"));
			String imagpth = t.get("image", "");
			String onClickFunc = t.get("onClick", "");
			String onHoverFunc = t.get("onHover", "");
			
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
				SpriteSheet animSheet = new SpriteSheet(new SpriteSheet(path.substring(0, path.lastIndexOf('\\')) + "/global_assets/" + imagpth, w, h * 2), 0, 0, 1, 2, w, h);
				AnimatedSprite animSpr = new AnimatedSprite(animSheet, 1, 1, 1);
				btn = new UI_Button(x, y, animSpr);
				//AnimatedSprite aspr = new AnimatedSprite();
				//addUI(new UI_Button(x, y, animSpr));
			}
			
			btn.addListener(new UI_ButtonListener() {
				@Override
				public void ButtonClick() {
					if (script == null) return;
			        try {
			        	script.call(onClickFunc);
			        } catch (LuaError e) {
			        	e.printStackTrace();
			        }
				}

				@Override
				public void ButtonHover() {
					if (script == null) return;
			        try {
			        	script.call(onHoverFunc);
			        } catch (LuaError e) {
			        	e.printStackTrace();
			        }
			    }
			});
			
			btn.SetAlignment(t.get("align", ""));
			btn.SetID(t.get("id", btn.GetID()));
			
			addUI(btn);
			
			break;
		case "uiset.components.label":
			int lblx = (int)parseNum(t.get("x", "0"));
			int lbly = (int)parseNum(t.get("y", "0"));
			int lblhex = Long.decode(t.get("color", "0")).intValue();
			int lblhvhex = Long.decode(t.get("hvcolor", "" + lblhex)).intValue();
			String hyperlink = t.get("hyperlink", "");

			UI_Label uilbl = new UI_Label(lblx, lbly, val);
			uilbl.setDefaultColor(lblhex);
			uilbl.fallback_color = uilbl.color;
			uilbl.hyperlink = hyperlink;
			uilbl.hover_color = lblhvhex;
			uilbl.spacing = -2;
			uilbl.font_size = 8;
			
			uilbl.SetAlignment(t.get("align", ""));
			uilbl.SetID(t.get("id", uilbl.GetID()));
			addUI(uilbl);
			
			break;
		case "uiset.components.image":
			int imagx = (int)parseNum(t.get("x", "0"));
			int imagy = (int)parseNum(t.get("y", "0"));
			int imagw = (int)parseNum(t.get("w", "0"));
			int imagh = (int)parseNum(t.get("h", "0"));
			boolean anim = parseBool(t.get("anim", "false"));
			double imagr = parseNum(t.get("r", "-1"));
			String imagimagpth = val;
			
			Sprite imagspr = Sprite.get("Grass");
			UI_Sprite uis;
			
			if (!anim) {
				try {					
					int hex = Long.decode(imagimagpth).intValue();
					imagspr = new Sprite(imagw, imagh, hex);
				} catch (NumberFormatException e) {
					try {
						if (!anim)
							imagspr = Sprite.get(imagimagpth);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				if (imagr != -1)
					imagspr = Sprite.rotate(imagspr, Math.toRadians(imagr));
				
				uis = new UI_Sprite(imagx, imagy, imagspr);

			} else {
				AnimatedSprite sp = Sprite.getNewAnim(imagimagpth);
				uis = new UI_Sprite(imagx, imagy, sp);
			}
			
			uis.SetAlignment(t.get("align", ""));
			uis.SetID(t.get("id", uis.GetID()));

			addUI(uis);
			
			break;
		case "uiset.components.slider":
			UI_Slider uiSlider;

			int	sliderx  = (int)parseNum(t.get("x",  "0"));
			int slidery  = (int)parseNum(t.get("y",  "0"));
			int sliderw  = (int)parseNum(t.get("w",  "0"));
			int sliderxo = (int)parseNum(t.get("xo", "" + sliderw/2));
			String onPosUpdated = t.get("onPosUpdated", "");

			uiSlider = new UI_Slider(sliderx, slidery, sliderw, sliderxo);
			uiSlider.railCol = Long.decode(t.get("railColor", "" + uiSlider.railCol)).intValue();
			uiSlider.slideCol = Long.decode(t.get("slideCol", "" + uiSlider.slideCol)).intValue();


			uiSlider.addListener(new UI_SliderListener() {
				@Override
				public void PositionChanged() {
			        script.call(onPosUpdated, uiSlider.pos);
				}
			});
			addUI(uiSlider);
			break;
		
		case "uiset.components.textfield":
			int	fieldx  = (int)parseNum(t.get("x",  "0"));
			int fieldy  = (int)parseNum(t.get("y",  "0"));
			int	maxchars  = (int)parseNum(t.get("max",  "24"));
			boolean scrollable = parseBool(t.get("scrollable", "true"));
			boolean numeric = parseBool(t.get("numeric_only", "false"));
			boolean sensitive = parseBool(t.get("sensitive_input", "false"));
			
			
			UI_TextField field = new UI_TextField(fieldx, fieldy, maxchars, scrollable, numeric, sensitive);
			field.SetID(t.get("id", field.GetID()));
			field.prompt_text = val;
			
			String onSubmit = t.get("onSubmit", "");
			String onKeyed = t.get("onKeyed", "");
			field.addListener(new UI_TextInputListener() {
				@Override
				public void SubmitInput(String input) {
			        script.call(onSubmit, input);
				}

				@Override
				public void KeyEntered(char c, boolean filtered) {
			        script.call(onKeyed, c, filtered);
				}
			});
			
			addUI(field);
			
			break;
		case "uiset.components.toggle":
			System.out.println("Toggles are not fully implemented.");
			UI_Toggle toggle = new UI_Toggle(0, 0, false, null);
			addUI(toggle);
			break;
			
		case "uiset.components.canvas":
			int	canvasx = (int)parseNum(t.get("x",  "0"));
			int canvasy = (int)parseNum(t.get("y",  "0"));
			int canvasw = (int)parseNum(t.get("width",  "0"));
			int canvash = (int)parseNum(t.get("height",  "0"));
			int drawcol = Long.decode(t.get("drawcol", "0")).intValue();
			int bgcol = Long.decode(t.get("bgcol", "0")).intValue();

			UI_Canvas canvas = new UI_Canvas(canvasx, canvasy, canvasw, canvash);
			canvas.SetColor(drawcol);
			canvas.SetBackground(bgcol);
			addUI(canvas);
			break;

		default:
			System.out.println(" - !! Unknown Menu Component !!: " + t.uri);
			result = false;
			break;
		}
		return result;
	}
	
			
	public void killLua() {
		script = null;
		this.loadedLua = false;
	}
	
	public boolean runningLua() {
		return this.loadedLua;
	}
	
	public void initLua() {
		loadLua();
	}
	
	public void loadLua() {
		try {
		String luaString = path + ".lua";
		script = new LuaScript(luaString);
		script.AddGeneralGlobals();
		
		script.run();
		loadedLua = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		script.call("KeyInput", e.getKeyCode());
		//System.out.println("Key Released: " + e.getKeyCode());
	}
	
}
