package scc.flashcards.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "FlashCard", description = "Simple Flash Card Model for our WebService")
@Entity(name = "FlashCard")
public class FlashCard extends AbstractModel{
	
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
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
	private Page frontpage;
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
	private Page backpage;
	public Page getBackpage() {
		return backpage;
	}
	public void setBackpage(Page backpage) {
		this.backpage = backpage;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="flashcard_id", referencedColumnName="id")
	private List<Comment> comments;
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Column(name="box_id")
	private int boxId;
	public int getBoxId() {
		return boxId;
	}
	public void setBox(int boxId) {
		this.boxId = boxId;
	}
	
	public FlashCard(){}
	
	public FlashCard(Page frontpage, Page backpage, int boxId) {
		super();
		this.frontpage = frontpage;
		this.backpage = backpage;
		this.boxId = boxId;
	}
	


	

}
