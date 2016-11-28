package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import displayEntities.LowSecurityDisplayEntity;
import ejb.interfaces.LocalLowLoginEJB;
import entities.LowSecurityEntity;
import messageservice.MessageService;

@Named(value="loginLow")
@RequestScoped
public class LoginLowSecurityBean implements Serializable {

	private static final long serialVersionUID = -7409229278212770001L;

	private String username;
	private String password;
	private LowSecurityDisplayEntity lowSecurityDisplayEntity;
	private String firstCharPassword;
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

	public String getFirstCharPassword() {
		return firstCharPassword;
	}

	public void setFirstCharPassword(String firstCharPassword) {
		this.firstCharPassword = firstCharPassword;
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
		LowSecurityDisplayEntity returnedEntity = lowLoginEJB.login(username, password);
		
		if(returnedEntity != null) {
			this.lowSecurityDisplayEntity = returnedEntity;
			this.username = "";
			this.password = "";
			firstCharPassword = returnedEntity.getPassword().substring(0, 1); 
			return "loggedOnLowSecurity";
		}else {
			messageService.errorMsg("login1", "Wrong username and/or password");
			return "";
		}
	}
	
	
	
}
