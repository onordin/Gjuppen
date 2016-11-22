package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ejbinterfaces.LocalLowLoginEJB;

@EJB
@Stateless
public class LowSecurityLoginEJB implements LocalLowLoginEJB{

}
