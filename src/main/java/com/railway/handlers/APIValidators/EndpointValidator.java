package com.railway.handlers.APIValidators;

import org.json.simple.JSONObject;

import com.railway.utils.CustomExceptions;

public interface EndpointValidator {
	public void validate(JSONObject json) throws CustomExceptions;
}
