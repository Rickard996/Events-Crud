package cl.RicardoC.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.RicardoC.models.Message;
import cl.RicardoC.repositories.MessageRepo;

@Service
public class MessageService {

	private MessageRepo messageRepo;

	public MessageService(MessageRepo messageRepo) {
		super();
		this.messageRepo = messageRepo;
	}
	
	public List<Message> findAll(){
		return (List<Message>) this.messageRepo.findAll();
	}
	
	public void saveAndUpdateMessage(Message message) {
		this.messageRepo.save(message);
	}
	
	
	
}
