package com.railway.handlers.APIValidators;

import org.json.simple.JSONObject;

import com.railway.handlers.APIValidators.ParamRules.DataTypeRule;
import com.railway.handlers.APIValidators.ParamRules.StationRule;
import com.railway.utils.CustomExceptions;

public class StationEndpointValidator implements EndpointValidator,DataTypeRule,StationRule {
	public void validate(JSONObject json) throws CustomExceptions{
		if(json.get("name")!=null) {
			validateDataType(json.get("name"), String.class);
		}
		if(json.get("code")!=null) {
			validateStation((String)json.get("code"));
		}
		if(json.get("oldcode")!=null) {
			validateStation((String)json.get("oldcode"));
		}
	}
}

