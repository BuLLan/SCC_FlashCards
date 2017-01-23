package scc.flashcards.model.user;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum UserRole{
	Admin, Editor, Member
}
