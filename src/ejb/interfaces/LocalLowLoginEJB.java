package ejb.interfaces;

import javax.ejb.Local;

import displayEntities.LowSecurityDisplayEntity;

@Local
public interface LocalLowLoginEJB {

	LowSecurityDisplayEntity login(String username, String password);

}
