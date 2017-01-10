package scc.flashcards.service;

import scc.flashcards.model.AbstractLearningProfile;
import scc.flashcards.model.User;

/**
 * Interface for all kinds of learning strategy.
 * 
 * @author Timi
 *
 */
public interface LearningStrategy {

	/**
	 * Factory Method for creating a specific learning profile
	 * @param user
	 * @return
	 */
	public AbstractLearningProfile createLearningProfile(User user);
	
}
