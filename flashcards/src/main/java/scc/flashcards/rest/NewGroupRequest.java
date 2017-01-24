package scc.flashcards.rest;

import io.swagger.annotations.ApiModelProperty;

public class NewGroupRequest extends JsonRequest{

	@ApiModelProperty(required=true)
	private String title;
	
	@ApiModelProperty(required=false)
	private String description;

	public NewGroupRequest() {
		super();
	}

	public boolean validateRequest() {
		return true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
