package scc.flashcards.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class NewBoxRequest extends JsonRequest{

	@ApiParam(value="If set, adds the box with the given id to the users boxes",required=false)
	private Long boxId;
	
	@ApiModelProperty(required=true)
	private String title;
		
	@ApiModelProperty(required=true)
	private Long categoryId;
	
	@ApiModelProperty(required=false)
	private  List<String> tags = new ArrayList<String>();
	
	@ApiModelProperty(required=true)
	private boolean isPublic;
	
	public NewBoxRequest() {
		super();
	}

	public boolean validateRequest() {
		if(boxId!=null){
			return true;
		} else {
			if(title.isEmpty() || categoryId == null) {
				throw new BadRequestException("Title and category are mandatory");
			}
		}
		return true;
	}
	
	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	
	
}
