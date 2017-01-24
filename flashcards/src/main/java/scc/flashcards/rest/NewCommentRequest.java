package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;

import io.swagger.annotations.ApiModelProperty;

public class NewCommentRequest extends JsonRequest{
	
	@ApiModelProperty(required=true)
	private String subject;
	
	@ApiModelProperty(required=true)
	private String content;
	
	@ApiModelProperty(required=true)
	private Long authorId;
	
	public NewCommentRequest() {
		
	}
	

	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}

	


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	@Override
	public boolean validateRequest() {
		if(content==null || content.isEmpty()){
			throw new BadRequestException("Missing content");
		}
		if(authorId==null){
			throw new BadRequestException("No Author given.");
		}
		return true;
	}
	
	
}
