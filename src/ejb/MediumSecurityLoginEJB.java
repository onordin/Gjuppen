package ejb;

import javax.ejb.Stateless;

import dao.MediumSecurityDAOBean;
import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalMediumLoginEJB;
import ejb.passwordencryption.MD5;
import entities.MediumSecurityEntity;
import helpers.MD5_API_Reader;

import javax.ejb.EJB;

/**
 * Business logic for medium security login.
 */

@Stateless
public class MediumSecurityLoginEJB implements LocalMediumLoginEJB {
	
	@EJB
	private MediumSecurityDAOBean mediumSecurityDAOBean;

	@Override
	public MediumSecurityDisplayEntity login(String username, String password) {
		MediumSecurityDisplayEntity entityToReturn;
		MediumSecurityEntity mediumSecurityEntity = mediumSecurityDAOBean.getUserByUsername(username);
		
		if(mediumSecurityEntity != null) {
			String enteredPasswordWithHash = MD5.getMD5(password);
			if(mediumSecurityEntity.getPassword().equals(enteredPasswordWithHash)) {
				entityToReturn = new MediumSecurityDisplayEntity();
				entityToReturn.setUsername(mediumSecurityEntity.getUsername());
				entityToReturn.setHashedPassword(mediumSecurityEntity.getPassword());
				return entityToReturn;
			}
		}
		return null;
	}

	/**
	 * Method that unhashes the MD5-password stored in the database using MD5_API-Reader.java file.
	 */
	@Override
	public String reverseHash(String hashedPassword) {
		String returnValue = "";
		System.out.println("inside EJB to reverse: " + hashedPassword);
		try {
			returnValue = MD5_API_Reader.getResult(hashedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("returning: " + returnValue);
		return returnValue;
	}

}
