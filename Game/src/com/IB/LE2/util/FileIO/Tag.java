package com.IB.LE2.util.FileIO;

import java.util.HashMap;

public class Tag {

	public final String name;
	public final String value;
	public final String uri;
	public final HashMap<String, String> attributes;
	
	public Tag(String name, String uri, String inner_text) {
		this(name, uri, inner_text, null);
	}
	
	public Tag(String name, String uri, String inner_text, HashMap<String, String> attrs) {
		this.name = name;
		this.attributes = attrs;
		this.value = inner_text;
		this.uri = uri;
	}
	
	public boolean hasAttribute(String attribute) {
		return (attributes.containsKey(attribute));
	}
	
	public String getAttribute(String attribute, String default_value) {
		if (hasAttribute(attribute))
			return attributes.get(attribute);
		
		return default_value;
	}
	
	public String get(String attribute, String default_value) {
		return getAttribute(attribute, default_value);
	}
	
	public String toString() {
		return name;
	}
	
	
}
