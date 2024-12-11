package com.railway.handlers.APIValidators.ParamRules;

import java.util.ArrayList;

import com.railway.utils.CustomExceptions;

public interface PeriodicityRule extends ValuesRule{
    default public void validatePeriodicity(ArrayList<String> periodicity) throws CustomExceptions {
        for (String element : periodicity) {
            if (element.length() != 3) {
            	validateValues(element,new String[]{"MON","TUE","WED","THU","FRI","SAT","SUN"});
                throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
            }
        }
    }
}
