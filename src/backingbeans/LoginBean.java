package backingbeans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;




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
	
}
