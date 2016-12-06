package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import dao.LowSecurityDAOBean;
import displayEntities.LowSecurityDisplayEntity;
import ejb.interfaces.LocalLowLoginEJB;
import entities.LowSecurityEntity;

/**
 * Business logic for low security login.
 */

@Stateless
public class LowSecurityLoginEJB implements LocalLowLoginEJB{
	
	@EJB
	private LowSecurityDAOBean lowSecurityDAOBean;

	@Override
	public LowSecurityDisplayEntity login(String username, String password) {
		
		LowSecurityDisplayEntity entityToReturn;
		LowSecurityEntity lowSecurityEntity = lowSecurityDAOBean.getUserByUsername(username);
		
		if(lowSecurityEntity != null) {
			if(lowSecurityEntity.getPassword().equals(password)) {
				entityToReturn = new LowSecurityDisplayEntity();
				entityToReturn.setUsername(lowSecurityEntity.getUsername());
				entityToReturn.setPassword(lowSecurityEntity.getPassword());
				return entityToReturn;
			}
		}
		return null;
	}

	
	
}
