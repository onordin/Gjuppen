package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.LowSecurityDAOBean;
import ejbinterfaces.LocalLowLoginEJB;

@Stateless
public class LowSecurityLoginEJB implements LocalLowLoginEJB{
	
	@EJB
	private LowSecurityDAOBean lowSecurityDAOBean;

}
