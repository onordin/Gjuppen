package ejb.interfaces;

import javax.ejb.Local;

@Local
public interface LocalRegistrationEJB {

	boolean registerUser(String username, String password, String otp);

	void deleteAllUsers();
	
	String checkPasswordStrength(String password);

}
