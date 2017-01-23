package scc.flashcards.model.learning;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Property;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.swagger.annotations.ApiModel;
import scc.flashcards.model.AbstractModel;

@XmlRootElement
@ApiModel(value = "LearningProfile", description = "Model for Learning Profiles")
@Entity(name = "LearningProfile")
public abstract class AbstractLearningProfile extends AbstractModel implements LearningProfile {

	private long userId;

	private long boxId;

	@Column(name = "data", columnDefinition="TEXT NULL")
	public abstract String getSerializedData();

	public abstract void setSerializedData(String serializedData);

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}



}
