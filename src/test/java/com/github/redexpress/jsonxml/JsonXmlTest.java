package com.github.redexpress.jsonxml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonXmlTest {
	private static final String JSON = "{\"name\":\"Gavin Yang\",\"address\":[{\"city\":\"Shenzhen\",\"province\":\"Guangdong\"}, {\"city\":\"Zhumadian\",\"province\":\"Henan\"}],\"site\":\"http://redexpress.github.com\"}";

	@Test
	public void testFindStringFromJsonByJsonPath() {
		String str = JsonXml.findStringFromJsonByJsonPath(JSON, "$.address[0].city");
		assertEquals("Shenzhen", str);
	}

	@Test
	public void testFindStringFromJsonByXpath() throws Exception {
		String str = JsonXml.findStringFromJsonByXpath(JSON, "//address//city");
		assertEquals("Shenzhen", str);
	}

	@Test
	public void testFindStringFromXmlByXpath() throws Exception {
		String xml = JsonXml.json2xml(JSON);
		String str = JsonXml.findStringFromXmlByXpath(xml, "//address//city");
		assertEquals("Shenzhen", str);
	}

}
