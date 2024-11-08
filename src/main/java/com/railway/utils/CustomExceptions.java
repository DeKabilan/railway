package com.railway.utils;

public class CustomExceptions extends Exception {
    private static final long serialVersionUID = 1L;
	Exceptions exception;

    public CustomExceptions(Exceptions exception){
        this.exception = exception;
    }
    
    public String getMessage() {
    	return this.exception.getMessage();
    }
    public String getCode() {
        return this.exception.getCode();
    }
    
    public enum Exceptions{
    	
        USER_DOESNT_EXIST("ERR001","The Mentioned User Doesn't Exist"),
        USER_OR_PASSWORD_INCORRECT("ERR002","Username Or Password Incorrect"),
        USER_ALREADY_EXISTS("ERR003","User with Email Already Exists"),
        STATION_DOESNT_EXIST("ERR004","The Mentioned Station Doesn't Exist"),
        STATION_ALREADY_EXISTS("ERR004","The Mentioned Station Already Exists"),
        TRAIN_DOESNT_EXIST("ERR005","The Mentioned Train Doesn't Exist"),
    	TRAIN_ALREADY_EXISTS("ERR006","The Mentioned Train Doesn't Exist"),
    	SOURCE_DOESNT_EXIST("ERR005","The Mentioned Source Doesn't Exist"),
    	DESTINATION_DOESNT_EXIST("ERR005","The Mentioned Destination Doesn't Exist"),
    	INVALID_TIME_FORMAT("ERR005","The Provided Time Format is Invalid, Use HHmm"),;
    	
    	
    	
    	
    	String code;
    	String message;
    	
        Exceptions(String code,String message){
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }
        public String getMessage() {
            return message;
        }
    }
    public Exceptions getException() {
        return exception;
    }
}