package scc.flashcards.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value = "Box", description = "Simple Box Model for our WebService")
@Entity(name = "Box")
public class Box extends AbstractModel {
	
	private int id;
	
	private String title;
	
	private Category category;
	
	private Category subcategory;
	
	private String tags;
	
	private int owner_id;
	
	private Set<FlashCard> flashcards;
	
	private boolean isPublic;
	
	public Box(){}
	
	public Box(String title, Category category, Category subcategory, String tags, int owner_id) {
		super();
		this.title = title;
		this.category = category;
		this.subcategory = subcategory;
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
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id", referencedColumnName="id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="subcategory_id", referencedColumnName="id")
	public Category getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Category subcategory) {
		this.subcategory = subcategory;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	public int getOwner() {
		return owner_id;
	}

	public void setOwner(int owner) {
		this.owner_id = owner;
	}

	@ApiModelProperty(hidden = true)
	@ElementCollection
	@OneToMany(mappedBy="box",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public Set<FlashCard> getFlashcards() {
		return flashcards;
	}

	public void setFlashcards(Set<FlashCard> flashcards) {
		this.flashcards = flashcards;
	}
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
