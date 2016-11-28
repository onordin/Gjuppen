package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.LowSecurityEntity;

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

	public void deleteAllUsers() {
		try {
			TypedQuery<LowSecurityEntity> query = (TypedQuery<LowSecurityEntity>) em.createNamedQuery("LowSecurityEntity.findAll");
		
			List<LowSecurityEntity> allUsers = query.getResultList();
			for(LowSecurityEntity low : allUsers) {
				em.remove(low);
			}
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			
		}
		
	}
	
	

}
