package scc.flashcards.model.flashcards;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import io.swagger.annotations.ApiModel;
import scc.flashcards.model.AbstractModel;
import scc.flashcards.model.user.User;

/**
 * Models the current score of a card for a user
 * 
 * @author Timi
 *
 */
@XmlRootElement
@ApiModel(value = "CardScore", description = "Simple Box Model for our WebService")
@Entity(name = "CardScore")
public class CardScore extends AbstractModel{

	 @XmlTransient
	 @OneToMany
	 private User user;
	 
	 @XmlTransient
	 @OneToMany
	 private FlashCard flashcard;
	 
	 private int score;
	 
	 public CardScore() {
		 
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
	
}
