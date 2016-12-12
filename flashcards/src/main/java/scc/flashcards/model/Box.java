package scc.flashcards.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "Box", description = "Simple Box Model for our WebService")
@Entity(name = "Box")
public class Box extends AbstractModel {
	
	private int id;
	
	/**
	 * Title of the Box
	 */
	private String title;
	
	private int category_id;
	
	private int subcategory_id;
	
	private String tags;
	
	private int owner_id;
	
	private Set<FlashCard> flashcards;
	
	public Box(){}
	
	public Box(String title, int category_id, int subcategory_id, String tags, int owner_id) {
		super();
		this.title = title;
		this.category_id = category_id;
		this.subcategory_id = subcategory_id;
		this.tags = tags;
		this.owner_id = owner_id;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getOwner() {
		return owner_id;
	}

	public void setOwner(int owner) {
		this.owner_id = owner;
	}

	@ElementCollection
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="box_id")
	public Set<FlashCard> getFlashcards() {
		return flashcards;
	}

	public void setFlashcards(Set<FlashCard> flashcards) {
		this.flashcards = flashcards;
	}
	
	
}
