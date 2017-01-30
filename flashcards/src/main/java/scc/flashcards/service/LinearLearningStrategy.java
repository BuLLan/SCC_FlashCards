package scc.flashcards.service;

import java.time.Instant;
import java.util.Date;

import scc.flashcards.model.learning.CardScore;

/**
 * Nnumber of Days to next due equals the cards score
 * 
 * @author Timi
 *
 */
public class LinearLearningStrategy implements LearningStrategy{

	public CardScore scoreCard(CardScore score, boolean isCorrect) {
		int newScore = score.getScore();
		newScore += (isCorrect) ? 1 : -1 ; 
		score.setScore(newScore);
		score.setDue(calculateDueDate(score.getScore()));
		score.setLastUpdate(Date.from(Instant.now()));
		
		return score;
	}
	
	private Date calculateDueDate(int score){
		long daysInSeconds = score * 86400;
		return Date.from(Instant.now().plusSeconds(daysInSeconds));
	}
	
}
