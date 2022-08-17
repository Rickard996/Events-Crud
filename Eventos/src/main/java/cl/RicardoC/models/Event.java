package cl.RicardoC.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "events")
public class Event {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 4, message = "Name must be greater than 4 characters")
	private String name;
	@Size(min = 5, message = "Location must be greater than 4 characters")
	private String location;
	private String hostName;  //nombre del dueño del evento
	@NotNull(message = "Date of the event must not be null")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateEvent;
	
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
	private List<Message> messages;   //messages in the wall of the event
	
	@ManyToMany(mappedBy = "events")
	private List<User> users;

	public Event() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public Date getDateEvent() {
		return dateEvent;
	}


	public void setDateEvent(Date dateEvent) {
		this.dateEvent = dateEvent;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public List<Message> getMessages() {
		return messages;
	}


	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
	
	
}
