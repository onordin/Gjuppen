package ejb;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import dao.HighSecurityDAOBean;
import dao.LowSecurityDAOBean;
import dao.MediumSecurityDAOBean;
import ejb.interfaces.LocalRegistrationEJB;
import ejb.passwordencryption.MD5;
import ejb.passwordencryption.PBKDF2;
import entities.HighSecurityEntity;
import entities.LowSecurityEntity;
import entities.MediumSecurityEntity;

@Stateless
public class RegistrationEJB implements LocalRegistrationEJB {

	private final Integer clientId = 30770;
	private final String secretKey = "5I7U3b492h87TmEQoXe5qfInLiQ=";

	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;

	@EJB
	private MediumSecurityDAOBean mediumSecurityDAOBean;

	@EJB
	private LowSecurityDAOBean lowSecurityDAOBean;

	@Override
	public boolean registerUser(String username, String password, String otp) {
		if(validateUser(username, password, otp)){
			LowSecurityEntity low = new LowSecurityEntity();
			low.setUsername(username);
			low.setPassword(password);
			
			MediumSecurityEntity medium = new MediumSecurityEntity();
			medium.setUsername(username);
			medium.setPassword(MD5.getMD5(password));
			
			HighSecurityEntity high = new HighSecurityEntity();
			high.setUsername(username);
			
			try {
				String hashedPassword = PBKDF2.generateHashedPassword(password);
				high.setPassword(hashedPassword);
				System.out.println("Hashed password = " +hashedPassword);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return false;
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
				return false;
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				return false;
			}
			
			high.setYubico(getYubicoId(otp));
			if(!high.getYubico().equals(null)){
				highSecurityDAOBean.saveUser(high);
				lowSecurityDAOBean.saveUser(low);
				mediumSecurityDAOBean.saveUser(medium);
				return true;
			}
			
		}
		
		return false;
	}
	
	private boolean validateUser(String username, String password, String otp) {
		boolean usernameValid = false;
		boolean passwordValid = false;
		if(!username.equals(null) || !username.trim().equals("")){
			//TODO nån regex kolla så man inte skriver in sql kod tex
			if(lowSecurityDAOBean.getUserByUsername(username) == null && mediumSecurityDAOBean.getUserByUsername(username) == null
					&& highSecurityDAOBean.getUserByUsername(username) == null) {
				
				usernameValid = true;
			}
			
		}
		if(!password.equals(null) || !password.trim().equals("")){
			passwordValid = true;
		}
		if(usernameValid && passwordValid && isOTPValid(otp)) {
			return true;
		}
		
		return false;
			
	}
	
	private boolean isOTPValid(String otp){
		boolean validOTP = YubicoClient.isValidOTPFormat(otp);
		return validOTP;
	}

	private String getYubicoId(String otp) {
		YubicoClient yubicoClient = YubicoClient.getClient(clientId, secretKey);
		String yubicoId = null;
			try {
				VerificationResponse response = yubicoClient.verify(otp);
				if(response.isOk()){
					yubicoId = YubicoClient.getPublicId(otp);
					return yubicoId;
				}
			} catch (YubicoVerificationException e) {
				System.out.println("YubicoVerificationException " + e);
				return yubicoId;
			} catch (YubicoValidationFailure e) {
				System.out.println("YubicoValidationFailure " + e);
				return yubicoId;
			}

		return yubicoId;
	}

}
