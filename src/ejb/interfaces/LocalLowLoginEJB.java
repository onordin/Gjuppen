package ejb.interfaces;

import javax.ejb.Local;

import entities.LowSecurityEntity;

@Local
public interface LocalLowLoginEJB {

	LowSecurityEntity login(String username, String password);

}
