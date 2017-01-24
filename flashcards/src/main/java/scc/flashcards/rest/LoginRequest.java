package scc.flashcards.rest;

import javax.persistence.QueryHint;
import javax.ws.rs.BadRequestException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.validator.GenericValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
public class LoginRequest extends JsonRequest {

	@ApiModelProperty(required=true)
	private String email;

	@ApiModelProperty(required=true)
	private String password;
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private boolean validateMail = true;
	
	public LoginRequest(){}

	/**
	 * Validates mail address of user.
	 * 
	 * @throws BadRequestException
	 */
	public boolean validateRequest() {
		if(GenericValidator.isEmail(this.email)){
			return true;
		} else {
			throw new BadRequestException("Invalid Email address");
		}
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
