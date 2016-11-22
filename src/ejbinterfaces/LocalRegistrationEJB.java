package ejbinterfaces;

import javax.ejb.Local;

@Local
public interface LocalRegistrationEJB {

	boolean registerUser(String username, String password, String otp);

}
