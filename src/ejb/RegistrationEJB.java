package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.HighSecurityDAOBean;
import dao.LowSecurityDAOBean;
import dao.MediumSecurityDAOBean;
import ejbinterfaces.LocalRegistrationEJB;

@Stateless
public class RegistrationEJB implements LocalRegistrationEJB {
	
	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;
	
	@EJB
	private MediumSecurityDAOBean mediumSecurityDAOBean;
	
	@EJB
	private LowSecurityDAOBean lowSecurityDAOBean;

}
