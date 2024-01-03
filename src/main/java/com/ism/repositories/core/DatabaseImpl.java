package com.ism.repositories.core;

import java.sql.*;

public class DatabaseImpl implements Database {

  private final String driver;
  private final String url;
  private final String user = "root";
  private final String password = "root";
  private Connection conn = null;

  public DatabaseImpl(String driver, String url) {
    this.driver = driver;
    this.url = url;
  }

  @Override
  public PreparedStatement getPs() {
    return ps;
  }

  private PreparedStatement ps;

  @Override
  public void openConnection() {
    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, user, password);

      System.out.println("Ouverture de connexion a la bd");
    } catch (SQLException | ClassNotFoundException e) {
      System.out.printf(
        "Erreur de chargement de driver %s",
        DatabaseImpl.class
      );
    }
  }

  @Override
  public void closeConnexion() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        System.out.println("Erreur de Fermeture de connexion");
      }
    }
  }

  @Override
  public PreparedStatement InitializePs(String sql) {
    try {
      ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      //System.out.println("PreparedStatement is initialized");

    } catch (Exception e) {
      System.out.println("Erreur d'Initialisation du PS");
    }
    return ps;
  }

  @Override
  public int executeUpdate() {
    int nbrLigne = 0;
    // System.out.println("excute update lancer");
    try {
      //System.out.println("testtttttttttt");
      nbrLigne = ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println(
        "Erreur de execution request update: " + e.getMessage()
      );
      e.printStackTrace(); // Ajoutez cette ligne pour imprimer la trace complète de l'exception
    }

    return nbrLigne;
  }

  @Override
  public ResultSet executeSelect() {
    ResultSet rs = null;
    try {
      rs = ps.executeQuery();
    } catch (SQLException e) {
      System.out.println("Erreur de execution request");
    }
    return rs;
  }
}
