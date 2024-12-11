package com.railway.handlers.APIValidators.ParamRules;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.railway.utils.CustomExceptions;

public interface PassengersRule {
	default public void validatePassengers(ArrayList<JSONObject> passengers) throws CustomExceptions{
		if(passengers.size()<=0 && passengers.size()>5) {
			throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
		}
		for(JSONObject passenger : passengers) {
			if(passenger.get("name")==null || ((String)passenger.get("name")).isEmpty()) {
				throw new CustomExceptions(CustomExceptions.Exceptions.NAME_IS_EMPTY);
			}
			if(passenger.get("age")==null || Integer.parseInt((String)passenger.get("age"))>100) {
				throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
			}
		}
		
	}
}
