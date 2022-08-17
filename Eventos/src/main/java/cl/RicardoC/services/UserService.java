package cl.RicardoC.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import cl.RicardoC.models.User;
import cl.RicardoC.repositories.UserRepo;

@Service
public class UserService {

	private UserRepo userRepo;

	public UserService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	// registrar el usuario y hacer Hash a su password
	
	public User registerUser(User user) {
		
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);  //guarda password ya encriptada
		return this.userRepo.save(user);
	}
	
	public User findByEmail(String email) {
		return this.userRepo.findByEmail(email);
	}
	
	public User findByFirstName(String firstName) {
		return this.userRepo.findByFirstName(firstName);
	}
	
	public User findByLastName(String lastName) {
		return this.findByLastName(lastName);
	}
	
	
	// encontrar un usuario por su id
	
	public User findUserById(Long id){
		
		Optional<User> user = this.userRepo.findById(id);
		
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	// autenticar usuario. usamos los metodos anteriores
	
	public boolean authenticateUser(String email, String password) {
		//primero encontremos al user
		User user = this.findByEmail(email);
		
		if(user==null) {
			return false; //false significa que fallo la autenthication
		}else {
			//checkea password
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	public User saveAndUpdate(User user) {
		return this.userRepo.save(user);
	}
	
	public List<User> findAllUsers(){
		return (List<User>) this.userRepo.findAll();
	}
	
	
	
	
}
