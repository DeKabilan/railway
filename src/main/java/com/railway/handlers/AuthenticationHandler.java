package com.railway.handlers;

import com.railway.dao.UserDAO;
import com.railway.decorator.PasswordDecorator;
import com.railway.model.User;

public class AuthenticationHandler {
	UserDAO userdao = new UserDAO();
	PasswordDecorator hasher = new PasswordDecorator();

	public Boolean validateUser(String loginemail, String pass) {
		String loginpass = Integer.toString(hasher.hash(pass));
		User userFromDB = userdao.getUser(loginemail);
		if (userFromDB.getPassword().equals(loginpass) && userFromDB.getRole().equals("user")) {
			return true;
		}
		return false;
	}

	public Boolean validateAdmin(String loginemail, String pass) {
		String loginpass = Integer.toString(hasher.hash(pass));
		User userFromDB = userdao.getUser(loginemail);
		if (userFromDB.getPassword().equals(loginpass) && userFromDB.getRole().equals("admin")) {
			return true;
		}
		return false;
	}

	public Boolean registerUser(String email, String pass) {
		if (userdao.isUserExist(email)) {
			return false;
		} else {
			userdao.createUser(email, Integer.toString(hasher.hash(pass)));
			return true;
		}

	}

}
