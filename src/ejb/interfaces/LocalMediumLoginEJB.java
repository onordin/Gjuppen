package ejb.interfaces;

import javax.ejb.Local;

import displayEntities.MediumSecurityDisplayEntity;

@Local
public interface LocalMediumLoginEJB {

	MediumSecurityDisplayEntity login(String username, String password);

}
