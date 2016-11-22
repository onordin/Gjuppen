package backingbeans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="loginMedium")
@RequestScoped
public class LoginMediumSecurityBean implements Serializable {

	private static final long serialVersionUID = -7132410149160249386L;

}
