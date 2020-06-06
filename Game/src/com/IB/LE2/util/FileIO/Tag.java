package com.IB.LE2.util.FileIO;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;

public class Tag {

	public String value;
	public String name;
	public String uri;
	public Tag parent;
	private ArrayList<Tag> children;
	private HashMap<String, String> attributes;
	
	public Tag(String name) {
		this(name, "");
	}
	
	public Tag(String name, String uri) {
		this(name, uri, "");
	}
	
	public Tag(String name, String uri, String inner_text) {
		this(name, uri, inner_text, null);
	}
	
	public Tag(String name, String uri, String inner_text, HashMap<String, String> attrs) {
		this.name = name;
		this.attributes = attrs;
		if (attributes == null) attributes = new HashMap<String, String>();
		this.value = inner_text;
		this.uri = uri;
		if (this.uri.equals("")) this.uri = name;
		
		children = new ArrayList<>();
	}
	
	public boolean hasAttribute(String attribute) {
		return (attributes.containsKey(attribute));
	}
	
	public String getAttribute(String attribute, String default_value) {
		if (hasAttribute(attribute))
			return attributes.get(attribute);
		
		return default_value;
	}
	
	public int getAttribute(String attribute, int default_value) {
		return Integer.parseInt(getAttribute(attribute, "" + default_value));
	}
	
	public float getAttribute(String attribute, float default_value) {
		return Float.parseFloat(getAttribute(attribute, "" + default_value));
	}
	
	public String get(String attribute, String default_value) {
		return getAttribute(attribute, default_value);
	}
	
	public double get(String attribute, double default_value) {
		return Double.parseDouble(get(attribute, "" + default_value));
	}
	
	public int get(String attribute, int default_value) {
		return Integer.parseInt(get(attribute, "" + default_value));
	}
	
	public boolean get(String attribute, boolean default_value) {
		return Boolean.parseBoolean(get(attribute, "" + default_value));
	}
	
	public boolean testEquality(String attribute, String value) {
		return getAttribute(attribute, value).equals(value);
	}
	
	public void addChild(Tag t) {
		this.children.add(t);
	}
	
	public ArrayList<Tag> getChildren() {
		return this.children;
	}
	
	public Tag getChild(int index) {
		return getChildren().get(index);
	}
	
	public HashMap<String, String> getAttributes() {
		return this.attributes;
	}
	
	public String toString() {
		return "(" + uri + ")" + name + "::" + value;
	}

	public void setAttributes(NamedNodeMap namedNodeMap) {
		for (int i = 0; i < namedNodeMap.getLength(); i++) {
			attributes.put(namedNodeMap.item(i).getNodeName(), namedNodeMap.item(i).getNodeValue());
		}
	}
	
	public boolean holdsData() {
		if ((value == null || value.equals("")) && (attributes == null || attributes.size() == 0)) {
			return false;
		}
		return true;
	}
	
	public boolean hasChild() {
		return (children.size() != 0);
	}
}
