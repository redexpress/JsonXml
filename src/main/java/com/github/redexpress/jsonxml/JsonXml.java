package com.github.redexpress.jsonxml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.jayway.jsonpath.JsonPath;

public class JsonXml {
	private static boolean isXml;
	private static boolean isXPath;
	private static boolean isFile;
	
	public static void main(String[] a) throws Exception{
		String[] args = new String[] {};
		Options options = new Options();
		String help = "JsonXml [options] <input> <query>\n"
				+ "input        JSON string or XML string or filename (with option -f)\n"
				+ "query        JSONPath string or XPath string (with option -p)\n"
				+ "where possible options include:";
		options.addOption("x", false, "input is XML format, default is JSON");
		options.addOption("p", false, "query is XPath, default is JSONPath");
		options.addOption("f", false, "load input from file, default regard as string");
		options.addOption("i", "info", false, "print debug information");
		options.addOption("h", "help", false, "print help information");

		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println("JsonXml: error: paramters parse error");
			formatter.printHelp(help, options);
			System.exit(1);
		}
		List<String> argsList = cmd.getArgList();
		if (argsList.size() < 2){
			System.out.println("JsonXml: error: missing paramters");
			formatter.printHelp(help, options);
			System.exit(1);
		}
		String input = argsList.get(0);
		String query = argsList.get(1);
		isXml = cmd.hasOption('h');
		isFile = cmd.hasOption('f');
		isXPath = cmd.hasOption('p');
		String result = "";
		if (isXml) {
			if (isFile) {
				result = findStringFromXmlFileByXpath(input, query);
			} else {
				result = findStringFromXmlByXpath(input, query);
			}
		} else {
			if (isXPath) {
				if (isFile) {
					result = findStringFromJsonFileByXpath(input, query);
				} else {
					result = findStringFromJsonByXpath(input, query);
				}
			} else {
				if (isFile) {
					result = findStringFromJsonFileByJsonPath(input, query);
				} else {
					result = findStringFromJsonByJsonPath(input, query);
				}
			}
		}
		System.out.println(result);
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
