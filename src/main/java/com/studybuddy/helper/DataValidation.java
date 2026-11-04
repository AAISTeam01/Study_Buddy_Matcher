package com.studybuddy.helper;

public class DataValidation {
	public static Boolean validatePasswords(String password,String confirmPassword) {
		if(password.equals(confirmPassword)) {  // == not use in string
			return true;
		}
		return false;
	}
	public static Boolean validateName(String name) {
		// here we have to check the first name and the last name,
		// they should not contain any numbers or any special symbols.
		if (name != null && name.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)*$")) {
		return true;
	   }
		return false;
	}
	public static Boolean validateEmail(String email) {
		// we have to check the email address for any missing domain names
		// or @ symbols and also it should not have any other special symbol other than "@" and "."
		 if (email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {

	            return true;
	        }

	        return false;
	}
}

