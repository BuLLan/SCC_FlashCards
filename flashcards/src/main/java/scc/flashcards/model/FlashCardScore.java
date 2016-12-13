package scc.flashcards.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "FlashCardScore", description = "Simple Score Model for our WebService")
@Entity(name = "FlashCardScore")
public class FlashCardScore {
	
	private int id;

	private User user;
	
	private FlashCard flashcard;
	
	private int score;
	
	private Date last_update;
	
	public FlashCardScore () {}
	
	public FlashCardScore(User user, FlashCard flashcard) {
		super();
		this.user = user;
		this.flashcard = flashcard;
		this.score = 0;
		this.last_update = Date.from(Instant.now());
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

	@ManyToOne(cascade=CascadeType.ALL) 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(cascade=CascadeType.ALL) 
	public FlashCard getFlashcard() {
		return flashcard;
	}

	public void setFlashcard(FlashCard flashcard) {
		this.flashcard = flashcard;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
	
	
}
