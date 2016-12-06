package scc.flashcards.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value="User", description="Simple User Model for our WebService")
public class User {

	@XmlElement(name = "user-id", required = true)
	@ApiModelProperty(value = "Id of the User")
	private int Id;
	@XmlElement(name = "first-name")
	private String firstName;
	@XmlElement(name = "last-name")
	private String lastName;
	@XmlElement(name = "login", required = true)
	private String login;
	@XmlElement(name = "password", required = true)
	private String password;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
