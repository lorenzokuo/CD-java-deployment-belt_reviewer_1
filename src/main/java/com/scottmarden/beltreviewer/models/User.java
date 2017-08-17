package com.scottmarden.beltreviewer.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue
	private Long id;
	@Size(min=3)
	private String firstName;
	@Size(min=3)
	private String lastName;
	@Size(min=1)
	@Pattern(regexp=".+@.+\\..+")
	private String email;
	@Size(min=1)
	private String city;
	private String state;
	@Size(min=10)
	private String password;
	private String passwordConfirmation;
	private Date createdAt;
	private Date updatedAt;
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<Comment> comments;
	@OneToMany(mappedBy="host", fetch=FetchType.LAZY)
	private List<Event> hosted_events;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "event_attendees",
		joinColumns = @JoinColumn(name = "attendee_id"),
		inverseJoinColumns = @JoinColumn(name = "event_id")
	) 
	private List<Event> eventsAttending;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "users_roles",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
	
	
	public User() {
		
	}
	
	
	

	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email.trim();
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city.trim();
	}
	
	
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state.trim();
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}




	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}




	public Date getCreatedAt() {
		return createdAt;
	}




	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public Date getUpdatedAt() {
		return updatedAt;
	}




	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}




	public List<Event> getHosted_events() {
		return hosted_events;
	}




	public void setHosted_events(List<Event> hosted_events) {
		this.hosted_events = hosted_events;
	}




	public List<Event> getEventsAttending() {
		return eventsAttending;
	}




	public void setEventsAttending(List<Event> eventsAttending) {
		this.eventsAttending = eventsAttending;
	}




	public List<Role> getRoles() {
		return roles;
	}




	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}




	@PrePersist
	protected void onCreate() {
		this.setCreatedAt(new Date());
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.setUpdatedAt(new Date());
	}
}
