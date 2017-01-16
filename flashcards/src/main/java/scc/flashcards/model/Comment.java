package scc.flashcards.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value = "Comment", description = "Comment to a flashcard")
@Entity(name = "Comment")
public class Comment extends AbstractModel{

	private User author;
	
	private String title;
	
	private String description;
	
	private int created_at;
	
	private FlashCard flashcard;
	
	public Comment(){}

	public Comment(int id, User author, String title, String description, int created_at) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.created_at = created_at;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="author_id", referencedColumnName="id")
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

	public int getCreated_at() {
		return created_at;
	}

	public void setCreated_at(int created_at) {
		this.created_at = created_at;
	}

	@XmlTransient
	@ApiModelProperty(hidden=true)
	@ManyToOne
	@JoinColumn(name="flashcard_id", referencedColumnName="id")
	public FlashCard getFlashcard() {
		return flashcard;
	}

	public void setFlashcard(FlashCard flashcard) {
		this.flashcard = flashcard;
	};

	
	
	
}
