package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import displayEntities.LowSecurityDisplayEntity;
import ejb.interfaces.LocalLowLoginEJB;
import entities.LowSecurityEntity;
import messageservice.MessageService;

/**
 * Presentation layer for the low-login function.
 */

@Named(value="loginLow")
@SessionScoped
public class LoginLowSecurityBean implements Serializable {

	private static final long serialVersionUID = -7409229278212770001L;

	private String username;
	private String password;
	private LowSecurityDisplayEntity lowSecurityDisplayEntity;
	private MessageService messageService;

	@EJB
	private LocalLowLoginEJB lowLoginEJB;
	

	public LoginLowSecurityBean() {
		messageService = new MessageService();
	}
	
	
	public LowSecurityDisplayEntity getLowSecurityDisplayEntity() {
		return lowSecurityDisplayEntity;
	}

	public void setLowSecurityDisplayEntity(LowSecurityDisplayEntity lowSecurityDisplayEntity) {
		this.lowSecurityDisplayEntity = lowSecurityDisplayEntity;
	}

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
		if(username.trim().isEmpty() || password.trim().isEmpty()) {
			messageService.errorMsg("login1", "Username and password required");
			return "";
		}
		LowSecurityDisplayEntity returnedEntity = lowLoginEJB.login(username, password);
		if(returnedEntity != null) {
			this.lowSecurityDisplayEntity = returnedEntity;
			this.username = "";
			this.password = "";
			return "loggedOnLowSecurity";
		}else {
			messageService.errorMsg("login1", "Wrong username and/or password");
			return "";
		}
	}
	
	
	
}
