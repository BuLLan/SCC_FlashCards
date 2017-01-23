package scc.flashcards.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.swagger.annotations.ApiModel;

@Embeddable
@ApiModel(value = "Page", description = "Page Model")
public class Page {

	public Page(){};
	
	public Page(ContentType contentType, String content) {
		super();
		this.contentType = contentType;
		this.content = content;
	}
	
	@Enumerated(EnumType.STRING)
	private ContentType contentType;
	public ContentType getContentType() {
		return contentType;
	}
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
