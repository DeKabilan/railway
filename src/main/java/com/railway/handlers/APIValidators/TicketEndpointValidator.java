package com.railway.handlers.APIValidators;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.railway.handlers.APIValidators.ParamRules.DataTypeRule;
import com.railway.handlers.APIValidators.ParamRules.DateRule;
import com.railway.handlers.APIValidators.ParamRules.PassengersRule;
import com.railway.handlers.APIValidators.ParamRules.StationRule;
import com.railway.handlers.APIValidators.ParamRules.ValuesRule;
import com.railway.utils.CustomExceptions;

public class TicketEndpointValidator implements EndpointValidator,DataTypeRule,DateRule,StationRule,ValuesRule,PassengersRule {
	public void validate(JSONObject json) throws CustomExceptions{
		if(json.get("train")!=null) {
			validateDataType(json.get("train"), String.class);
		}
		if(json.get("source")!=null) {
			validateStation((String)json.get("source"));
		}
		if(json.get("destination")!=null) {
			validateStation((String)json.get("destination"));
		}
		if(json.get("type")!=null) {
			validateValues((String)json.get("type"),new String[] {"AC","NONAC"});
		}
		if(json.get("date")!=null) {
			validateDate((String)json.get("date"));
		}
		if(json.get("passengers")!=null) {
			validatePassengers((ArrayList<JSONObject>)json.get("passengers"));
		}
	}
}

