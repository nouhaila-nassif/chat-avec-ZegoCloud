package org.example.backendtest.services;

import org.example.backendtest.entities.Message;
import org.example.backendtest.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Enregistre un message dans la base de données.
     *
     * @param message Le message à enregistrer.
     * @return Le message enregistré.
     */
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Récupère tous les messages enregistrés.
     *
     * @return La liste de tous les messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Récupère les messages envoyés par un utilisateur spécifique.
     *
     * @param username Le nom d'utilisateur de l'expéditeur.
     * @return La liste des messages envoyés par l'utilisateur.
     */
    public List<Message> getMessagesByUser(String username) {
        return messageRepository.findBySender(username);
    }

    /**
     * Envoie un message.
     * Cette méthode peut inclure une logique supplémentaire comme des notifications ou des vérifications.
     *
     * @param message Le message à envoyer.
     * @return Le message envoyé et enregistré.
     */
    public Message sendMessage(Message message) {
        // Ajoutez ici une logique supplémentaire (exemple : validation ou notification).
        return messageRepository.save(message);
    }

    /**
     * Récupère tous les messages.
     * Implémentation alternative à `getAllMessages`.
     *
     * @return La liste de tous les messages.
     */
    public List<Message> getMessages() {
        return getAllMessages();
    }
}
