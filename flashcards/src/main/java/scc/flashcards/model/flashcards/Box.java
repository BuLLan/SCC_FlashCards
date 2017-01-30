package scc.flashcards.model.flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import scc.flashcards.model.AbstractModel;
import scc.flashcards.model.user.User;

@XmlRootElement
@ApiModel(value = "Box", description = "Simple Box Model for our WebService")
@Entity(name = "Box")
public class Box extends AbstractModel {
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	private String title;
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	private String description;
	
	@ApiModelProperty(required=true)
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="id")
	private Category category;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private List<String> tags = new ArrayList<String>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	
	@ManyToMany(mappedBy="boxes")
	@XmlTransient
	private List<User> user;
	
	@ApiModelProperty(hidden = true)
	@XmlTransient
	@OneToMany(mappedBy="box", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<FlashCard> flashcards;
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	private boolean isPublic;
	
	public Box(){}
	
	public Box(String title, Category category, String description, List<String> tags, User owner) {
		super();
		this.title = title;
		this.category = category;
		this.tags = tags;
		this.owner = owner;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	@JsonIgnore
	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> users) {
		this.user = users;
	}

	@JsonIgnore
	public Set<FlashCard> getFlashcards() {
		return flashcards;
	}

	public void setFlashcards(Set<FlashCard> flashcards) {
		this.flashcards = flashcards;
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
