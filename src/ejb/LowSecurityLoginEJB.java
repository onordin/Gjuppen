package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.LowSecurityDAOBean;
import ejb.interfaces.LocalLowLoginEJB;
import entities.LowSecurityEntity;

@Stateless
public class LowSecurityLoginEJB implements LocalLowLoginEJB{
	
	@EJB
	private LowSecurityDAOBean lowSecurityDAOBean;

	@Override
	public LowSecurityEntity login(String username, String password) {
		
		LowSecurityEntity lowSecurityEntity = lowSecurityDAOBean.getUserByUsername(username);
		if(lowSecurityEntity != null) {
			if(lowSecurityEntity.getPassword().equals(password)) {
				return lowSecurityEntity;
			}
		}
		return null;
	}

	
	
}
