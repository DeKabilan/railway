package com.railway.handlers.APIValidators.ParamRules;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.railway.utils.CustomExceptions;

public interface DateRule {
	default public void validateDate(String dateStr) throws CustomExceptions{
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        try {
	            LocalDate.parse(dateStr, formatter);
	        } 
	        catch (DateTimeParseException e) {
	        	throw new CustomExceptions(CustomExceptions.Exceptions.DATE_NOT_VALID);
	        }
		}
}
