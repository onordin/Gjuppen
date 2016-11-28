package ejb;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.HighSecurityDAOBean;
import displayEntities.HighSecurityDisplayEntity;
import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalHighLoginEJB;
import ejb.passwordencryption.MD5;
import ejb.passwordencryption.PBKDF2;
import entities.HighSecurityEntity;
import entities.MediumSecurityEntity;

@Stateless
public class HighSecurityLoginEJB implements LocalHighLoginEJB {
	
	
	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;

	@Override
	public HighSecurityDisplayEntity login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		HighSecurityDisplayEntity entityToReturn;
		HighSecurityEntity highSecurityEntity = highSecurityDAOBean.getUserByUsername(username);
		if(highSecurityEntity != null) {
			if(PBKDF2.validatePassword(password, highSecurityEntity.getPassword(), highSecurityEntity.getSalt())) {
				entityToReturn = new HighSecurityDisplayEntity();
				entityToReturn.setUsername(highSecurityEntity.getUsername());
				entityToReturn.setHashedPassword(highSecurityEntity.getPassword());
				entityToReturn.setSalt(highSecurityEntity.getSalt());
				entityToReturn.setYubicoId(highSecurityEntity.getYubico());
				return entityToReturn;
			} 
		}
		return null;
	}




}

