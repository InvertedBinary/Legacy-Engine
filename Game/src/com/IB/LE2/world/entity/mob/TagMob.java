package com.IB.LE2.world.entity.mob;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.projectile.Selector;

public class TagMob extends Mob
{
	private static final long serialVersionUID = 1L;

	protected String PATH = "";
	protected String TAG = "";
	private InputStream tag_stream = null;
	protected boolean external_tag = false;

	public HashMap<String, String> tags = new HashMap<>();

	private String reading_tag;
	private String current_tag;

	public EntityContainer inventory;

	/*private transient AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 64, 64, 7);
	private transient AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 64, 64, 7);
	private transient AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 64, 64, 7);
	private transient AnimatedSprite jump = new AnimatedSprite(SpriteSheet.player_up, 64, 64, 7);

	private transient AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_up, 64, 64, 7);

	private transient AnimatedSprite anim = idle;*/
	
	public TagMob(String tag_name)
	{
		this.TAG = tag_name;

		init();
	}

	public TagMob(String tag_name, double xi, double yi)
	{
		this.TAG = tag_name;

		init();

		this.x(xi);
		this.y(yi);
	}

	public TagMob(String path, boolean external)
	{
		this.external_tag = external;
		this.PATH = path;

		init();
	}

	public TagMob(String path, boolean external, double xi, double yi)
	{
		this.external_tag = external;
		this.PATH = path;

		init();

		this.x(xi);
		this.y(yi);
	}

	public void init() {
		if (PATH.equals("")) {
			PATH = "/Tags/Entities/" + TAG;
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

		readTags();

		try {
			tag_stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//printTags();

		if (!processAllTags()) Boot.log("One or more tags failed to process!", "TagEntity", true);

		this.sprite = Sprite.get("Anvil");
	}

	public void readTags() {
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
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (reading_tag == null) reading_tag = "";

		if (current_tag == null) current_tag = "";

		if (!qName.equals("entity") && qName != null) {
			reading_tag += (qName + ".");
			current_tag = qName;
		}
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

			setTag(reading_tag, val);
			reading_tag = reading_tag.replaceAll(current_tag, "");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		reading_tag = reading_tag.replace(qName + ".", "");
	}

	public void setTag(String tag, String value)
	{
		this.tags.put(tag, value);
	}

	public void printTags()
	{
		for (String i : tags.keySet()) {
			System.out.println("TAG::VAL=> " + i + " :: " + tags.get(i));
		}
	}

	public boolean processAllTags()
	{
		boolean result = true;
		for (String i : tags.keySet()) {
			if (!processTag(i)) result = false;
		}

		return result;
	}

	public boolean addAndProcessTag(String tag, String tag_val)
	{
		this.tags.put(tag, tag_val);
		return processTag(tag);
	}

	public boolean processTag(String tag)
	{
		boolean result = true;
		if (!tags.containsKey(tag)) return (result = false);

		String val = this.tags.get(tag);

		switch (tag) {
		case "props.name":
			this.name = val;
			break;
		case "props.id":
			this.ENTITY_ID = (int) parseNum(val);
			break;
		case "props.hp":
			//this.mobhealth = parseNum(val);
			break;
		case "props.speed":
			this.speed = parseNum(val);
			break;
		case "props.rarity":
			//this.rarity = (int) parseNum(val);
			break;
		case "props.hostility":
			//this.hostility = val;
			break;
		// TODO: Add XP ranges
		case "props.xp_min":
			//this.Exp = (long) parseNum(val);
			break;
		case "props.xp_max":
			//this.Exp = (long) parseNum(val);
			break;
		case "props.dsp_index":
			this.despawn_index = (int) parseNum(val);
			break;
		case "props.essential":
			this.essential = parseBool(val);
			break;
		case "props.det_radius":
			this.detection_radius = parseNum(val);
			break;

		case "sprite.sheet":
			// TODO: add sprite loading by name
			break;
		case "sprite.xOffset":
			this.render_xOffset = (int) parseNum(val);
			break;
		case "sprite.yOffset":
			this.render_yOffset = (int) parseNum(val);
			break;
		case "sprite.width":
			//
			break;
		case "sprite.height":
			//
			break;
		case "sprite.static_spr":
			//
			break;
		case "sprite.dspl_spr":
			//
			break;
		case "anims.right":
			//
			break;
		case "anims.left":
			//
			break;
		case "anims.jump":
			//
			break;

		case "collision.xOffset":
			this.xOffset = (int) parseNum(val);
			break;
		case "collision.yOffset":
			this.yOffset = (int) parseNum(val);
			break;
		case "collision.width":
			this.entWidth = (int) parseNum(val);
			break;
		case "collision.height":
			this.entHeight = (int) parseNum(val);
			break;

		// TODO: Add scripting engine
		case "scripts.behavior":
			break;
		case "scripts.trigger":
			break;
		case "scripts.equipped":
			break;
		case "scripts.combat":
			break;
		case "event_scripts":
			break;

		default:
			result = false;
			break;
		}

		return result;
	}

	public void processEvent(int event_id)
	{
		return;
	}

	public double parseNum(String val)
	{
		return Double.parseDouble(val);
	}

	public Boolean parseBool(String val)
	{
		return Boolean.parseBoolean(val);
	}

	public void render(Screen screen) {
		if (removed)
			return;

		screen.drawEntity((int) x() + render_xOffset, (int) y() + render_yOffset, this);
	}

	public void update()
	{
		if (VARS.do_possession && Selector.selected.equals((this))) {
		} else {
			move();
		}
	}

	public void move()
	{
		double xa = 0, ya = 0;
		List<PlayerMP> players = level.getPlayers();

		if (players.size() > 0) {
			PlayerMP p = players.get(0);
			int track_offset = (p.sprite.getWidth()) + render_xOffset + p.render_xOffset;
			if ((int) x() <= (int) p.x() + track_offset && (int) x() >= (int) p.x() - track_offset) {
				// this.vel().x(0);
			} else {
				if ((int) x() < (int) p.x() + track_offset) this.vel().x(vel().x() + speed);
				if ((int) x() > (int) p.x() - track_offset) this.vel().x(vel().x() - speed);
			}
		}

		PVector Gravity = new PVector();
		Gravity.y(VARS.Ag);

		this.vel().add(Gravity);

		ya = vel().y();
		xa = vel().x();

		move(xa, ya);

		if (this.vx() > 0) {
			vel().x(vel().x() - speed);
		} else
			if (this.vx() < 0) {
				vel().x(vel().x() + speed);
			}

		if (xa != 0) {
			walking = true;
		} else {
			walking = false;
		}

	}

	public void renderGUI(Screen screen)
	{
		if (Boot.drawDebug) {
			if (this.feetLine != null) {
				this.feetLine.drawLine(screen, true);

				Debug.drawRect(screen, (int) x() + render_xOffset, (int) y() + render_yOffset, sprite.getWidth(),
						sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, entWidth, entHeight, 0xff00FFFF, true);
			}
		}
	}

}
