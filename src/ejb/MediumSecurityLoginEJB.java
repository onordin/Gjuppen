package ejb;

import javax.ejb.Stateless;

import dao.MediumSecurityDAOBean;
import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalMediumLoginEJB;
import ejb.passwordencryption.MD5;
import entities.MediumSecurityEntity;

import javax.ejb.EJB;

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

}
