package scc.flashcards.model.learning;

import java.util.HashMap;

import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.user.User;

/**
 * Most basic learning profile, 
 * 
 * @author Timi
 *
 */
public class NaiveLearningProfile extends AbstractLearningProfile {

	private User user;
	
	private Box box;
	
	private HashMap<FlashCard, Integer> scoreMap;
	
	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}

	@Override
	public int getScore(FlashCard card) {
		return this.scoreMap.get(card);
	}

	@Override
	public void updateCard(FlashCard card, boolean isCorrect) {
		int modifier = isCorrect ? 1 : -1;
		int newScore = this.getScore(card) + modifier;
		this.scoreMap.put(card, newScore);
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	@Override
	public String getSerializedData() {
		return null;
	}

	@Override
	public void setSerializedData(String serializedData) {
		return;
	}

}
