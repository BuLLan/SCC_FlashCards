
package scc.flashcards.service;

import scc.flashcards.model.learning.AbstractLearningProfile;
import scc.flashcards.model.learning.NaiveLearningProfile;
import scc.flashcards.model.user.User;

public class NaiveLearningStrategy implements LearningStrategy{

	@Override
	public AbstractLearningProfile createLearningProfile(User user) {
		AbstractLearningProfile profile = new NaiveLearningProfile();
		profile.setUser(user);
		return profile;
	}
	
}

