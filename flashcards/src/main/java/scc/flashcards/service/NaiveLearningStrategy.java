package scc.flashcards.service;

import scc.flashcards.model.AbstractLearningProfile;
import scc.flashcards.model.NaiveLearningProfile;
import scc.flashcards.model.User;

public class NaiveLearningStrategy implements LearningStrategy{

	@Override
	public AbstractLearningProfile createLearningProfile(User user) {
		AbstractLearningProfile profile = new NaiveLearningProfile();
		profile.setUser(user);
		return profile;
	}
	
}
