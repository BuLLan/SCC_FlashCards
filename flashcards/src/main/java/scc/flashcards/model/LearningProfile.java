package scc.flashcards.model;

/**
 * Represents a learning profile for a user.
 * 
 * Each learning Strategy should use a implementation of LearningProfile to keep
 * track of users learning progress.
 * 
 * @author Timi
 *
 */


public interface LearningProfile {

	public User getUser();
	
	public void setUser(User user);
	
	public Box getBox();
	
	public void setBox(Box box);
	
	/**
	 * Get the current score value of a flashcard
	 * 
	 * @param card A flashcard
	 * @return int
	 */
	public int getScore(FlashCard card);
	
	/**
	 * Update the card score
	 * @param card The FlashCard
	 * @param isCorrect wether the user was correct or not
	 */
	public void updateCard(FlashCard card, boolean isCorrect);
	
}
