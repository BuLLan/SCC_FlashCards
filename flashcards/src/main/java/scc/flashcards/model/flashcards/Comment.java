package scc.flashcards.model.flashcards;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.owlike.genson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import scc.flashcards.model.AbstractModel;
import scc.flashcards.model.user.User;

@XmlRootElement
@ApiModel(value = "Comment", description = "Comment to a flashcard")
@Entity(name = "Comment")
public class Comment extends AbstractModel{

	@ManyToOne
	@JoinColumn(name="author_id", referencedColumnName="id")
	private User author;
	
	private String title;
	
	private String description;
	
	private long created_at;
	
	@ApiModelProperty(hidden=true)
	@ManyToOne
	@JoinColumn(name="flashcard_id", referencedColumnName="id")
	private FlashCard flashcard;
	
	public Comment(){}

	public Comment(int id, User author, String title, String description, long created_at) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.created_at = created_at;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	@JsonIgnore
	public FlashCard getFlashcard() {
		return flashcard;
	}

	public void setFlashcard(FlashCard flashcard) {
		this.flashcard = flashcard;
	};

	
	
	
}
