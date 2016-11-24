package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalMediumLoginEJB;
import entities.MediumSecurityEntity;

@Named(value="loginMedium")
@RequestScoped
public class LoginMediumSecurityBean implements Serializable {

	private static final long serialVersionUID = -7132410149160249386L;

	private String username;
	private String password;
	private MediumSecurityDisplayEntity mediumSecurityDisplayEntity; 

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
	
	
	public MediumSecurityDisplayEntity getMediumSecurityDisplayEntity() {
		return mediumSecurityDisplayEntity;
	}

	public void setMediumSecurityDisplayEntity(MediumSecurityDisplayEntity mediumSecurityDisplayEntity) {
		this.mediumSecurityDisplayEntity = mediumSecurityDisplayEntity;
	}

	public String login() {
		MediumSecurityDisplayEntity returnedEntity = mediumLoginEJB.login(username, password);
		
		
		
		if(returnedEntity != null) {
			this.mediumSecurityDisplayEntity = returnedEntity; 
			System.out.println("all good medium logon");
			return "loggedOnMediumSecurity";
		}else {
			return "";
		}
	}
	
	
	
}
