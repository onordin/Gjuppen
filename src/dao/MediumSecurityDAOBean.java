package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.LowSecurityEntity;
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
			if(mediumSecurityEntity != null) {
				return mediumSecurityEntity;
			} 
			return null;
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			return null;
		}
	}
	
	public void deleteAllUsers() {
		try {
			TypedQuery<MediumSecurityEntity> query = (TypedQuery<MediumSecurityEntity>) em.createNamedQuery("MediumSecurityEntity.findAll");
		
			List<MediumSecurityEntity> allUsers = query.getResultList();
			for(MediumSecurityEntity low : allUsers) {
				em.remove(low);
			}
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			
		}
		
	}
}
