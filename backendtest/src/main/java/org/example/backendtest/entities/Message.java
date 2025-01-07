package org.example.backendtest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Définir la clé étrangère user_id
    private User user;  // La propriété user fait la liaison avec l'entité User

    public Message() {
    }

    public Message(String sender, String content, Long timestamp, User user) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.user = user;  // Ajouter l'utilisateur pour lier le message à un utilisateur
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
