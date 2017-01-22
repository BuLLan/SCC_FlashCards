package scc.flashcards.rest;

import javax.persistence.QueryHint;
import javax.ws.rs.BadRequestException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.validator.GenericValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
public class UpdateUserRequest extends JsonRequest {
	
	private int id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private boolean validateMail = true;
	
	public UpdateUserRequest(){}

	/**
	 * Validates mail address of user.
	 * 
	 * @throws BadRequestException
	 */
	public boolean validateRequest() {
		//Only validate mail, if new mail was set
		if(this.email.isEmpty()){
			return true;
		}
		
		if(GenericValidator.isEmail(this.email)){
			return true;
		} else {
			throw new BadRequestException("Invalid Email address");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidateMail() {
		return validateMail;
	}

	public void setValidateMail(boolean validateMail) {
		this.validateMail = validateMail;
	}
	
	
	

}
