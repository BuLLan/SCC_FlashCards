package scc.flashcards.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumns;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Group", description = "Groups can share boxes and comments")
@Entity(name = "Group")
@Table(name = "groups")
@XmlRootElement
public class Group extends AbstractModel {

	private String title;

	private String description;

	private Map<User, UserRole> users = new TreeMap<User, UserRole>();

	private Set<Box> boxes;

	public Group() {
	}

	public Group(Integer id, String title, String description, Map<User, UserRole> users, Set<Box> boxes) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.users = users;
		this.boxes = boxes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ApiModelProperty(hidden = true)
	@XmlTransient
	@JsonIgnore
	@ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="group_users",
                     joinColumns=@JoinColumn(name="group_id"))
    @Column(name="role")
	@MapKeyJoinColumn(name="user_id")
//	@MapKeyJoinColumns(value = {
//			@MapKeyJoinColumn(table="group_users", name="user_id", referencedColumnName="id"),
//			@MapKeyJoinColumn(table="group_users", name="group_id", referencedColumnName="id")
//	})
////	@JoinTable(name = "group_users", joinColumns = {
////			@JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
////					@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) })
	public Map<User, UserRole> getUsers() {
		return this.users;
	}

	public void setUsers(Map<User, UserRole> users) {
		this.users = users;
	}

	@ApiModelProperty(hidden = true)
	@XmlTransient
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "group_boxes", joinColumns = {
			@JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "box_id", referencedColumnName = "id", nullable = false) })
	public Set<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(Set<Box> boxes) {
		this.boxes = boxes;
	}

	@Id
	@XmlID
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;

	}

}
