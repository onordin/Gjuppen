package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.HighSecurityDAOBean;
import ejb.interfaces.LocalHighLoginEJB;
import entities.HighSecurityEntity;

@Stateless
public class HighSecurityLoginEJB implements LocalHighLoginEJB {
	
	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;

	
}

