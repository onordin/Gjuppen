package backingbeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalMediumLoginEJB;

@Named(value="loginMedium")
@SessionScoped
public class LoginMediumSecurityBean implements Serializable {

	private static final long serialVersionUID = -7132410149160249386L;

	private String username;
	private String password;
	private MediumSecurityDisplayEntity mediumSecurityDisplayEntity; 
	private String reversedHash;

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

	
	public String getReversedHash() {
		return reversedHash;
	}

	public void setReversedHash(String reversedHash) {
		this.reversedHash = reversedHash;
	}

	public String login() {
		mediumSecurityDisplayEntity = null;
		reversedHash = "";
		MediumSecurityDisplayEntity returnedEntity = mediumLoginEJB.login(username, password);
		if(returnedEntity != null) {
			this.mediumSecurityDisplayEntity = returnedEntity; 
			this.username = "";
			this.password = "";
			System.out.println("Returning displayentitiy: " + this.mediumSecurityDisplayEntity.toString());
			return "loggedOnMediumSecurity";
		}else {
			return "";
		}
	}
	
	
	public String unhash(String hashedPassword) {
		System.out.println("hash still in bean: " + mediumSecurityDisplayEntity.getHashedPassword());
		System.out.println("hashedPassword: " + hashedPassword);
		String resultReversedHash = mediumLoginEJB.reverseHash(hashedPassword);
		if(resultReversedHash != null && resultReversedHash != "" && reversedHash.length() != 0) {		 
			//vid failed de-cryption så verkar den returnera en sträng som inte är "" men length är 0 vilket åtminstone gör att den hamnar i "else" delen av metoden
			this.reversedHash = resultReversedHash;
		}else {
			this.reversedHash = "Congratulations, your password was strong/unique enough to resist MD5 decryption (for now)";
		}
		return "";
	}
	
}
