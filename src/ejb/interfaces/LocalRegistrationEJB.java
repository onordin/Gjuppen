package ejb.interfaces;

import javax.ejb.Local;

@Local
public interface LocalRegistrationEJB {

	boolean registerUser(String username, String password, String otp);
	
	String checkPasswordStrength(String password);

}
