package com.railway.decorator;

import org.json.simple.JSONObject;

import com.railway.utils.CustomExceptions;

public class ErrorDecorator {
	public JSONObject decorate(CustomExceptions.Exceptions exception) {
		JSONObject json = new JSONObject();
		
			json.put("response",exception.getCode());
			json.put("message",exception.getMessage());
		
		return json;
		
	}
}
