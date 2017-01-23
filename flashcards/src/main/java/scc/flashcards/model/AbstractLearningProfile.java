package scc.flashcards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Property;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.swagger.annotations.ApiModel;

@XmlRootElement
@ApiModel(value = "LearningProfile", description = "Model for Learning Profiles")
@Entity(name = "LearningProfile")
public abstract class AbstractLearningProfile extends AbstractModel implements LearningProfile {

	private int id;

	private int user_id;

	private int box_id;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "data", columnDefinition="TEXT NULL")
	public abstract String getSerializedData();

	public abstract void setSerializedData(String serializedData);

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getBox_id() {
		return box_id;
	}

	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}

}
