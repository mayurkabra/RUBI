package com.kabra.mayur.utility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONObject;
import org.scribe.model.Request;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.w3c.dom.Document;

public class HTTPListener {
	
	public static Document getDocument(String url) {
		try {
			Response response = getResponse(url);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			String body = response.getBody();
			InputStream stream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
			Document document = documentBuilder.parse(stream);
			document.getDocumentElement().normalize();
			return document;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	    
	}
	
	/*public static Document getDocument(String url){
		try {
			File fXmlFile = new File("C:\\Users\\Mayur\\Desktop\\test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			return doc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	private static Response getResponse(String url) {
		Request request = new Request(Verb.GET, url);
		//request.addHeader("Accept", "text/xml");
		Response response = request.send();
		return response;
	}

	public static JSONObject getJSON(String url) throws Exception {
		Response response = getResponse(url);
		JSONObject jsonObject = new JSONObject(response.getBody());
		return jsonObject;
	}

}
