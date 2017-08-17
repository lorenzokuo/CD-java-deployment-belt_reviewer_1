package com.scottmarden.beltreviewer.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scottmarden.beltreviewer.models.Comment;
import com.scottmarden.beltreviewer.models.Event;
import com.scottmarden.beltreviewer.models.User;
import com.scottmarden.beltreviewer.services.EventService;
import com.scottmarden.beltreviewer.services.UserService;
import com.scottmarden.beltreviewer.validators.EventValidator;

@Controller
public class EventCtrl {

	private UserService userService;
	private EventService eventService;
	private EventValidator eventValidator;
	
	public EventCtrl(UserService userService, EventService eventService, EventValidator eventValidator) {
		this.userService = userService;
		this.eventService = eventService;
		this.eventValidator = eventValidator;
	}
	
	@RequestMapping(value="/events")
	public String dashboard(@Valid @ModelAttribute("event") Event newEvent, Principal principal, Model model) {
		String userEmail = principal.getName();
		User currentUser = userService.findByEmail(userEmail);
		List<Event> mainEvents = eventService.findEventsInState(currentUser.getState());
		List<Event> otherEvents = eventService.findEventsOutState(currentUser.getState());
		model.addAttribute("mainEvents", mainEvents);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("states", userService.stateList());
		model.addAttribute("otherEvents", otherEvents);
		return "dashboard.jsp";
	}
	
	@PostMapping(value="/events/new")
	public String createEvent(@Valid @ModelAttribute("event") Event newEvent, BindingResult result, @RequestParam(value="date") String date, Principal principal, Model model) {
		newEvent.setDate(date);
		eventValidator.validate(newEvent, result);
		if(result.hasErrors()) {
			String userEmail = principal.getName();
			User currentUser = userService.findByEmail(userEmail);
			List<Event> mainEvents = eventService.findEventsInState(currentUser.getState());
			List<Event> otherEvents = eventService.findEventsOutState(currentUser.getState());
			model.addAttribute("mainEvents", mainEvents);
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("states", userService.stateList());
			model.addAttribute("otherEvents", otherEvents);
			return "dashboard.jsp";
		}
		String userEmail = principal.getName();
		newEvent.setHost(userService.findByEmail(userEmail));
		eventService.createEvent(newEvent);
		return "redirect:/events";
	}
	
	
	@RequestMapping(value="/events/join/{event_id}")
	public String joinEvent(@PathVariable("event_id") Long event_id, Principal principal) {
		String userEmail = principal.getName();
		User currentUser = userService.findByEmail(userEmail);
		eventService.joinEvent(event_id, currentUser);
		return "redirect:/events";
	}
	
	@RequestMapping(value="/events/leave/{event_id}")
	public String leaveEvent(@PathVariable("event_id") Long event_id, Principal principal) {
		String userEmail = principal.getName();
		User currentUser = userService.findByEmail(userEmail);
		eventService.leaveEvent(event_id, currentUser);
		return "redirect:/events";
	}
	
	@RequestMapping(value="/events/delete/{event_id}")
	public String destroyEvent(@PathVariable("event_id") Long event_id) {
		eventService.destroyEvent(event_id);
		return "redirect:/events";
	}
	
	@RequestMapping(value="/events/{event_id}")
	public String eventDetails(@PathVariable("event_id") Long event_id, @Valid @ModelAttribute("comment") Comment comment, Principal principal, Model model) {
		String userEmail = principal.getName();
		User currentUser = userService.findByEmail(userEmail);
		Event event = eventService.findEvent(event_id);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("event", event);
		return "eventDetails.jsp";
	}
	
	@PostMapping(value="/comments/new/{event_id}")
	public String createComment(@PathVariable("event_id") Long event_id, @Valid @ModelAttribute("comment") Comment comment, BindingResult result, Principal principal, Model model) {
		if(result.hasErrors()) {
			return "eventDetails.jsp";
		}
		User currentUser = userService.findByEmail(principal.getName());
		Event event = eventService.findEvent(event_id);
		comment.setUser(currentUser);
		comment.setEvent(event);
		eventService.addComment(event_id, currentUser, comment);
		return "redirect:/events/" + event_id;
	}
	
	@RequestMapping(value="/events/edit/{event_id}")
	public String editEvent(@PathVariable("event_id") Long event_id, Model model, Principal principal) {
		User currentUser = userService.findByEmail(principal.getName());
		Event event = eventService.findEvent(event_id);
		if(currentUser == event.getHost()) {
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("editEvent", event);
			model.addAttribute("states", userService.stateList());
			model.addAttribute("dateStr", event.getDateStr());
			return "editEvent.jsp";
		}else {
			return "redirect:/events";
		}	
	}
	
	@PostMapping(value="/events/edit/{editEventId}")
	public String updateEvent(@RequestParam("editDate") String dateStr, @PathVariable("editEventId") Long event_id, @Valid @ModelAttribute("editEvent") Event editEvent, BindingResult result, Model model, Principal principal) {
		editEvent.setDate(dateStr);
		eventValidator.validate(editEvent, result);
		if(result.hasErrors()) {
			User currentUser = userService.findByEmail(principal.getName());
			Event event = eventService.findEvent(event_id);
			if(currentUser == event.getHost()) {
				model.addAttribute("currentUser", currentUser);
				model.addAttribute("editEvent", event);
				model.addAttribute("states", userService.stateList());
				model.addAttribute("dateStr", event.getDateStr());
				return "editEvent.jsp";
			}else {
				return "redirect:/events";
			}	
		}

		eventService.updateEvent(event_id, editEvent);
		return "redirect:/events";
	}
}
