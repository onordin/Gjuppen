package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ejb.interfaces.LocalRegistrationEJB;

@Named(value="registrationBean")
@RequestScoped
public class RegistrationBean implements Serializable {

	private static final long serialVersionUID = 7134740539132414943L;
	
	private String username;
	private String password;
	private String otp;
	
	@EJB
	private LocalRegistrationEJB localRegistrationEJB;
	
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


	public String register(){
		if(localRegistrationEJB.registerUser(username, password, otp)){
			System.out.println("gick bra");
			return "";
		}
		System.out.println("gick inte bra");
		return "";
	}

}
