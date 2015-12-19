package com.github.redexpress.jsonxml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

public class JsonXmlTest {
	private static final String JSON = "{\"name\":\"Gavin Yang\",\"address\":[{\"city\":\"Shenzhen\",\"province\":\"Guangdong\"}, {\"city\":\"Zhumadian\",\"province\":\"Henan\"}],\"site\":\"http://redexpress.github.com\"}";
	
	@Test
	public void testXml2json() {
		fail("Not yet implemented");
	}

	@Test
	public void testJson2xml() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindStringFromJsonByJsonPath() {
		String str = JsonXml.findStringFromJsonByJsonPath(JSON, "$.address[0].city");
		assertEquals("Shenzhen", str);
	}

	@Test
	public void testFindStringFromJsonByXpath() {
		String str = JsonXml.findStringFromJsonByXpath(JSON, "/address[1]/city");
		assertEquals("Shenzhen", str);
	}

	@Test
	public void testFindStringFromXmlByXpath() {
		String xml = JsonXml.json2xml(JSON);
		String str = JsonXml.findStringFromXmlByXpath(xml, "/address[1]/city");
		assertEquals("Shenzhen", str);
	}

}
