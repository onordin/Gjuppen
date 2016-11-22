package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medium database table.
 * 
 */
@Entity
@Table(name="medium", schema="gjuppen")
@NamedQueries({
	@NamedQuery(name="MediumSecurityEntity.getUserByUsername", query="SELECT u FROM MediumSecurityEntity u WHERE u.username = :username")
})
public class MediumSecurityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String password;

	private String username;

	public MediumSecurityEntity() {
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