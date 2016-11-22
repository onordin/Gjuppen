package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.HighSecurityDAOBean;
import ejbinterfaces.LocalHighLoginEJB;

@Stateless
public class HighSecurityLoginEJB implements LocalHighLoginEJB {
	
	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;

}
