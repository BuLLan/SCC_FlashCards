package scc.flashcards.rest;

import io.swagger.annotations.ApiParam;

public class GetAllBoxesRequest extends JsonRequest {
	
	@ApiParam(value="Filters categories by id", required=false)
	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public boolean validateRequest() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
