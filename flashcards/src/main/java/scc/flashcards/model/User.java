package scc.flashcards.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value = "User", description = "Simple User Model for our WebService")
@Entity(name = "User")
public class User extends AbstractModel {

	private Integer id;

	private String firstName;

	private String lastName;

	private String login;

	private String password;

	/**
	 * Empty Constructor for JAXB, don't use!
	 */
	public User() {
	}

	public User(Integer id, String firstName, String lastName, String login, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
