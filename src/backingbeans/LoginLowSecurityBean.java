package backingbeans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="loginLow")
@RequestScoped
public class LoginLowSecurityBean implements Serializable {

	private static final long serialVersionUID = -7409229278212770001L;

}
