package backingbeans;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import displayEntities.HighSecurityDisplayEntity;
import ejb.interfaces.LocalHighLoginEJB;

@Named(value="loginHigh")
@SessionScoped
public class LoginHighSecurityBean implements Serializable {

	private static final long serialVersionUID = 4990844464975214810L;

	private String username;
	private String password;
	private String otp;
	private Integer clientId = 30770;
	private String secretKey = "5I7U3b492h87TmEQoXe5qfInLiQ=";
	private HighSecurityDisplayEntity highSecurityDisplayEntity;
	
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
	public HighSecurityDisplayEntity getHighSecurityDisPlayEntity() {
		return highSecurityDisplayEntity;
	}

	public void setHighSecurityDisplayEntity(HighSecurityDisplayEntity highSecurityDisplayEntity) {
		this.highSecurityDisplayEntity = highSecurityDisplayEntity;
	}
	

	public String login() throws YubicoVerificationException, YubicoValidationFailure, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		HighSecurityDisplayEntity returnedEntity = highLoginEJB.login(username, password); 
		
		if (returnedEntity != null) {
			YubicoClient client = YubicoClient.getClient(clientId, secretKey);
			VerificationResponse response = client.verify(otp);
			
			if (response.isOk()) {
				
				//After validating the OTP you should make sure that the publicId part belongs to the correct user. For example:

				if(YubicoClient.getPublicId(otp)
					    .equals(returnedEntity.getYubicoId())) {
					System.out.println("HighLogin Success!");
					this.highSecurityDisplayEntity = returnedEntity;
					System.out.println("kommer det ut något här? " + this.highSecurityDisplayEntity.getUsername());
					System.out.println("kommer det ut något här? " + this.highSecurityDisplayEntity.getHashedPassword());
					System.out.println("kommer det ut något här? " + this.highSecurityDisplayEntity.getYubicoId());
					System.out.println("kommer det ut något här? " + this.highSecurityDisplayEntity.getSalt());
					return "loggedOnHighSecurity";
				} else {
					System.out.println("This Yubico key doesn't belong to this user.");
					return "";
				}
				
			} else {
				System.out.println("Response is not Ok.");
				return "";
				
			}
		} else {
			System.out.println("returnedEntity is null");
			return "";
		}
		
	}

	

	
	
	
}
