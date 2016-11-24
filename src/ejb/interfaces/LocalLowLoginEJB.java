package ejb.interfaces;

import javax.ejb.Local;

import displayEntities.LowSecurityDisplayEntity;
import entities.LowSecurityEntity;

@Local
public interface LocalLowLoginEJB {

	LowSecurityDisplayEntity login(String username, String password);

}
