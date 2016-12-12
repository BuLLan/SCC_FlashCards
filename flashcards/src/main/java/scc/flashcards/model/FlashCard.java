package scc.flashcards.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "FlashCard", description = "Simple Flash Card Model for our WebService")
@Entity(name = "FlashCard")
public class FlashCard extends AbstractModel{
	
	
	private int id;
	
	private String frontpage;
	
	private String backpage;
	
	private int box_id;
	
	public FlashCard(){}
	
	public FlashCard(String frontpage, String backpage, int box_id) {
		super();
		this.frontpage = frontpage;
		this.backpage = backpage;
		this.box_id = box_id;
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

	public String getFrontpage() {
		return frontpage;
	}

	public void setFrontpage(String frontpage) {
		this.frontpage = frontpage;
	}

	public String getBackpage() {
		return backpage;
	}

	public void setBackpage(String backpage) {
		this.backpage = backpage;
	}

	public int getBox_id() {
		return box_id;
	}

	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}

}
