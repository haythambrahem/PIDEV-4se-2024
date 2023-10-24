/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.HashMap;
import java.util.UUID;
import tn.esprit.entity.Client;

/**
 *
 * @author hayth
 */
public class SessionManager {
     private static HashMap<String, Client> sessions = new HashMap<>();

    public static String createSession(Client client) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, client);
        return sessionId;
    }

    public static Client getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
     public static int loggedInPersonneId(String sessionId) {
        Client client = getSession(sessionId);
        if (client != null) {
            // Replace this with the actual method to get the `personne_id` from the Client object
            // You may need to adapt this to your data model
            return client.getId();
        }
        return -1; // Return -1 or some other value to indicate an error or no logged-in user
    }
    
}
