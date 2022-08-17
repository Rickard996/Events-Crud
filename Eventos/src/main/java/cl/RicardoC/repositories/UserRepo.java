package cl.RicardoC.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.RicardoC.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{

	User findByEmail(String email);   //encuentra el user por su email(para hacer validation en el login)
	User findByFirstName(String firstName);
	User findByLastName(String lastName);
}
