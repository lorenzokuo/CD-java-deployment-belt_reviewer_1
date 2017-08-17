package com.scottmarden.beltreviewer.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="events")
public class Event {

	@Id
	@GeneratedValue
	private Long id;
	@Size(min=1)
	private String title;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="host_id")
	private User host;
	private Date date;
	@Size(min=1)
	private String city;
	private String state;
	private Date createdAt;
	private Date updatedAt;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "event_attendees",
		joinColumns = @JoinColumn(name = "event_id"),
		inverseJoinColumns = @JoinColumn(name = "attendee_id")
	)
	private List<User> Attendees;
	@OneToMany(mappedBy="event", fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	
	public Event() {
		
	}
	
	public Event(String title, User host, String city, String state) {
		this.title = title;
		this.host = host;
		this.city = city;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(String dateStr) {
		if(dateStr.equals("")) {
			this.date = null;
		}
		else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				this.date = df.parse(dateStr);
			} catch(ParseException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public String getDateStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(this.date);
		return dateStr;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public List<User> getAttendees() {
		return Attendees;
	}

	public void setAttendees(List<User> attendees) {
		Attendees = attendees;
	}
	
	
	
}
