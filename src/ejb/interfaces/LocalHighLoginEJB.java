package ejb.interfaces;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.Local;

import displayEntities.HighSecurityDisplayEntity;

@Local
public interface LocalHighLoginEJB {

	HighSecurityDisplayEntity login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException;


}
