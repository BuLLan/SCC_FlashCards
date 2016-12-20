package scc.flashcards.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "Group", description = "Groups can share boxes and comments")
@Entity(name = "FlashCard")
public class Group extends AbstractModel{

	private Integer id;
	
	private String name;
	
	private Set<User> members;
	
	private Set<Box> sharedBoxes;
	
	public Group(){}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "group_members",  joinColumns = {
			@JoinColumn(name = "group_id", referencedColumnName="id", nullable = false, updatable = false) },
			inverseJoinColumns = { 
					@JoinColumn(name = "member_id", referencedColumnName="id", nullable = false, updatable = false) })
	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	@ManyToMany
	@JoinTable(name = "group_boxes",  joinColumns = {
			@JoinColumn(name = "group_id", referencedColumnName="id", nullable = false, updatable = false) },
			inverseJoinColumns = { 
					@JoinColumn(name = "box_id", referencedColumnName="id", nullable = false, updatable = false) })
	public Set<Box> getSharedBoxes() {
		return sharedBoxes;
	}

	public void setSharedBoxes(Set<Box> sharedBoxes) {
		this.sharedBoxes = sharedBoxes;
	};
	
	
}
