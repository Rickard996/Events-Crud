package cl.RicardoC.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.RicardoC.models.Event;
import cl.RicardoC.repositories.EventRepo;


@Service
public class EventService{

	private EventRepo eventRepo;

	public EventService(EventRepo eventRepo) {
		super();
		this.eventRepo = eventRepo;
	}
	
	//save or update my event
	public Event saveAndUpdateEvent(Event event) {
		return this.eventRepo.save(event);
	}
	
	public List<Event> findAll(){
		return (List<Event>) this.eventRepo.findAll();
	}
	
	// host es First Name +" "+ Last Name
	public List<Event> findAllByHost(String host){
		return this.eventRepo.findAllByHostName(host);
	}
	
	public Event findEventById(Long id) {
		
		Optional<Event> event = this.eventRepo.findById(id);
		if(event.isPresent()) {
			return event.get();
		}else {
			return null;
		}
	}
	
	public void deleteEvent(Event event) {
		this.eventRepo.delete(event);
	}
	
	
	
	
	
	
}
