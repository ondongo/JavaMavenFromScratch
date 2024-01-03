package com.ism.repositories.bd.impl;

import com.ism.entities.Module;
import com.ism.entities.Professeur;
import com.ism.entities.ProfesseurClasse;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.ProfesseurClasseRepo;
import com.ism.repositories.core.Database;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfesseurClasseRepoImpl implements ProfesseurClasseRepo {

  private Database database;
  private ModuleRepo moduleRepository;
  private ClasseRepo classeRepository;
  private final String SQL_INSERT =
    "INSERT INTO `professeurclasse` (`idProfesseur`,`idClasse`,`modules`) VALUES (?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `professeurclasse` SET `idProfesseur`=?,`idClasse`=?,`modules`=? WHERE `id` = ?";
  private final String SQL_UPDATE_MODULES =
    "UPDATE `professeurclasse` SET `modules`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM `professeurclasse`  ";
  private final String SQL_DELETE =
    "DELETE FROM `professeurclasse` WHERE id = ?";
  private final String SQL_FIND_BY_ID =
    "SELECT * FROM `professeurclasse`WHERE idProfesseur = ? and idClasse = ?";
  private final String SQL_FIND_BY =
    "SELECT * FROM `professeurclasse` WHERE idProfesseur = ? ";

  public ProfesseurClasseRepoImpl(
    Database database,
    ModuleRepo moduleRepository,
    ClasseRepo classeRepository
  ) {
    this.database = database;
    this.moduleRepository = moduleRepository;
    this.classeRepository = classeRepository;
  }

  @Override
  public int insert(ProfesseurClasse data) {
    int nbrLigne = 0;
    try {
      ArrayList<String> idModules = new ArrayList<>();
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setInt(1, data.getIdProfesseur());
      database.getPs().setInt(2, data.getIdClasse());
      if (data.getModules() == null) {
        idModules.add("0");
      } else {
        for (Module module : data.getModules()) {
          idModules.add(module.getId() + "");
        }
      }
      database.getPs().setString(3, String.join(",", idModules));
      database.executeUpdate();
      ResultSet rs = database.getPs().getGeneratedKeys();
      if (!rs.next()) {
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
  public int update(ProfesseurClasse data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public ArrayList<ProfesseurClasse> findAll() {
    ArrayList<ProfesseurClasse> professeursClasses = new ArrayList<>();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);
      database.getPs();
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        ProfesseurClasse professeurclasse = new ProfesseurClasse();
        ArrayList<Module> allModules = new ArrayList<>();
        professeurclasse.setId(rs.getInt("id"));
        professeurclasse.setIdProfesseur(rs.getInt("idProfesseur"));
        professeurclasse.setIdClasse(rs.getInt("idClasse"));
        if (rs.getString("modules") == "0") {
          allModules = null;
        } else {
          ArrayList<String> modules = new ArrayList<>(
            Arrays.asList(rs.getString("modules").split(","))
          );

          for (String i : modules) {
            allModules.add(moduleRepository.findByID(Integer.valueOf(i)));
          }
        }
        professeurclasse.setModules(allModules);
        professeursClasses.add(professeurclasse);
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return professeursClasses;
  }

  @Override
  public ArrayList<ProfesseurClasse> findBy(int idProfesseur) {
    ArrayList<ProfesseurClasse> professeursClasses = new ArrayList<>();
    try {
      ArrayList<Module> allModules = new ArrayList<>();
      database.openConnection();
      database.InitializePs(SQL_FIND_BY);
      database.getPs().setInt(1, idProfesseur);
      database.getPs();
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        ProfesseurClasse professeurclasse = new ProfesseurClasse();
        professeurclasse.setId(rs.getInt("id"));
        professeurclasse.setIdProfesseur(rs.getInt("idProfesseur"));
        professeurclasse.setIdClasse(rs.getInt("idClasse"));
        if (rs.getString("modules") == "0") {
          allModules = null;
        } else {
          ArrayList<String> modules = new ArrayList<>(
            Arrays.asList(rs.getString("modules").split(","))
          );

          for (String i : modules) {
            allModules.add(moduleRepository.findByID(Integer.valueOf(i)));
          }
        }
        professeurclasse.setModules(allModules);

        professeursClasses.add(professeurclasse);
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return professeursClasses;
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

  @Override
  public ProfesseurClasse findByID(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
  }
}
