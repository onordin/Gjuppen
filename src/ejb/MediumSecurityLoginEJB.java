package ejb;

import javax.ejb.Stateless;

import dao.MediumSecurityDAOBean;
import ejbinterfaces.LocalMediumLoginEJB;

import javax.ejb.EJB;

@Stateless
public class MediumSecurityLoginEJB implements LocalMediumLoginEJB {
	
	@EJB
	private MediumSecurityDAOBean mediumSecurityDAOBean;

}
