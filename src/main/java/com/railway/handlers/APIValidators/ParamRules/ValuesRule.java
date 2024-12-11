package com.railway.handlers.APIValidators.ParamRules;

import com.railway.utils.CustomExceptions;

public interface ValuesRule {
	default public void validateValues(String pattern, String[] inputs) throws CustomExceptions{
		for(String input: inputs) {
			if(input.equalsIgnoreCase(pattern)) {
				return;
			}
		}
		throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
	}
}
