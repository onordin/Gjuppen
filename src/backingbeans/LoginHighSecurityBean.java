package backingbeans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="loginHigh")
@RequestScoped
public class LoginHighSecurityBean implements Serializable {

	private static final long serialVersionUID = 4990844464975214810L;

}
