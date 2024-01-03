package com.ism.repositories.bd.impl;

import com.ism.entities.Module;
import com.ism.entities.Sale;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.SaleRepository;
import com.ism.repositories.core.Database;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SaleRepositoryImpl implements SaleRepository {

  private Database database;

  private final String SQL_INSERT =
    "INSERT INTO `sale` (`libelle`,`place`,`etat`) VALUES (?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `sale` SET `libelle`=?,`place`=?,`etat`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM sale  ";
  private final String SQL_DELETE = "DELETE FROM `sale` WHERE id = ?";
  private final String SQL_FIND_BY_ID = "SELECT * FROM `sale` WHERE id = ?";

  public SaleRepositoryImpl(Database database) {
    this.database = database;
  }

  @Override
  public int insert(Sale data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setInt(2, data.getPlace());
      database.getPs().setInt(3, data.getEtat());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ModuleRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int update(Sale data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_UPDATE);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setInt(2, data.getPlace());
      database.getPs().setInt(3, data.getEtat());
      database.getPs().setInt(4, data.getId());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ModuleRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public ArrayList<Sale> findAll() {
    ArrayList<Sale> sales = new ArrayList();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);
      database.getPs();
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        Sale data = new Sale();
        data.setId(rs.getInt("id"));
        data.setLibelle(rs.getString("libelle"));
        data.setPlace(rs.getInt("place"));
        data.setEtat(rs.getInt("etat"));
        if (data.getEtat() == 1) {
          sales.add(data);
        }
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", SaleRepositoryImpl.class);
    }
    System.out.println("Very good");
    return sales;
  }

  @Override
  public Sale findByID(int id) {
    Sale data = null;
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_BY_ID);
      database.getPs().setInt(1, id);
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        data = new Sale();
        data.setId(rs.getInt("id"));
        data.setLibelle(rs.getString("libelle"));
        data.setPlace(rs.getInt("place"));
        data.setEtat(rs.getInt("etat"));
      }

      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      //  System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    // System.out.println(data);
    return data;
  }

  @Override
  public int delete(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public int indexOf(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
  }
}
