package scc.flashcards.service;

import java.time.Instant;
import java.util.Date;

import org.apache.commons.lang3.NotImplementedException;

import scc.flashcards.model.learning.CardScore;

/**
 * Each time a card is answered correctly, it's due date will be set one day longer than the previous time.
 * 
 * @author Timi
 *
 */
public class AdvancedLearningStrategy implements LearningStrategy{

	public CardScore scoreCard(CardScore score, boolean isCorrect) {
		throw new NotImplementedException("Currently not supported!");
	}
	
	private Date calculateDueDate(int score){
		long daysInSeconds = score * 86400;
		return Date.from(Instant.now().plusSeconds(daysInSeconds));
	}
	
}
