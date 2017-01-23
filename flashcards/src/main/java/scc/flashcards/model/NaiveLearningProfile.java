package scc.flashcards.model;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import com.google.gson.Gson;

/**
 * Most basic learning profile, 
 * 
 * @author Timi
 *
 */
public class NaiveLearningProfile extends AbstractLearningProfile {

	private User user;
	
	private Box box;
	
	private HashMap<FlashCard, Integer> scoreMap;
	
	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}

	@Override
	public int getScore(FlashCard card) {
		return this.scoreMap.get(card);
	}

	@Override
	public void updateCard(FlashCard card, boolean isCorrect) {
		int modifier = isCorrect ? 1 : -1;
		int newScore = this.getScore(card) + modifier;
		this.scoreMap.put(card, newScore);
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	@Override
	public String getSerializedData() {
		Gson gson = new Gson();
		return gson.toJson(this.scoreMap);
	}

	@Override
	public void setSerializedData(String serializedData) {
		Gson gson = new Gson();
		this.scoreMap = gson.fromJson(serializedData, this.scoreMap.getClass());
	}

}
