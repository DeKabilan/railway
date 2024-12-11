package com.railway.handlers.APIValidators.ParamRules;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.railway.utils.CustomExceptions;

public interface TimeRule {
    default public void validateTime(String time) throws CustomExceptions {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new CustomExceptions(CustomExceptions.Exceptions.INVALID_PARAMS);
        }
    }
}