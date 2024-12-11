package com.railway.handlers.APIValidators.ParamRules;

import com.railway.utils.CustomExceptions;

public interface DataTypeRule {
    default public void validateDataType(Object input, Class<?> type) throws CustomExceptions {
        if (!type.isInstance(input)) {
            throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
        }
    }
}