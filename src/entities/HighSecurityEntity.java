package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the high database table.
 * 
 */
@Entity
@Table(name="high", schema="gjuppen")
@NamedQueries({
	@NamedQuery(name="HighSecurityEntity.getUserByUsername", query="SELECT u FROM HighSecurityEntity u WHERE u.username = :username")
})
public class HighSecurityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String password;

	private String username;

	private String yubico;
	
	private String salt;

	public HighSecurityEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getYubico() {
		return this.yubico;
	}

	public void setYubico(String yubico) {
		this.yubico = yubico;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}