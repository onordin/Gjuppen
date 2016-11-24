package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entities.HighSecurityEntity;
import exceptions.DataNotFoundException;

@Stateless
public class HighSecurityDAOBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public void saveUser(HighSecurityEntity highSecurityEntity){
		try {
			em.merge(highSecurityEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HighSecurityEntity getUserByUsername(String username) throws DataNotFoundException {
		try {
			return (HighSecurityEntity) em.createNamedQuery("HighSecurityEntity.getUserByUsername")
					.setParameter("username", username)
					.getSingleResult();
		} catch (NoResultException nre) {
				return null;
		}
	}
	
	public HighSecurityEntity getUserByYubicoId(String yubicoId) throws DataNotFoundException {
		try {
			return (HighSecurityEntity) em.createNamedQuery("HighSecurityEntity.getUserByYubicoId").setParameter("yubico", yubicoId).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
