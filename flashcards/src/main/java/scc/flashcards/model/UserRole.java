package scc.flashcards.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "User", description = "Simple User Model for our WebService")
@Entity(name = "User")
public class UserRole extends AbstractModel {

	private int id;

	private String roleName;

	private String email;
	
	/**
	 * Empty Constructor for JAXB, don't use!
	 */
	public UserRole() {
	}

//	public UserRole(String firstName, String lastName, String login, String password) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.login = login;
//		this.password = password;
//	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
