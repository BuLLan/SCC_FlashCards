package scc.flashcards.model.learning;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;

import scc.flashcards.model.AbstractModel;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.user.User;

public class CardScore extends AbstractModel{

	@ManyToOne
	User user;
	
	@ManyToOne
	FlashCard flashcard;
	
	private int score;
	
	private Date created;
	
	private Date lastUpdate;
	
	private Date due;
	
	public CardScore()
	{
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	};
	
	
}
