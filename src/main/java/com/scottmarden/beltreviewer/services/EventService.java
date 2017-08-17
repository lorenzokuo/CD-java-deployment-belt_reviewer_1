package com.scottmarden.beltreviewer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scottmarden.beltreviewer.models.Comment;
import com.scottmarden.beltreviewer.models.Event;
import com.scottmarden.beltreviewer.models.User;
import com.scottmarden.beltreviewer.repositories.CommentRepository;
import com.scottmarden.beltreviewer.repositories.EventRepository;

@Service
public class EventService {
	
	private EventRepository eventRepository;
	private CommentRepository commentRepository;
	
	public EventService(EventRepository eventRepository, CommentRepository commentRepository) {
		this.eventRepository = eventRepository;
		this.commentRepository = commentRepository;
	}
	
	public void createEvent(Event event) {
		eventRepository.save(event);
	}
	
	public Event findEvent(Long id) {
		return eventRepository.findOne(id);
	}
	
	public List<Event> findEventsInState(String state){
		return eventRepository.findEventsInState(state);
	}
	
	public List<Event> findEventsOutState(String state){
		return eventRepository.findEventsOutState(state);
	}
	
	public void joinEvent(Long event_id, User user) {
		Event event = eventRepository.findOne(event_id);
		List<User> attendees = event.getAttendees();
		attendees.add(user);
		event.setAttendees(attendees);
		eventRepository.save(event);
	}
	
	public void leaveEvent(Long event_id, User user) {
		Event event = eventRepository.findOne(event_id);
		List<User> attendees = event.getAttendees();
		attendees.remove(user);
		event.setAttendees(attendees);
		eventRepository.save(event);
	}
	
	public void addComment(Long event_id, User user, Comment comment) {
		Event event = eventRepository.findOne(event_id);
		List<Comment> comments = event.getComments();
		comments.add(comment);
		event.setComments(comments);
		eventRepository.save(event);
		commentRepository.save(comment);
	}
	
	public void updateEvent(Long event_id, Event editEvent) {
		Event event = eventRepository.findOne(event_id);
		event.setTitle(editEvent.getTitle());
		event.setDate(editEvent.getDateStr());
		event.setCity(editEvent.getCity());
		event.setState(editEvent.getState());
		eventRepository.save(event);
	}
	
	public void destroyEvent(Long id) {
		eventRepository.delete(id);
	}
}
