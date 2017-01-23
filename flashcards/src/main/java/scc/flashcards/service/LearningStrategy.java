package scc.flashcards.service;

import scc.flashcards.model.learning.CardScore;

/**
 * Interface for all kinds of learning strategy.
 * 
 * @author Timi
 *
 */
public interface LearningStrategy {

	public CardScore scoreCard(CardScore score, boolean isCorrect);
	
}
