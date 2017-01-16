package scc.flashcards.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(value = "FlashCard", description = "Simple Flash Card Model for our WebService")
@Entity(name = "FlashCard")
public class FlashCard extends AbstractModel{

	private List<Comment> comments = new ArrayList<Comment>();
	
	private Page frontpage;
	
	private Page backpage;
	
	private Box box;
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="content", column = @Column(name="frontpage") ),
        @AttributeOverride(name="contentType", column = @Column(name="frontpage_type") )
	} )
	public Page getFrontpage() {
		return frontpage;
	}
	public void setFrontpage(Page frontpage) {
		this.frontpage = frontpage;
	}
	
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="content", column = @Column(name="backpage") ),
        @AttributeOverride(name="contentType", column = @Column(name="backpage_type") )
	} )
	
	public Page getBackpage() {
		return backpage;
	}
	public void setBackpage(Page backpage) {
		this.backpage = backpage;
	}
	
	@ApiModelProperty(hidden = true)
	@OneToMany(mappedBy="flashcard", fetch=FetchType.EAGER)
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public FlashCard(){}
	
	public FlashCard(Page frontpage, Page backpage) {
		super();
		this.frontpage = frontpage;
		this.backpage = backpage;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="box_id", referencedColumnName="id")
	@XmlTransient
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
	
	


	

}
