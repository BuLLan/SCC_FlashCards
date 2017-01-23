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
public class RolesPermission extends AbstractModel {

	private String roleName;

	private String permission;
	/**
	 * Empty Constructor for JAXB, don't use!
	 */
	public RolesPermission() {
	}

//	public UserRole(String firstName, String lastName, String login, String password) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.login = login;
//		this.password = password;
//	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}



}