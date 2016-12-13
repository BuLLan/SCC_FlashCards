package scc.flashcards.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import scc.flashcards.model.Box;
import scc.flashcards.model.FlashCard;
import scc.flashcards.model.FlashCardScore;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

public class ScoreService {

	private Session session;

	public FlashCard getNext(int user_id, int box_id, int flashcard_id, boolean isCorrect) {

		Box currentBox;
		User currentUser;
		FlashCard currentFlashcard;

		try {
			currentBox = this.getSession().get(Box.class, box_id);
			currentUser = this.getSession().get(User.class, user_id);
			currentFlashcard = this.getSession().get(FlashCard.class, flashcard_id);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}

		FlashCardScore fcs = new FlashCardScore(currentUser, currentFlashcard);
		int score = (isCorrect) ? -1 : 1;
		score += fcs.getScore();
		score = (score > 0) ? score : 0;
		fcs.setScore(score);

		try {
			this.getSession().beginTransaction();
			this.getSession().save(fcs);
			this.getSession().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.closeSession();

		return new FlashCard();
	}

	private Session getSession() {
		if (this.session == null) {
			this.session = PersistenceHelper.getInstance().getSessionFactory().openSession();
		}

		return this.session;
	}

	private void closeSession() {
		if (this.session == null || !this.session.isOpen()) {
			return;
		}

		this.session.close();
	}

	public FlashCard getCardWithLowestScore(int user_id, int box_id){
		//Gather all Cards
		List<FlashCard> allCards = this.getSession().createCriteria(FlashCard.class, "flashcard")
			.createAlias("flashcard.box", "box")
			.add(Restrictions.eq("box.id", box_id))
			.list();
		List<FlashCard> newCards = new LinkedList<FlashCard>(allCards);
		
		List allScores = this.getSession().createCriteria(FlashCardScore.class, "fcs")
			.createAlias("fcs.user", "user")
			.createAlias("fcs.flashcard", "flashcard")
			.createAlias("flashcard.box", "box")
			.add(Restrictions.eq("user.id", user_id))
			.add(Restrictions.eq("box.id", box_id))
			.list();
		Iterator<FlashCardScore> it = allScores.iterator();
		FlashCardScore score;
		FlashCard fc;
		int lowestScore = 9999999;
		FlashCard lowestCard = null;
		while(it.hasNext()){
			score = it.next();
			int score_id = score.getFlashcard().getId();
			if(lowestScore > score.getScore()){
				lowestScore = score.getScore();
				lowestCard = score.getFlashcard();
			}
			//Remove from newCards
			Iterator<FlashCard> fc_it = allCards.iterator();
			while(fc_it.hasNext()){
				fc = fc_it.next();
				if(fc.getId() == score_id){
					newCards.remove(fc);
					break;
				}
			}
		}
		if(!newCards.isEmpty()){
			return newCards.get(0);
		}
		
		System.out.println(allScores.toString());
				
		return lowestCard;
	}

}
