package displayEntities;


public class HighSecurityDisplayEntity {
	private String username;
	private String hashedPassword;
	private String salt;
	private String yubicoId;
	
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getYubicoId() {
		return yubicoId;
	}
	public void setYubicoId(String yubicoId) {
		this.yubicoId = yubicoId;
	}
	
	
}
