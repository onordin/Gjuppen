package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.MediumSecurityEntity;
import exceptions.DataNotFoundException;

@Stateless
public class MediumSecurityDAOBean {
	
	@PersistenceContext
	private EntityManager em;

	public void saveUser(MediumSecurityEntity mediumSecurityEntity){
		try {
			em.merge(mediumSecurityEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MediumSecurityEntity getUserByUsername(String username) throws DataNotFoundException {
		try {
			return (MediumSecurityEntity) em.createNamedQuery("MediumSecurityEntity.getUserByUsername")
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
}
