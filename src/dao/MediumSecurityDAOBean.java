package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MediumSecurityDAOBean {
	
	@PersistenceContext
	private EntityManager em;

}
