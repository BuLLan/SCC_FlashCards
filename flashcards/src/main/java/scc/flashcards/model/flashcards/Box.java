package scc.flashcards.model.flashcards;

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
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_id", referencedColumnName="id")
	private Category category;
	
	@ElementCollection
	private List<String> tags;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User owner;
	
	@ManyToMany(mappedBy="boxes")
	@XmlTransient
	private List<User> users;
	
	@ApiModelProperty(hidden = true)
	@ElementCollection
	@OneToMany(mappedBy="box",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<FlashCard> flashcards;
	
	@ApiModelProperty(required=true)
	@XmlAttribute(required=true)
	private boolean isPublic;
	
	public Box(){}
	
	public Box(String title, Category category, List<String> tags, User owner) {
		super();
		this.title = title;
		this.category = category;
		this.tags = tags;
		this.owner = owner;
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
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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
