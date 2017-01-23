package scc.flashcards.service;

import java.time.Instant;
import java.util.Date;

import scc.flashcards.model.learning.CardScore;

/**
 * Each time a card is answered correctly, it's due date will be set one day longer than the previous time.
 * 
 * @author Timi
 *
 */
public class LinearLearningStrategy implements LearningStrategy{

	public CardScore scoreCard(CardScore score, boolean isCorrect) {
		score.setScore(score.getScore() + 1);
		score.setDue(calculateDueDate(score.getScore()));
		score.setLastUpdate(Date.from(Instant.now()));
		
		return score;
	}
	
	private Date calculateDueDate(int score){
		long daysInSeconds = score * 86400;
		return Date.from(Instant.now().plusSeconds(daysInSeconds));
	}
	
}
