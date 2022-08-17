package cl.RicardoC.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.RicardoC.models.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long>{

	List<Event> findAllByHostName(String host);
	Optional<Event> findById(Long id);
	
}
