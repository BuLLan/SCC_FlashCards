package scc.flashcards.model;

public class FlashCard {
	
	private int id;
	
	private Page frontpage;
	
	private Page backpage;
	
	private User owner;
	
	
	public FlashCard(Page frontpage, Page backpage, User owner) {
		super();
		this.frontpage = frontpage;
		this.backpage = backpage;
		this.owner = owner;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Page getFrontpage() {
		return frontpage;
	}

	public void setFrontpage(Page frontpage) {
		this.frontpage = frontpage;
	}

	public Page getBackpage() {
		return backpage;
	}

	public void setBackpage(Page backpage) {
		this.backpage = backpage;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}


}
