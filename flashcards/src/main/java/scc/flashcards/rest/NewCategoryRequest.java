package scc.flashcards.rest;

import java.util.List;

import javax.ws.rs.FormParam;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Category;

public class NewCategoryRequest extends JsonRequest{

	@ApiModelProperty(required=true)
	private String title;
	
	@ApiModelProperty(required=true)
	private Long parentCategoryId;

	public NewCategoryRequest() {
		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Long parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	@Override
	public boolean validateRequest() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean hasParentCategory(){
		if(this.parentCategoryId != null && this.parentCategoryId >= 0){
			return true;
		}
		return false;
	}
	
}
