package dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.HighSecurityEntity;
import entities.LowSecurityEntity;

@Stateless
public class HighSecurityDAOBean {

	@PersistenceContext
	private EntityManager em;

	public void saveUser(HighSecurityEntity highSecurityEntity) {
		try {
			em.merge(highSecurityEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HighSecurityEntity getUserByUsername(String username) {
		try {
			HighSecurityEntity highSecurityEntity = (HighSecurityEntity) em
					.createNamedQuery("HighSecurityEntity.getUserByUsername").setParameter("username", username)
					.getSingleResult();
			if (highSecurityEntity != null) {
				return highSecurityEntity;
			}
			return null;
		} catch (NoResultException nre) {
			// nre.printStackTrace();
			return null;
		}
	}

	public HighSecurityEntity getUserByYubicoId(String yubicoId) {
		try {
			HighSecurityEntity highSecurityEntity = (HighSecurityEntity) em.createNamedQuery("HighSecurityEntity.getUserByYubicoId")
					.setParameter("yubico", yubicoId).getSingleResult();
			if (highSecurityEntity != null) {
				return highSecurityEntity;
			}
			return null;
		} catch (NoResultException nre) {
			return null;
		}
	}

	public void deleteAllUsers() {
		try {
			TypedQuery<HighSecurityEntity> query = (TypedQuery<HighSecurityEntity>) em.createNamedQuery("HighSecurityEntity.findAll");
		
			List<HighSecurityEntity> allUsers = query.getResultList();
			for(HighSecurityEntity low : allUsers) {
				em.remove(low);
			}
		} catch (NoResultException nre) {
			//nre.printStackTrace();
			
		}
		
	}
}
