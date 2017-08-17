package com.scottmarden.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scottmarden.beltreviewer.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{
	
	@Query("SELECT e FROM Event e WHERE e.state = ?1")
	List<Event> findEventsInState(String state);
	
	@Query("SELECT e FROM Event e WHERE e.state != ?1")
	List<Event> findEventsOutState(String state);

}
