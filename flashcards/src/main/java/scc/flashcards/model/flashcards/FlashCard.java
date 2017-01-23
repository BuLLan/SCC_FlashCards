package scc.flashcards.model.flashcards;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.owlike.genson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import scc.flashcards.model.AbstractModel;

@XmlRootElement
@ApiModel(value = "FlashCard", description = "Simple Flash Card Model for our WebService")
@Entity(name = "FlashCard")
public class FlashCard extends AbstractModel{

	@ApiModelProperty(hidden = true)
	@XmlTransient
	@JsonIgnore
	@OneToMany(mappedBy="flashcard")
	private List<Comment> comments = new ArrayList<Comment>();
	
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="content", column = @Column(name="frontpage") ),
        @AttributeOverride(name="contentType", column = @Column(name="frontpage_type") )
	} )
	private Page frontpage;
	
	@Embedded
	@AttributeOverrides( {
        @AttributeOverride(name="content", column = @Column(name="backpage") ),
        @AttributeOverride(name="contentType", column = @Column(name="backpage_type") )
	} )
	private Page backpage;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="box_id", referencedColumnName="id")
	private Box box;
	
	
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
	
	@JsonIgnore
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
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
	
	


	

}
