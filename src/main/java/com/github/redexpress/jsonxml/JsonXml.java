package com.github.redexpress.jsonxml;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.jayway.jsonpath.JsonPath;

public class JsonXml {
	
	public static void main(String[] args){
		
	}

	public static String xml2json(String xml) {
		return new XMLSerializer().read(xml).toString();
	}

	public static String json2xml(String json) {
		JSONObject jobj = JSONObject.fromObject(json);
		return new XMLSerializer().write(jobj);
	}
	
	public static String findStringFromJsonByJsonPath(String json, String jsonPath){
		Object object = JsonPath.read(json, jsonPath);
		return object.toString();
	}
	
	public static String findStringFromJsonByXpath(String json, String xpath){
		String xml = JsonXml.json2xml(json);
		return xml;
	}
	
	public static String findStringFromXmlByXpath(String xml, String xpath) {
		return xml;
	}
}
