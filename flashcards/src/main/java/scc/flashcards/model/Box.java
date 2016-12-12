package scc.flashcards.model;

import java.util.Collection;

public class Box {
	
	private int id;
	
	/**
	 * Title of the Box
	 */
	private String title;
	
	private int category_id;
	
	private int subcategory_id;
	
	private Collection<String> tags;
	
	private User owner;
	
	private Collection<FlashCard> flashcards;
	
	public Box(){}
	
	public Box(int id, String title, int category_id, int subcategory_id, Collection<String> tags, User owner) {
		super();
		this.id = id;
		this.title = title;
		this.category_id = category_id;
		this.subcategory_id = subcategory_id;
		this.tags = tags;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getSubcategory_id() {
		return subcategory_id;
	}

	public void setSubcategory_id(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}

	public Collection<String> getTags() {
		return tags;
	}

	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Collection<FlashCard> getFlashcards() {
		return flashcards;
	}

	public void setFlashcards(Collection<FlashCard> flashcards) {
		this.flashcards = flashcards;
	}
	
	
}
