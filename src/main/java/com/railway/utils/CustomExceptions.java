package com.railway.utils;

public class CustomExceptions extends Exception {
	private static final long serialVersionUID = 1L;
	Exceptions exception;

	public CustomExceptions(Exceptions exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return this.exception.getMessage();
	}

	public String getCode() {
		return this.exception.getCode();
	}

	public enum Exceptions {
		OPERATION_SUCCESSFULL("OK", "The Operation was Successfull"),
		INVALID_ENDPOINT("ERR000", "The Mentioned Endpoint Doesn't Exist"),
		USER_DOESNT_EXIST("ERR001", "The Mentioned User Doesn't Exist"),
		USER_OR_PASSWORD_INCORRECT("ERR002", "Username Or Password Incorrect"),
		USER_ALREADY_EXISTS("ERR003", "User with Email Already Exists"),
		STATION_DOESNT_EXIST("ERR004", "The Mentioned Station Doesn't Exist"),
		STATION_ALREADY_EXISTS("ERR005", "The Mentioned Station Already Exists"),
		TRAIN_DOESNT_EXIST("ERR006", "The Mentioned Train Doesn't Exist"),
		TRAIN_ALREADY_EXISTS("ERR007", "The Mentioned Train Already Exists"),
		SOURCE_DOESNT_EXIST("ERR008", "The Mentioned Source Doesn't Exist"),
		SOURCE_CANT_BE_EMPTY("ERR009", "The Source Can't be Empty"),
		DESTINATION_DOESNT_EXIST("ERR010", "The Mentioned Destination Doesn't Exist"),
		DESTINATION_CANT_BE_EMPTY("ERR011", "The Destination Can't be Empty"),
		STOP_DOESNT_EXIST("ERR0012", "The Mentioned Stop Doesn't Exist"),
		PERIODICITY_IS_EMPTY("ERR0013", "The Train's Periodicity is Empty"),
		ACCESS_DENIED("ERR014", "The User Doesn't have Access Rights"),
		NO_SEATS_LEFT("ERR015", "The Train Doesn't have Enough Seats"),
		INVALID_COMPARTMENT_TYPE("ERR016", "The Mentioned Compartment Type is Invalid"),
		NAME_IS_EMPTY("ERR017", "Name Cannot be Empty"),
		INVALID_PARAMS("ERR018", "The Params are Incorrect or not Structured Properly"),
		DATE_NOT_VALID("ERR019", "The Date Format is not Valid"),
		NO_TRAIN_FOUND("ERR020", "No Train Found for the Given Source and Destination");

		String code;
		String message;

		Exceptions(String code, String message) {
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