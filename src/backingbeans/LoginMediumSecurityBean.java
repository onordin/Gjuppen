package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ejb.interfaces.LocalMediumLoginEJB;

@Named(value="loginMedium")
@RequestScoped
public class LoginMediumSecurityBean implements Serializable {

	private static final long serialVersionUID = -7132410149160249386L;

	private String username;
	private String password;

	@EJB
	private LocalMediumLoginEJB mediumLoginEJB;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
