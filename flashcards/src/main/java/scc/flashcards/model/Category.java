package scc.flashcards.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "Category", description = "Simple Flash Card Model for our WebService")
@Entity(name = "Category")
public class Category extends AbstractModel {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_category_id", referencedColumnName="id")
	private Category parent_category;
	
	public Category(){}

	public Category(int id, String title, Category parent_category) {
		super();
		this.id = id;
		this.title = title;
		this.parent_category = parent_category;
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

	public Category getParent_category() {
		return parent_category;
	}

	public void setParent_category(Category parent_category) {
		this.parent_category = parent_category;
	};
	
	
}
