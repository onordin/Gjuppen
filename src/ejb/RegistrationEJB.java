package ejb;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import messageservice.MessageService;

/**
 * Business logic for registration.
 */

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

	@EJB
	private MessageService message;

	@Override
	public boolean registerUser(String username, String password, String otp) {
		if (validateUser(username, password, otp)) {
			LowSecurityEntity low = new LowSecurityEntity();
			low.setUsername(username);
			low.setPassword(password);

			MediumSecurityEntity medium = new MediumSecurityEntity();
			medium.setUsername(username);
			medium.setPassword(MD5.getMD5(password));

			HighSecurityEntity high = new HighSecurityEntity();
			high.setUsername(username);
			try {
				byte[] salt = PBKDF2.getSalt();
				char[] passwordAsChar = password.toCharArray();
				high.setSalt(PBKDF2.toHex(salt));
				high.setPassword(PBKDF2.generateHashedPassword(passwordAsChar, salt));
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

			String yubicoId = getYubicoIdFromOTP(otp);

			if (yubicoId != null) {
				 if (highSecurityDAOBean.getUserByYubicoId(yubicoId) == null)
				 {
				high.setYubico(yubicoId);
				highSecurityDAOBean.saveUser(high);
				lowSecurityDAOBean.saveUser(low);
				mediumSecurityDAOBean.saveUser(medium);
				message.successMsg("registration", "Registration Successful");
				return true;
				 }
				 message.errorMsg("registration", "YubiKey already in use");
			}

		}
		return false;
	}

	private boolean validateUser(String username, String password, String otp) {

		if (isUsernameValid(username) && isPasswordValid(password) && isOTPValid(otp)) {
			return true;
		}
		return false;
	}

	private boolean isUsernameValid(String username) {
		if (!username.trim().equals("")) {

			if (lowSecurityDAOBean.getUserByUsername(username) == null
					&& mediumSecurityDAOBean.getUserByUsername(username) == null
					&& highSecurityDAOBean.getUserByUsername(username) == null) {

				return true;
			}
			message.errorMsg("registration", "Username taken");
			return false;
		}
		message.errorMsg("registration", "Username required");
		return false;
	}

	private boolean isPasswordValid(String password) {
		if (!password.trim().equals("")) {
			return true;
		}
		message.errorMsg("registration", "Password required");
		return false;
	}

	private boolean isOTPValid(String otp) {
		boolean validOTP = YubicoClient.isValidOTPFormat(otp);
		if (validOTP == false) {
			message.errorMsg("registration","Invalid OTP-format");
		}
		return validOTP;
	}

	private String getYubicoIdFromOTP(String otp) {
		YubicoClient yubicoClient = YubicoClient.getClient(clientId, secretKey);
		String yubicoId = null;
		try {
			VerificationResponse response = yubicoClient.verify(otp);
			if (response.isOk()) {
				yubicoId = YubicoClient.getPublicId(otp);
				return yubicoId;
			}
			message.errorMsg("registration", "Invalid OTP");
		} catch (YubicoVerificationException yve) {
			yve.printStackTrace();
			return yubicoId;
		} catch (YubicoValidationFailure yvf) {
			yvf.printStackTrace();
			return yubicoId;
		}

		return yubicoId;
	}

	@Override
	public void deleteAllUsers() {
		lowSecurityDAOBean.deleteAllUsers();
		mediumSecurityDAOBean.deleteAllUsers();
		highSecurityDAOBean.deleteAllUsers();
		message.successMsg("registration", "All users deleted");
	}
	
	public String checkPasswordStrength(String password) {
		String passwordStrength = "";
		if(password.trim().equals("")||password == null){
			return "Invalid password";
		}
		if(controlPassword(password) < 2 ) {
			passwordStrength = "Very strong";
		}
		if(controlPassword(password) >= 3 ) {
			passwordStrength = "Weak";
		}
		if(controlPassword(password) == 2) {
			passwordStrength = "Strong";
		}
		return passwordStrength;
		
	}
	
	private int controlPassword(String password){
		String upperCase = "[A-Z]";
		String lowerCase = "[a-z]";
		String digit = "[0-9]";
		String specialChar = "[!@#$&*]";
		int minimumLength = 8;
		int counter = 0;
		if(password.length() < minimumLength){
			counter +=2;
		}
		if(!isPasswordContaining(password, upperCase)){
			counter++;
		}
		if(!isPasswordContaining(password, lowerCase)){
			counter++;
		}
		if(!isPasswordContaining(password, digit)){
			counter++;
		}
		if(!isPasswordContaining(password, specialChar)){
			counter++;
		}
		
		return counter;
	}
	
	private boolean isPasswordContaining(String password, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		
		if(!matcher.find()){
			return false;
		}
		return true;
	}
}


