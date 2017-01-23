package scc.flashcards.model.flashcards;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import scc.flashcards.model.AbstractModel;

@XmlRootElement
@ApiModel(value = "Category", description = "Simple Flash Card Model for our WebService")
@Entity(name = "Category")
public class Category extends AbstractModel {

	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="parent_category_id", referencedColumnName="id")
	private Category parentCategory;
	
	public Category(){}

	public Category(int id, String title, Category parentCategory) {
		super();
		this.id = id;
		this.title = title;
		this.parentCategory = parentCategory;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	};
	
	
}
