package scc.flashcards.model.user;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.owlike.genson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import scc.flashcards.model.AbstractModel;

@XmlRootElement
@ApiModel(value = "User", description = "Simple User Model for our WebService")
@Entity(name = "User")
public class User extends AbstractModel {

	private String firstName;

	private String lastName;

	@ApiModelProperty(hidden=true)
	private String login;

	@ApiModelProperty(hidden=true)
	private String password;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Group> groups;

	public User() {
	}

	public User(String firstName, String lastName, String login, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.password = password;
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

	@JsonIgnore
	@XmlTransient
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonIgnore
	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
