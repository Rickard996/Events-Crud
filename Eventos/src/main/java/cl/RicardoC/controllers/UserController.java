package cl.RicardoC.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.RicardoC.models.Event;
import cl.RicardoC.models.Message;
import cl.RicardoC.models.User;
import cl.RicardoC.services.EventService;
import cl.RicardoC.services.MessageService;
import cl.RicardoC.services.UserService;
import cl.RicardoC.validator.UserValidator;

@Controller
public class UserController {

	private UserValidator userValidator;
	private UserService userService;
	private EventService eventService;
	private MessageService messageService;
	
	public UserController(UserValidator userValidator, UserService userService, EventService eventService,
			MessageService messageService) {
		super();
		this.userValidator = userValidator;
		this.userService = userService;
		this.eventService = eventService;
		this.messageService = messageService;
	}
	
	//page of registration and login
	
	@RequestMapping(value = "/")
	public String home(@ModelAttribute("user") User user, HttpSession session) {
		return "homePage.jsp";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute(value = "user") User user, BindingResult result,
			HttpSession session) {
		
		session.setAttribute("success", "");
		this.userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "homePage.jsp";
		}else {
			this.userService.registerUser(user);
			session.setAttribute("success", "Se ha registrado Correctamente!!. Por favor vaya a Login.");  //Mensaje de exito
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, Model model, HttpSession session) {
				
		boolean authentication = this.userService.authenticateUser(email, password);
		if(authentication) {
			session.setAttribute("idUser", this.userService.findByEmail(email).getId());  
			//le paso el id a la siguiente vista
			return "redirect:/welcome";
		}else {
			session.setAttribute("error", "Las credenciales no corresponden, por favor intente nuevamente");
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomePage(HttpSession session, Model model) {
		
		Long id = (Long) session.getAttribute("idUser");
		// session.setAttribute("idUser", id);
		User user = this.userService.findUserById(id);
		List<Event> allEvents = this.eventService.findAll();
		List<Event> eventsInSameState = new ArrayList<Event>();
		List<Event> eventsInDifferentState = new ArrayList<Event>();
		
		for(Event event: allEvents) {
			if(event.getLocation().equals(user.getLocation())) {     
				eventsInSameState.add(event);
				
			}else {
				eventsInDifferentState.add(event);
			}
		}
		//POR AHORA EN VEZ DE STATES ESTOY VERIFICANDO POR LOCATION

		
		model.addAttribute("nameUser", user.getFirstName()+" "+user.getLastName());
		
		model.addAttribute("user", user);
		
		model.addAttribute("eventsSameState", eventsInSameState);
		model.addAttribute("eventosOtherState", eventsInDifferentState);
		
		return "welcomePage.jsp";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/event/new")
	public String createEvent(@Valid @ModelAttribute(value = "event") Event event, BindingResult result,
			HttpSession session, Model model) {
		
		Long id = (Long) session.getAttribute("idUser"); //al pasar cierto tiempo se pierde la session
		User user = this.userService.findUserById(id);
		System.out.println(event.getName());
		
		if(result.hasErrors()) {
			System.out.println("con errors");
			return "redirect:/welcome";
		}else {
			event.setHostName(user.getFirstName()+" "+user.getLastName());   //Luego usare esto
			this.eventService.saveAndUpdateEvent(event);  //primero se guarda el evento
			user.getEvents().add(event);  //luego se asigna al user
			this.userService.saveAndUpdate(user);    
			//se guarda el user (sin escribir uno nuevo) y se guarda el nuevo evento asociado a este user
		
			System.out.println("se creo evento correctamente");
			return "redirect:/welcome";
		}
		
	}
	
	@RequestMapping(value = "/events/{idEvent}", method = RequestMethod.GET)
	public String eventView(HttpSession session, Model model,
			@PathVariable(value = "idEvent") Long idEvent) {

		Event event = this.eventService.findEventById(idEvent);
		List<User> usersInEvent = event.getUsers();  //all the users in the event(including creator)
		String fullNameCreator = event.getHostName().trim();
		User userCreator = new User();  //User creator of the Event in the view
		
		for(User user: usersInEvent) {
			if((user.getFirstName()+user.getLastName()).trim().equals(fullNameCreator)) {
				userCreator = user;
				usersInEvent.remove(user);
			}
		}

		//usersInEvent.remove(user); //remuevo al creador del evento
		
		model.addAttribute("usersInEvent", usersInEvent);  //users que se unieron al event
		model.addAttribute("user", userCreator);  //usuario que creo el evento
		model.addAttribute("cantidadAsistentes", usersInEvent.size());
		model.addAttribute("event", event);  //el evento
		model.addAttribute("eventId", event.getId());
		
		List<Message> messagesInEvent = event.getMessages();
		model.addAttribute("messages", messagesInEvent);
		
		return "eventView.jsp";
		
	}
	
	//Añadir comment
	@RequestMapping(value = "/events/{idEvent}", method = RequestMethod.POST)
	public String addComment(@PathVariable(value = "idEvent") Long idEvent,
			@RequestParam(value = "subject") String subject, HttpSession session) {
		
		Long idUser = (Long) session.getAttribute("idUser");
		User user = this.userService.findUserById(idUser);   //user activo en la page (no el propietario del event)
		
		
		Event event = this.eventService.findEventById(idEvent);
		subject = user.getFirstName()+" "+user.getLastName()+": "+subject;
		
		//save new message 
		Message message = new Message();
		message.setSubject(subject);
		message.setEvent(event);
		event.getMessages().add(message);
		this.messageService.saveAndUpdateMessage(message);
		
		return "redirect:/events/"+idEvent;
		
	}
	
	@RequestMapping(value = "/delete/{idEvent}")
	public String deleteEvent(@PathVariable(value = "idEvent") Long idEvent, HttpSession session) {
		
		
		Event eventToDelete = this.eventService.findEventById(idEvent);
		
		Long idUser = (Long) session.getAttribute("idUser");
		User user = this.userService.findUserById(idUser);
		
		List<User> allUsers = this.userService.findAllUsers();
		for(User userx: allUsers) {
			if(userx.getEvents().contains(eventToDelete)) {
				userx.getEvents().remove(eventToDelete);  //primero remover desde la tabla intermedia. sino error 500
			}
		}
		
		this.eventService.deleteEvent(eventToDelete);   //finalmente puedo borrar el evento
		
		return "redirect:/welcome";
	}
	
	
	//Controlling Join y Cancel Event
	@RequestMapping(value = "/join/{idEvent}", method = RequestMethod.GET)
	public String joinToEvent(@PathVariable(value = "idEvent") Long idEvent, HttpSession session,
			Model model) {
		
		Event currentEvent = this.eventService.findEventById(idEvent);
		Long idUser = (Long) session.getAttribute("idUser");
		User user = this.userService.findUserById(idUser);
		
		if(!currentEvent.getUsers().contains(user)) {
			System.out.println("Entre a actualizar los users en el Event");
			user.getEvents().add(currentEvent);
			this.userService.saveAndUpdate(user);
		}
		
		
//		model.addAttribute("joining", true);
		
		return "redirect:/welcome";
	}
	
	@RequestMapping(value = "/cancelJoin/{idEvent}")
	public String cancelJoinEvent(@PathVariable(value = "idEvent") Long idEvent, HttpSession session,
			Model model) {
		
		Event currentEvent = this.eventService.findEventById(idEvent);
		Long idUser = (Long) session.getAttribute("idUser");
		User user = this.userService.findUserById(idUser);
		
		if(currentEvent.getUsers().contains(user)) {
			user.getEvents().remove(currentEvent);
			this.userService.saveAndUpdate(user);
		}
		
		return "redirect:/welcome";
	}
	
	@RequestMapping(value = "/events/{idEvent}/edit", method = RequestMethod.POST)
	public String editEvent(@PathVariable(value = "idEvent") Long idEvent,
			Model model, @RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "dateEvent", required = false) String dateEvent, 
			@RequestParam(value = "location", required = false) String location) {
		
		System.out.println("Entrando a edit");

		Event eventToUpdate = this.eventService.findEventById(idEvent);

		if(name.length()>0) {  //si no esta vacio ni es null
			eventToUpdate.setName(name);
		}
		
		if(dateEvent.length()>0) {
			try {
				Date updatedDate =new SimpleDateFormat("yyyy-MM-dd").parse(dateEvent);
				eventToUpdate.setDateEvent(updatedDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		
		if(location.length()>0) {  //si no esta vacio ni es null
			eventToUpdate.setLocation(location);
		}
		
		this.eventService.saveAndUpdateEvent(eventToUpdate);
		System.out.println("Se ha actualizado exitosamente el evento");
		
		return "redirect:/welcome";
		
	}
	
	@RequestMapping(value = "/events/{idEvent}/edit", method = RequestMethod.GET)
	public String editView(@PathVariable(value = "idEvent") Long idEvent, Model model, HttpSession session) {
		
		
		//User que desea editar
		Long idUser = (Long) session.getAttribute("idUser");
		User userEditer = this.userService.findUserById(idUser);
		
		String fullNameEditer = userEditer.getFirstName()+" "+userEditer.getLastName();
		Event eventToUpdate = this.eventService.findEventById(idEvent);
		
		//los trim no funcionan en este caso, OJO
		
		String fullNameHost = eventToUpdate.getHostName();
	
		if(fullNameEditer.equals(fullNameHost)) {   //si entra, accede a edition page, si no de vuelta a welcome
			model.addAttribute("idEvent", idEvent);
			model.addAttribute("event", eventToUpdate);
			
			return "editView.jsp";
		}else {
			return "redirect:/welcome";
		}
	}
	
	
	
	
	
}
