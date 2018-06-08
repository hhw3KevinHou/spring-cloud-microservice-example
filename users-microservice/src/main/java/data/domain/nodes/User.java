package data.domain.nodes;

import data.domain.rels.Action;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@NodeEntity
public class User {

	@Id
                     @GeneratedValue
	Long id;

	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	private Long birthDate;

	
                    @Relationship(type = "FOLLOWS", direction = Relationship.OUTGOING)
	Set<User> follows;

	
                    @Relationship(type = "FOLLOWS", direction = Relationship.INCOMING)
	Set<User> followers;

	
                     @Relationship(type = "ACTION", direction = Relationship.OUTGOING)
	Set<Action> actions;

	
                    @Relationship(type = "ACTION", direction = Relationship.OUTGOING)
	Set<Event> events;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Long birthDate) {
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}
}
