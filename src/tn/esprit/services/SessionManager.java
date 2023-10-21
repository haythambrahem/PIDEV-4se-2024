/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import com.sun.deploy.util.SessionState.Client;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author fadi saidi
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
    
    
}
