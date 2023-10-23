// ServiceAmi.java
package com.eventhub.services;

import com.eventhub.entities.Ami;
import com.eventhub.entities.User;
import com.eventhub.tools.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceAmi implements AmiService {
    private Connection connection;

    public ServiceAmi() {
        this.connection = MyDB.getInstance().getConnection(); // Utilisation de getInstance
    }
    @Override
    public boolean ajouterAmi(Ami ami) {
        String query = "INSERT INTO amis (idAmi, idUtilisateur) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, ami.getIdAmi());
            preparedStatement.setLong(2, ami.getUser().getId());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
public List<Ami> listerAmis(User utilisateur) {
    List<Ami> amis = new ArrayList<>();
    String query = "SELECT * FROM amis WHERE idUtilisateur = ?";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, utilisateur.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long idAmi = resultSet.getLong("idAmi");
            Ami ami = fetchAmiById(idAmi);
            amis.add(ami);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return amis;
}


   @Override
public List<Ami> rechercherAmiParNom(String nomRecherche, User utilisateurConnecte) {
    List<Ami> amisTrouves = new ArrayList<>();
    String query = "SELECT amis.idAmi, amis.idUtilisateur " +
                   "FROM amis " +
                   "INNER JOIN users ON amis.idUtilisateur = users.id " +
                   "WHERE (users.first_name = ? OR users.last_name = ?) " +
                   "AND amis.idUtilisateur = ?";

    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, nomRecherche);
        preparedStatement.setString(2, nomRecherche);
        preparedStatement.setLong(3, utilisateurConnecte.getId());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long idAmi = resultSet.getLong("idAmi");
            long idUtilisateur = resultSet.getLong("idUtilisateur");
            User amiUtilisateur = fetchUtilisateurById(idUtilisateur);
            Ami ami = new Ami(idAmi, amiUtilisateur);
            amisTrouves.add(ami);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return amisTrouves;
}



    @Override
    public boolean supprimerInvitation(User utilisateur, Ami ami) {
        String query = "DELETE FROM amis WHERE idUtilisateur = ? AND idAmi = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, utilisateur.getId());
            preparedStatement.setLong(2, ami.getIdAmi());
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void actualiserListeAmis(User utilisateurConnecte) {
        String query = "SELECT * FROM amis WHERE idUtilisateur = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, utilisateurConnecte.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long idAmi = resultSet.getLong("idAmi");
                long idUtilisateur = resultSet.getLong("idUtilisateur");
                User amiUtilisateur = fetchUtilisateurById(idUtilisateur);
                Ami ami = new Ami(idAmi, amiUtilisateur);
                utilisateurConnecte.getAmis().add(ami);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User fetchUtilisateurById(long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User utilisateur = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long idUtilisateur = resultSet.getLong("id");
                String first_name = resultSet.getString("first_name");
                String Last_name = resultSet.getString("Last_name");
                utilisateur = new User(idUtilisateur, first_name, Last_name, "", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
@Override
public boolean accepterInvitation(User utilisateur, Ami ami) {
    String query = "INSERT INTO amis (idAmi, idUtilisateur) VALUES (?, ?)";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, ami.getIdAmi());
        preparedStatement.setLong(2, utilisateur.getId());
        int rowsInserted = preparedStatement.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
@Override
public boolean rejeterInvitation(User utilisateur, Ami ami) {
    String query = "DELETE FROM amis WHERE idAmi = ? AND idUtilisateur = ?";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, ami.getIdAmi());
        preparedStatement.setLong(2, utilisateur.getId());
        int rowsDeleted = preparedStatement.executeUpdate();
        return rowsDeleted > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
@Override
public List<Ami> listerInvitations(User utilisateur) {
    List<Ami> invitations = new ArrayList<>();
    String query = "SELECT * FROM amis WHERE idUtilisateur = ? AND etat = 'en_attente'";
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, utilisateur.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long idAmi = resultSet.getLong("idAmi");
            Ami ami = fetchAmiById(idAmi);
            invitations.add(ami);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return invitations;
}


    private Ami fetchAmiById(long id) {
        String query = "SELECT * FROM amis WHERE idAmi = ?";
        Ami ami = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long idAmi = resultSet.getLong("idAmi");
                long idUtilisateur = resultSet.getLong("idUtilisateur");
                User amiUtilisateur = fetchUtilisateurById(idUtilisateur);
                ami = new Ami(idAmi, amiUtilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ami;
    }
}