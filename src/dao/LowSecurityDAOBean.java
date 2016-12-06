package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.LowSecurityEntity;

/**
 * Class LowSecurityDAOBean talks to database table low.
 */

@Stateless
public class LowSecurityDAOBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public void saveUser(LowSecurityEntity lowSecurityEntity){
		try {
			em.merge(lowSecurityEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LowSecurityEntity getUserByUsername(String username) {
		try {
			LowSecurityEntity lowSecurityEntity = 
					(LowSecurityEntity) em.createNamedQuery("LowSecurityEntity.getUserByUsername")
					.setParameter("username", username)
					.getSingleResult();
			if(lowSecurityEntity != null) {
				return lowSecurityEntity;
			} 
			return null;
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			return null;
		}
	}

}
