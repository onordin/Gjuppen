package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the low database table.
 * 
 */
@Entity
@Table(name="low", schema="gjuppen")
@NamedQueries({
	@NamedQuery(name="LowSecurityEntity.getUserByUsername", query="SELECT u FROM LowSecurityEntity u WHERE u.username = :username")
})
public class LowSecurityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String password;

	private String username;

	public LowSecurityEntity() {
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

}