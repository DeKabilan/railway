package com.railway.handlers.APIValidators.ParamRules;

import com.railway.utils.CustomExceptions;

public interface StationRule {
	default public void validateStation(String station) throws CustomExceptions{
		if(station.length()>3) {
			throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
		}
		
	}
}
