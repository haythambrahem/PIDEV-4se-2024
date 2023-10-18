/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tests;

import tn.esprit.entity.Evenement;
import tn.esprit.services.ServicePersonne;
import tn.esprit.entity.Personne;
import tn.esprit.entity.Reservation;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceReservation;

/**
 *
 * @author Fayechi
 */
public class Workshop_JDBC_4SE1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Personne p1 = new Personne(1,"ibtihel","ben mustfa");
//        Personne p2 = new Personne(1,"Anis","fetoui");
//        Personne p3 = p1;
//        System.out.println(p1);
//       
//        
//       ServicePersonne sp = new ServicePersonne();
//       sp.ajouter(p1);
//       sp.ajouter(p2);
//       
//       System.out.println(sp.affihcer());
//       Evenement e1 = new Evenement("a","b","c","d","e","f","g");
         Evenement e2 = new Evenement("Danse","OKLM","+boissons","16h","Minuit","Rafraf","Evènement Public");
//       System.out.println(e1);
//       System.out.println(e2);
//       System.out.println(e3);
         ///////////RESERVATION//////
         Reservation reservation = new Reservation();
          reservation.setIdEvt(1); 
    reservation.setTitreEvt("Foire aux Livres"); // Set the event title
    reservation.setPrixBillet(12.00f); // Set the price of the ticket
   try {
        // Step 2: Create an instance of ServiceReservation
        ServiceReservation serviceReservation = new ServiceReservation();
        serviceReservation.ajouterRes(reservation);

        System.out.println("Reservation added successfully.");
    } catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }      
      

////EVENEMENTTTTTTT//

ServiceEvenement se = new ServiceEvenement();
       
//       se.ajouterEv(e1);
//       se.ajouterEv(e2);
//       se.ajouterEv(e3);
        
        // Create an Evenement object with the existing ID you want to modify
        Evenement ev = new Evenement();
//        ev.setIdEvt(98); // Replace with the correct ID 
//        // Call modifierEv to update the event attributes
//        se.modifierEv("Siwar", "pi", "Rihab", "New Start Time", "New End Time", "New Address", "New Type", ev);
//        
        



////////SUPPRESSION 
////         // Create an Evenement object with the ID you want to delete
////        Evenement ev = new Evenement();
////        ev.setIdEvt(2); // Replace with the correct ID
////
////        // Call supprimerEv to delete the event
////        se.supprimerEv(ev); 
////
////        System.out.println("Event deleted successfully.");
////        // Print the list of events to check if the modification was successful
////        System.out.println(se.afficherEv());
//////        se.supprimerEv();

    }
}