package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.LowSecurityEntity;
import exceptions.DataNotFoundException;

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
	
	public LowSecurityEntity getUserByUsername(String username) throws DataNotFoundException {
		try {
			return (LowSecurityEntity) em.createNamedQuery("LowSecurityEntity.getUserByUsername")
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException nre) {
			nre.printStackTrace();
			return null;
		}
	}

}
