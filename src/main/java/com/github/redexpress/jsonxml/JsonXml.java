package com.github.redexpress.jsonxml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
	
	public static String findStringFromJsonFileByJsonPath(String jsonFile, String jsonPath) throws IOException{
		String json = fileToString(new File(jsonFile));
		Object object = JsonPath.read(json, jsonPath);
		return object.toString();
	}
	
	public static String findStringFromJsonByXpath(String json, String xpath) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		return findStringFromJsonByXpath(json, "UTF-8", xpath);
	}
	
	public static String findStringFromJsonByXpath(String json, String encoding, String xpath) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		String xml = JsonXml.json2xml(json);
		InputStream in =  new ByteArrayInputStream(xml.getBytes(encoding)); 
    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(in);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        in.close();
        Object o = xPath.evaluate(xpath, doc, XPathConstants.STRING);
        return o.toString();
	}
	
	public static String findStringFromJsonFileByXpath(String jsonFile, String xpath) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		return findStringFromJsonFileByXpath(jsonFile, "UTF-8", xpath);
	}
	
	private static String findStringFromJsonFileByXpath(String jsonFile, String encoding, String xpath) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
    	File file = new File(jsonFile);
    	String json = fileToString(file);
		String xml = JsonXml.json2xml(json);
		InputStream in = new ByteArrayInputStream(xml.getBytes(encoding)); 
    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(in);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        in.close();
        Object o = xPath.evaluate(xpath, doc, XPathConstants.STRING);
        return o.toString();
	}
	
	public static String findStringFromXmlByXpath(String xml, String xpath) throws Exception{
		return findStringFromXmlByXpath(xml, "UTF-8", xpath);
	}
	
	private static String findStringFromXmlByXpath(String xml, String encoding, String xpath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		InputStream in =  new ByteArrayInputStream(xml.getBytes(encoding)); 
    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(in);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        in.close();
        Object o = xPath.evaluate(xpath, doc, XPathConstants.STRING);
        return o.toString();
	}
	
	public static String findStringFromXmlFileByXpath(String xmlFile, String xpath) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		FileInputStream in = new FileInputStream(new File(xmlFile)); 
    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();//ParserConfigurationException
        Document doc = builder.parse(in);//SAXException
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        in.close();
        Object o = xPath.evaluate(xpath, doc, XPathConstants.STRING);
        return o.toString();
	}
	
	private static String fileToString(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		in.close();
		is.close();
		return buffer.toString();
	}
}
