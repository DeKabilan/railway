package com.railway.handlers.APIValidators;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.railway.handlers.APIValidators.ParamRules.DataTypeRule;
import com.railway.handlers.APIValidators.ParamRules.DateRule;
import com.railway.handlers.APIValidators.ParamRules.IntermediateRule;
import com.railway.handlers.APIValidators.ParamRules.PeriodicityRule;
import com.railway.handlers.APIValidators.ParamRules.ValuesRule;
import com.railway.handlers.APIValidators.ParamRules.StationRule;
import com.railway.handlers.APIValidators.ParamRules.TimeRule;
import com.railway.utils.CustomExceptions;

public class TrainEndpointValidator implements EndpointValidator,DataTypeRule,DateRule,StationRule,
						IntermediateRule,PeriodicityRule,ValuesRule,TimeRule {
	public void validate(JSONObject json) throws CustomExceptions{
		if(json.get("name")!=null) {
			validateDataType(json.get("name"), String.class);
		}
		if(json.get("oldname")!=null) {
			validateDataType(json.get("oldname"), String.class);
		}
		if(json.get("seatalgo")!=null) {
			validateValues((String)(json.get("seatalgo")),new String[]{"SCATTERRED","ORDERED","EVEN_FIRST","ODD_FIRST"});
		}
		if(json.get("source")!=null) {
			validateStation((String)json.get("source"));
		}
		if(json.get("destination")!=null) {
			validateStation((String)json.get("destination"));
		}
		if(json.get("departure")!=null) {
			validateTime((String)json.get("departure"));
		}
		if(json.get("arrival")!=null) {
			validateTime((String)json.get("arrival"));
		}
		if(json.get("periodicity")!=null) {
			validatePeriodicity((ArrayList<String>)json.get("periodicity"));
		}
		if(json.get("intermediate")!=null) {
			validateIntermediate((String)json.get("intermediate"));
		}
		if(json.get("acno")!=null) {
			validateDataType(json.get("acno"), Long.class);
		}
		if(json.get("acseats")!=null) {
			validateDataType(json.get("acseats"), Long.class);
		}
		if(json.get("accost")!=null) {
			validateDataType(json.get("accost"), Long.class);
		}
		if(json.get("nonacno")!=null) {
			validateDataType(json.get("nonacno"), Long.class);
		}
		if(json.get("nonacseats")!=null) {
			validateDataType(json.get("nonacseats"), Long.class);
		}
		if(json.get("nonaccost")!=null) {
			validateDataType(json.get("nonaccost"), Long.class);
		}
		
	}
}

