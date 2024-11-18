package com.railway.utils;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RequestBodyExtracter {
	JSONParser parser = new JSONParser();
	public JSONObject extract(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try{
			StringBuilder jsonBody = new StringBuilder();
	        try (BufferedReader reader = request.getReader()) {
	            String line;
				while ((line = reader.readLine()) != null) {
	                jsonBody.append(line);
	            }
	        }
	        json = (JSONObject)parser.parse(jsonBody.toString());
		}
		catch(Exception e) {
			
		}
		return json;
	}
}
