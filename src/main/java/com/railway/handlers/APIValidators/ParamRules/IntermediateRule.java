package com.railway.handlers.APIValidators.ParamRules;

import com.railway.utils.CustomExceptions;

public interface IntermediateRule {
    default public void validateIntermediate(String intermediate) throws CustomExceptions {
        String[] elements = intermediate.split(",");
        for (String element : elements) {
            if (element.length() != 3) {
                throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
            }
        }
    }
}

