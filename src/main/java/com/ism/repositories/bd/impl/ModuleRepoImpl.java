package com.ism.repositories.bd.impl;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.core.Database;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ModuleRepoImpl implements ModuleRepo {

  private Database database;
  private final String SQL_INSERT =
    "INSERT INTO `module` (`libelle`,`heure`,`etat`) VALUES (?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `module` SET `libelle`=?,`heure`=?,`etat`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM module  ";
  private final String SQL_DELETE = "DELETE FROM `module` WHERE id = ?";
  private final String SQL_FIND_BY_ID = "SELECT * FROM `module` WHERE id = ?";

  public ModuleRepoImpl(Database database) {
    this.database = database;
  }

  @Override
  public int insert(Module data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setInt(2, data.getHeure());
      database.getPs().setInt(3, data.getEtat());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ModuleRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int update(Module data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_UPDATE);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setInt(2, data.getHeure());

      database.getPs().setInt(3, data.getEtat());
      database.getPs().setInt(4, data.getId());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'update %s", ModuleRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public ArrayList<Module> findAll() {
    ArrayList<Module> modules = new ArrayList<>();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);
      database.getPs();
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        Module data = new Module();
        data.setId(rs.getInt("id"));
        data.setLibelle(rs.getString("libelle"));
        data.setHeure(rs.getInt("heure"));
        data.setEtat(rs.getInt("etat"));
        if (data.getEtat() == 1) {
          modules.add(data);
        }
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ModuleRepoImpl.class);
    }
    System.out.println("Very good");
    return modules;
  }

  @Override
  public Module findByID(int id) {
    Module data = null;
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_BY_ID);
      database.getPs().setInt(1, id);
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        data = new Module();
        data.setId(rs.getInt("id"));
        data.setLibelle(rs.getString("libelle"));
        data.setHeure(rs.getInt("heure"));
        data.setEtat(rs.getInt("etat"));
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return data;
  }

  @Override
  public int delete(int id) {
    int nbrLigne = 0;

    try {
      database.openConnection();
      database.InitializePs(SQL_DELETE);
      database.getPs().setInt(1, id);
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'update %s", ModuleRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int indexOf(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
  }
}
