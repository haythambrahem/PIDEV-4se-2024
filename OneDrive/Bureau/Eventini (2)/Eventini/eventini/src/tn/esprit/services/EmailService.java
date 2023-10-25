/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import tn.esprit.entity.Personne;
/**
 *
 * @author imen
 */
public class EmailService {
     private static final String EMAIL = "imen.thmayni@esprit.tn";
    private static final String PASSWORD = "223JFT5608";

    public static void sendConfirmationEmail(Personne personne) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress(EMAIL));
            email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(personne.getEmail()));
            email.setSubject("Confirmation de panier");
            email.setText("Cher " + personne.getPrenom() + ",\n\nVotre panier a été confirmé avec succès.\n\nCordialement, VotreApplication");

            Transport.send(email);
            System.out.println("E-mail de confirmation envoyé avec succès à " + personne.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'envoi de l'e-mail de confirmation : " + e.getMessage());
        }
    }

    
}
