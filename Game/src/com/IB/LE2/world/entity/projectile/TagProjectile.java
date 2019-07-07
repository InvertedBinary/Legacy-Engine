package com.IB.LE2.world.entity.projectile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.TagMob;

public class TagProjectile extends Projectile {
	private static final long serialVersionUID = -8287209713181341484L;

	public int FireRate = 0;

	protected String PATH = "";
	protected String TAG = "";
	private InputStream tag_stream = null;
	protected boolean external_tag = false;

	public HashMap<String, String> tags = new HashMap<>();

	private String reading_tag;
	private String current_tag;

	public TagProjectile(double x, double y, String XML, Entity origin) {
		this.init(x, y, 0);
		this.angle = angle();
		this.origin = origin;
		LoadTags(XML);
	}

	public TagProjectile(double x, double y, double angle, String XML) {
		this.init(x, y, angle);
		LoadTags(XML);
	}

	public TagProjectile(double x, double y, double angle, String XML, Entity origin) {
		this.init(x, y, angle);
		this.origin = origin;
		LoadTags(XML);
	}

	public void init(double x, double y, double angle) {
		this.sprite = Sprite.get("Grass");
		this.xOrigin = x;
		this.yOrigin = y;
		x(x);
		y(y);
		this.angle = angle;
	}

	public void update() {
		time++;
		
		if (CollidesLevel(this)) {
			remove();
		}
		
		this.moveSimple();
	}
	
	public void render(Screen screen) {
		screen.DrawEntity(this, (int)x() + DrawXOffset,(int)y() + DrawYOffset);
		if (Game.devModeOn) screen.DrawRect((int)x() - 3, (int)y() - 9, 5, 5, 0x0093FF, true);
	}
	
	public void renderGUI(Screen screen) {
		if (Boot.drawDebug) {
			if (BottomBound != null) {
				BottomBound.drawLine(screen, true);

				Debug.drawRect(screen, (int) x() + DrawXOffset, (int) y() + DrawYOffset, sprite.getWidth(),
						sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, EntWidth, EntHeight, 0xff00FFFF, true);
			}
		}
	}
	
	private void LoadTags(String path) {
		this.PATH = path;

		if (PATH.equals("")) {
			PATH = "/Tags/Projectiles/" + TAG;
		}

		if (!PATH.endsWith(".xml")) {
			PATH += ".xml";
		}

		try {
			if (!external_tag) {
				tag_stream = TagMob.class.getResourceAsStream(PATH);
			} else {
				tag_stream = new FileInputStream(new File(PATH));
			}

			if (tag_stream == null) {
				this.remove();
				return;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		ReadTags();

		try {
			tag_stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!processAllTags())
			Boot.log("One or more tags failed to process!", "TagProjectile", true);

		this.range = 200;
		
		nx += speed * Math.cos(angle);
		ny += speed * Math.sin(angle);
	}

	public void ReadTags() {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser sp;

		System.out.println("Loading A Tag Entity..");
		try {
			sp = parserFactory.newSAXParser();
			sp.parse(tag_stream, this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (reading_tag == null)
			reading_tag = "";

		if (current_tag == null)
			current_tag = "";

		if (!qName.equals("entity") && qName != null) {
			reading_tag += (qName + ".");
			current_tag = qName;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (reading_tag.equals("") || current_tag.equals(""))
			return;

		String val = (new String(ch, start, length));
		if (!(val.trim()).equals("")) {
			if (reading_tag.endsWith(".")) {
				reading_tag = reading_tag.substring(0, reading_tag.length() - 1);
			}

			setTag(reading_tag, val);
			reading_tag = reading_tag.replaceAll(current_tag, "");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		reading_tag = reading_tag.replace(qName + ".", "");
	}

	public void setTag(String tag, String value) {
		this.tags.put(tag, value);
	}

	public void printTags() {
		for (String i : tags.keySet()) {
			System.out.println("TAG::VAL=> " + i + " :: " + tags.get(i));
		}
	}

	public boolean processAllTags() {
		boolean result = true;
		for (String i : tags.keySet()) {
			if (!processTag(i))
				result = false;
		}

		return result;
	}

	public boolean addAndProcessTag(String tag, String tag_val) {
		this.tags.put(tag, tag_val);
		return processTag(tag);
	}

	public boolean processTag(String tag) {
		boolean result = true;
		if (!tags.containsKey(tag))
			return (result = false);

		String val = this.tags.get(tag);

		switch (tag) {
		case "props.name":
			this.name = val;
			break;
		case "props.speed":
			this.speed = parseNum(val);
			break;
		case "props.hp":
			this.damage = parseNum(val);
			break;
		
		// Sprite
		case "sprite.xOffset":
			this.DrawXOffset = (int) parseNum(val);
			break;
		case "sprite.yOffset":
			this.DrawYOffset = (int) parseNum(val);
			break;
		case "sprite.width":
			//
			break;
		case "sprite.height":
			//
			break;
		case "sprite.static_spr":
			this.sprite = Sprite.get(val);
			this.master_sprite = sprite;
			break;
		case "sprite.dspl_spr":
			break;
		default:
			result = false;
			break;
		}

		return result;
	}
	
	/*public void setProperties(Element eElement) {
		try {
		this.id = Integer.parseInt(eElement.getAttribute("id"));
		
		this.name = (eElement.getElementsByTagName("name").item(0).getTextContent());
		this.damage = Double.parseDouble(eElement.getElementsByTagName("damage").item(0).getTextContent());
		this.speed = Double.parseDouble(eElement.getElementsByTagName("speed").item(0).getTextContent());

		this.range = ThreadLocalRandom.current().nextInt(Integer.parseInt(((Element)eElement.getElementsByTagName("range").item(0)).getAttribute("min")), Integer.parseInt(((Element)eElement.getElementsByTagName("range").item(0)).getAttribute("max")) + 1);
		
		this.MovementStyle = ((Element)eElement.getElementsByTagName("movement").item(0)).getAttribute("shape");
		this.initial_rotation = Boolean.parseBoolean(((Element)eElement.getElementsByTagName("movement").item(0)).getAttribute("rot"));
		this.RoF = Integer.parseInt(((Element)eElement.getElementsByTagName("movement").item(0)).getAttribute("rof"));

		this.xOffset = Integer.parseInt(((Element)eElement.getElementsByTagName("sprite").item(0)).getAttribute("xOffset"));
		this.yOffset = Integer.parseInt(((Element)eElement.getElementsByTagName("sprite").item(0)).getAttribute("yOffset"));
		//Field field = Sprite.class.getField((((Element)eElement.getElementsByTagName("sprite").item(0)).getAttribute("Arrow")));
		//this.sprite = (Sprite) field.get(field.getType());
		
		//this.master_sprite = sprite;
		
		//r = new Rectangle((int)x, (int)y, sprite.getWidth(), sprite.getHeight());
		nx += speed * Math.cos(angle);
		ny += speed * Math.sin(angle);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public double parseNum(String val) {
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val) {
		return Boolean.parseBoolean(val);
	}

}
