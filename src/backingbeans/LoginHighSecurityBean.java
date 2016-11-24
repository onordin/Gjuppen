package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ejb.interfaces.LocalHighLoginEJB;

@Named(value="loginHigh")
@RequestScoped
public class LoginHighSecurityBean implements Serializable {

	private static final long serialVersionUID = 4990844464975214810L;

	private String username;
	private String password;
	private String otp;
	
	@EJB
	private LocalHighLoginEJB highLoginEJB;

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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
}
