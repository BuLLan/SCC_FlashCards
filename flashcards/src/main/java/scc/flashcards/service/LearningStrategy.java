
package scc.flashcards.service;

import scc.flashcards.model.learning.AbstractLearningProfile;
import scc.flashcards.model.user.User;

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

