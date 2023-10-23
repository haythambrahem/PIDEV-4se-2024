package com.eventhub.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    String url = "jdbc:mysql://localhost:3306/eventhub_database";
    String user = "root";
    String pwd = "";

    Connection con;

    // Étape 3: Renommez la variable "instance" en "INSTANCE"
    private static MyDB INSTANCE;

    // Étape 2: Changez le nom de la méthode "getinstance" en "getInstance"
    public static MyDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyDB();
        }
        return INSTANCE;
    }

    private MyDB() {
        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connexion établie");
        } catch (SQLException ex) {
            System.out.println("Problème de connexion");
        }
    }

    public Connection getConnection() {
        return con;
    }
}