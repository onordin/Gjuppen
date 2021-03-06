package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import entities.HighSecurityEntity;

/**
 * Class HighSecurityDAOBean talks to database table high.
 */

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

}
