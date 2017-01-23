package scc.flashcards.rest;

import java.util.List;

import javax.ws.rs.FormParam;

import io.swagger.annotations.ApiParam;

public class NewBoxRequest extends JsonRequest{

	private String title;
	
	private long ownerId;
	
	private long categoryId;
	
	private  List<String> tags;
	
	private boolean isPublic;
	
	public NewBoxRequest() {
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

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
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
