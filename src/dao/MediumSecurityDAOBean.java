package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.MediumSecurityEntity;

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
	
	public MediumSecurityEntity getUserByUsername(String username) {
		try {
			MediumSecurityEntity mediumSecurityEntity = 
					(MediumSecurityEntity) em.createNamedQuery("MediumSecurityEntity.getUserByUsername")
					.setParameter("username", username)
					.getSingleResult();
			if(!mediumSecurityEntity.equals(null)) {
				return mediumSecurityEntity;
			} 
			return null;
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			return null;
		}
	}
}
