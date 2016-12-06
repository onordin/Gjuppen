package ejb;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.yubico.client.v2.VerificationResponse;
import com.yubico.client.v2.YubicoClient;
import com.yubico.client.v2.exceptions.YubicoValidationFailure;
import com.yubico.client.v2.exceptions.YubicoVerificationException;

import dao.HighSecurityDAOBean;
import displayEntities.HighSecurityDisplayEntity;
import displayEntities.MediumSecurityDisplayEntity;
import ejb.interfaces.LocalHighLoginEJB;
import ejb.passwordencryption.MD5;
import ejb.passwordencryption.PBKDF2;
import entities.HighSecurityEntity;
import entities.MediumSecurityEntity;

/**
 * Business logic for high security login.
 */

@Stateless
public class HighSecurityLoginEJB implements LocalHighLoginEJB {
	
	private Integer clientId = 30770;
	private String secretKey = "5I7U3b492h87TmEQoXe5qfInLiQ=";
	
	@EJB
	private HighSecurityDAOBean highSecurityDAOBean;

	@Override
	public HighSecurityDisplayEntity login(String username, String password) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		HighSecurityDisplayEntity entityToReturn;
		HighSecurityEntity highSecurityEntity = highSecurityDAOBean.getUserByUsername(username);
		if(highSecurityEntity != null) {
			if(PBKDF2.validatePassword(password, highSecurityEntity.getPassword(), highSecurityEntity.getSalt())) {
				entityToReturn = new HighSecurityDisplayEntity();
				entityToReturn.setUsername(highSecurityEntity.getUsername());
				entityToReturn.setHashedPassword(highSecurityEntity.getPassword());
				entityToReturn.setSalt(highSecurityEntity.getSalt());
				entityToReturn.setYubicoId(highSecurityEntity.getYubico());
				return entityToReturn;
			} 
		}
		return null;
	}

	
	
	
	@Override
	public String yubicoHandler(HighSecurityDisplayEntity returnedEntity, String otp){
		YubicoClient yubicoClient = YubicoClient.getClient(clientId, secretKey);
		if(YubicoClient.isValidOTPFormat(otp)) {
			try {
				VerificationResponse response = yubicoClient.verify(otp);
				if (response.isOk()) {
					if(YubicoClient.getPublicId(otp)
						    .equals(returnedEntity.getYubicoId())) {
						return "loggedOnHighSecurity";
					} else {
						System.out.println("4");
						return "This Yubico key doesn't belong to this user.";
					}
				}
				return "Response is not Ok. Old one-time-password.";
			} catch (YubicoVerificationException e) {
				e.printStackTrace();
			} catch (YubicoValidationFailure e) {
				e.printStackTrace();
			}
		} 
		return "Use your YubicoKey idiot.";
		
		
			
		
	}




}

