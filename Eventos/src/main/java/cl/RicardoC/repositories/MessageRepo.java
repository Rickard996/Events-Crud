package cl.RicardoC.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.RicardoC.models.Message;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long>{

}
