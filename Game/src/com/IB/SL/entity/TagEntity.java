package com.IB.SL.entity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TagEntity extends Entity
{
	private static final long serialVersionUID = 1L;

	protected String PATH = "";
	protected String TAG = "";
	protected boolean external_tag = false;

	public HashMap<String, String> tags = new HashMap<>();

	protected String reading_tag;
	protected String current_tag;

	public TagEntity(String tag_name)
		{
			this.TAG = tag_name;

			init();
		}

	public TagEntity(String path, boolean external)
		{
			this.external_tag = external;
			this.PATH = path;

			init();
		}

	public void init()
		{
			readTags();
		}

	public void readTags()
		{
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser sp;

			System.out.println("Loading A Tag Entity..");
			try {
				sp = parserFactory.newSAXParser();
				System.out.println("ENT PATH: " + PATH);
				sp.parse(TagEntity.class.getResourceAsStream(PATH), this);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			if (reading_tag == null)
				reading_tag = "";
			
			if (current_tag == null)
				current_tag = "";
			
			if (!qName.equals("entity") && qName != null) {
				reading_tag += (qName + ".");
				current_tag = qName;
				//System.out.println(reading_tag);
			}
		}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException
		{
			if (reading_tag.equals("") || current_tag.equals(""))
				return;
			
			String val = (new String(ch, start, length));
			if (!(val.trim()).equals("")) {
				if (reading_tag.endsWith(".")) {
					reading_tag = reading_tag.substring(0, reading_tag.length() - 1);
				}
				System.out.println(reading_tag + " ::: " + val);
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

		}

}
