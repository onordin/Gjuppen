package backingbeans;

import java.io.Serializable;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import dao.LowSecurityDAOBean;
import entities.LowSecurityEntity;




@Named(value="loginBean")
@SessionScoped
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790365700380643675L;
	private String name;
	private String password;
	private String otp;
	
	private Integer clientId = 30770;
	private String secretKey = "5I7U3b492h87TmEQoXe5qfInLiQ=";
	
	@EJB
	LowSecurityDAOBean lowSecurityDOABean;
	
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String login() throws YubicoVerificationException, YubicoValidationFailure {
		if (this.name.equalsIgnoreCase("conny") && this.name.equalsIgnoreCase("conny")) {
			
			YubicoClient client = YubicoClient.getClient(clientId, secretKey);
			System.out.println(otp);
			VerificationResponse response = client.verify(otp);
			System.out.println("status: " + response.isOk());
			
			if (response.isOk()) {
				
				//After validating the OTP you should make sure that the publicId part belongs to the correct user. For example:

					YubicoClient.getPublicId(otp)
					    .equals(""/* Yubikey ID associated with the user */);
				
				return "loggedOn";
			} else {
				return "";
			}
		}
		else return "";
	}
	
	// tillfällig metod som kan tas bort när det är fullmåne
	public String createRandomLowUser() {
		String[] randomNames = {"Zoila",
				"Lewis",
				"Estefana",
				"Ona",
				"Natividad",
				"Alma",
				"Sacha",
				"Mireya",
				"Loma",
				"Merle",
				"Anneliese",
				"Janetta",
				"Jacob",
				"Idalia",
				"Kathryn",
				"Cordell",
				"Jen",
				"Tobi",
				"Roxann",
				"Eugenio",
				"Sophia",
				"Laurena",
				"Rima",
				"Apolonia",
				"Laurice",
				"Gladys",
				"Antionette",
				"Thad",
				"Marsha",
				"Cherrie",
				"Randi",
				"Sunday",
				"Bunny",
				"Vivienne",
				"Rosena"}; //35 st namn
		
		Random rand = new Random();
		int locationInArray = rand.nextInt(35)+1;
		String username = randomNames[locationInArray];
		String password = "password";
		LowSecurityEntity lowSecurityUser = new LowSecurityEntity();
		lowSecurityUser.setUsername(username);
		lowSecurityUser.setPassword(password);
		lowSecurityDOABean.saveUser(lowSecurityUser);
		System.out.println("Random user "+username+" created and added to database with password 'password'.");
		return "";
	}
	
}
