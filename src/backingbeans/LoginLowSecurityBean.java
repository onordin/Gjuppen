package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ejb.interfaces.LocalLowLoginEJB;
import entities.LowSecurityEntity;

@Named(value="loginLow")
@RequestScoped
public class LoginLowSecurityBean implements Serializable {

	private static final long serialVersionUID = -7409229278212770001L;

	private String username;
	private String password;
	private LowSecurityEntity lowSecurityEntity;
	
	public LowSecurityEntity getLowSecurityEntity() {
		return lowSecurityEntity;
	}

	public void setLowSecurityEntity(LowSecurityEntity lowSecurityEntity) {
		this.lowSecurityEntity = lowSecurityEntity;
	}

	@EJB
	private LocalLowLoginEJB lowLoginEJB;

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
	
	public String login() {
		LowSecurityEntity returnedEntity = lowLoginEJB.login(username, password);
		
		if(returnedEntity != null) {
			this.lowSecurityEntity = returnedEntity;
			return "loggedOnLowSecurity";
		}else {
			return "";
		}
	}
	
	
	
}
