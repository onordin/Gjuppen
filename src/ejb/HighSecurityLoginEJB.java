package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ejbinterfaces.LocalHighLoginEJB;

@EJB
@Stateless
public class HighSecurityLoginEJB implements LocalHighLoginEJB {

}
