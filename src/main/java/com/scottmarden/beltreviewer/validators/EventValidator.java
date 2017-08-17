package com.scottmarden.beltreviewer.validators;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.scottmarden.beltreviewer.models.Event;

@Component
public class EventValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		Event event = (Event) object;
		
		Date now = new Date();
		
		if(now.after(event.getDate())) {
			errors.rejectValue("date", "Match");
		}
	}
	
}
