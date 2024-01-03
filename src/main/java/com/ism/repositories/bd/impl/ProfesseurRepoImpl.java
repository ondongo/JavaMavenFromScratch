package com.ism.repositories.bd.impl;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.entities.Professeur;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.ProfesseurRepo;
import com.ism.repositories.core.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfesseurRepoImpl implements ProfesseurRepo {

  private Database database;
  // private ModuleRepo moduleRepository;
  // private ClasseRepo classeRepository;

  private final String SQL_INSERT =
    "INSERT INTO `professeur` (`nomComplet`,`adresse`,`telephone`,`grade`,`etat`) VALUES (?,?,?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `professeur` SET `nomComplet`=?,`adresse`=?,`telephone`=?,`grade`=?,`etat`=? WHERE `id` = ?";
  private final String SQL_UPDATE_MODULES =
    "UPDATE `classe` SET `modules`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM professeur  ";
  private final String SQL_DELETE = "DELETE FROM `profeseur` WHERE id = ?";
  private final String SQL_FIND_BY_ID =
    "SELECT * FROM `professeur` WHERE id = ?";

  public ProfesseurRepoImpl(Database database) {
    this.database = database;
    // this.moduleRepository = moduleRepository;
    // this.classeRepository = classeRepository;
  }

  @Override
  public int insert(Professeur data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setString(1, data.getNomComplet());
      database.getPs().setString(2, data.getAdresse());
      database.getPs().setString(3, data.getTelephone());
      database.getPs().setString(4, data.getGrade());
      database.getPs().setInt(5, data.getEtat());
      database.getPs().setInt(6, data.getId());
      System.out.println(nbrLigne);
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int update(Professeur data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_UPDATE);
      database.getPs().setString(1, data.getNomComplet());
      database.getPs().setString(2, data.getAdresse());
      database.getPs().setString(3, data.getTelephone());
      database.getPs().setString(4, data.getGrade());
      database.getPs().setInt(5, data.getEtat());
      System.out.println(nbrLigne);
      nbrLigne = database.executeUpdate();
      ResultSet rs = database.getPs().getGeneratedKeys();
      if (rs.next()) {
        System.out.println("BIEN");
        nbrLigne = rs.getInt(1);
        System.out.println(nbrLigne);
      }
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public ArrayList<Professeur> findAll() {
    ArrayList<Professeur> professeurs = new ArrayList<>();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);

      database.getPs();
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        Professeur professeur = new Professeur();
        professeur.setId(rs.getInt("id"));
        professeur.setNomComplet(rs.getString("nomComplet"));
        professeur.setAdresse(rs.getString("adresse"));
        professeur.setTelephone(rs.getString("telephone"));
        professeur.setGrade(rs.getString("grade"));
        professeur.setEtat(rs.getInt("etat"));
        if (professeur.getEtat() == 1) {
          professeurs.add(professeur);
        }
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return professeurs;
  }

  @Override
  public Professeur findByID(int id) {
    Professeur professeur = null;
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_BY_ID);
      database.getPs();
      database.getPs().setInt(1, id);
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        professeur = new Professeur();
        professeur.setId(rs.getInt("id"));
        professeur.setNomComplet(rs.getString("nomComplet"));
        professeur.setAdresse(rs.getString("adresse"));
        professeur.setTelephone(rs.getString("telephone"));
        professeur.setGrade(rs.getString("grade"));
        professeur.setEtat(rs.getInt("etat"));
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return professeur;
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
