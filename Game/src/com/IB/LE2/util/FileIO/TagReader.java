package com.IB.LE2.util.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.IB.LE2.world.entity.mob.TagMob;

public class TagReader extends DefaultHandler {

	protected String PATH = "";
	protected String TAG = "";
	protected String ROOT_ELEMENT;
	protected boolean external_tag = false;
	protected InputStream tag_stream = null;

	private ArrayList<Tag> tags = new ArrayList<>();

	private String reading_tag;
	private String current_tag;
	private Attributes current_attribs;
	
	public TagReadListener callbacks;
	
	
	public TagReader(String tag_name, String root_element, TagReadListener callbacks) {
		if (tag_name.startsWith("/") || tag_name.startsWith(".")) {
			//this.PATH = tag_name;
			//external_tag = true;
			PATH = tag_name;
		} else {
			this.TAG = tag_name;
		}
		
		this.callbacks = callbacks;
		this.ROOT_ELEMENT = root_element;
	}
	
	public String getPath() {
		return PATH;
	}
	
	public ArrayList<Tag> getTags() {
		return this.tags;
	}
	
	public void start() {
		if (PATH.equals("")) {
			PATH = "/Tags/Entities/" + TAG;
		}
		
		if (!PATH.endsWith(".xml") && !PATH.endsWith(".tmx")) {
			PATH += ".xml";
		}
		
		System.out.println("PATH: "  + PATH);
		
		try {
			if (!external_tag) {
				tag_stream = TagMob.class.getResourceAsStream(PATH);
			} else {
				tag_stream = new FileInputStream(new File(PATH));
			}

			if (tag_stream == null) {
				callbacks.TagsError();
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

		callbacks.TagsRead();
	}
	
	private void readTags() {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser sp;

		System.out.println("Reading tags...");
		try {
			sp = parserFactory.newSAXParser();
			sp.parse(tag_stream, this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addTag(Tag t) {
		this.tags.add(t);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if (reading_tag == null) reading_tag = "";
		if (current_tag == null) current_tag = "";
		
		current_attribs = attributes;
		
		if (!qName.equals(ROOT_ELEMENT) && qName != null) {
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
			
			HashMap<String, String> attrs = new HashMap<>();
			
			for (int i = 0; i < current_attribs.getLength(); i++) {
				attrs.put(current_attribs.getQName(i), current_attribs.getValue(i));
			}
			
			addTag(new Tag(current_tag, reading_tag, val, attrs));
			
			reading_tag = reading_tag.replaceAll(current_tag, "");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		reading_tag = reading_tag.replace(qName + ".", "");
	}

}
