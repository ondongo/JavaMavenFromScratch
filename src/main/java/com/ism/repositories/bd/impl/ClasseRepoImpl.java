package com.ism.repositories.bd.impl;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.entities.enums.EtatClasseEnum;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.core.Database;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClasseRepoImpl implements ClasseRepo {

  private Database database;
  private ModuleRepo moduleRepository;

  private final String SQL_INSERT =
    "INSERT INTO `classe` (`libelle`,`niveau`,`filiere`,`place`,`etat`,`modules`) VALUES (?,?,?,?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `classe` SET `libelle`=?,`niveau`=?,`filiere`=?,`place`=?,`etat`=? WHERE `id` = ?";
  private final String SQL_UPDATE_MODULES =
    "UPDATE `classe` SET `modules`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM `classe`";
  private final String SQL_DELETE = "DELETE FROM `personne` WHERE id = ?";
  private final String SQL_FIND_BY_ID = "SELECT * FROM `classe` WHERE id = ?";

  public ClasseRepoImpl(Database database, ModuleRepo moduleRepository) {
    this.database = database;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public int insert(Classe data) {
    int nbrLigne = 0;
    try {
      ArrayList<String> idModules = new ArrayList<>();
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setString(2, data.getNiveau());
      database.getPs().setString(3, data.getFiliere());
      database.getPs().setInt(4, data.getPlace());
      database.getPs().setString(5, data.getEtat());
      if (data.getModules() == null) {
        idModules.add("0");
      } else {
        for (Module module : data.getModules()) {
          idModules.add(module.getId() + "");
        }
      }

      database.getPs().setString(6, String.join(",", idModules));
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int addModulesInClasse(ArrayList<Module> modules, Classe classe) {
    ArrayList<String> idModules = new ArrayList<>();
    ArrayList<Module> allModules = new ArrayList<>();
    for (Module mod : classe.getModules()) {
      if (mod != null) {
        allModules.add(mod);
      }
    }
    for (Module mod : modules) {
      if (mod != null) {
        allModules.add(mod);
      }
    }

    for (Module module : allModules) {
      idModules.add(module.getId() + "");
    }
    int nbrLigne = 0;

    try {
      database.openConnection();
      database.InitializePs(SQL_UPDATE_MODULES);
      database.getPs().setString(1, String.join(",", idModules));
      database.getPs().setInt(2, classe.getId());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'update %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int update(Classe data) {
    int nbrLigne = 0;
    try {
      database.openConnection();
      database.InitializePs(SQL_UPDATE);
      database.getPs().setString(1, data.getLibelle());
      database.getPs().setString(2, data.getNiveau());
      database.getPs().setString(3, data.getFiliere());
      database.getPs().setInt(4, data.getPlace());
      database.getPs().setString(5, data.getEtat());
      database.getPs().setInt(6, data.getId());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'update %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public ArrayList<Classe> findAll() {
    ArrayList<Classe> classes = new ArrayList<>();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);
      database.getPs();
      ResultSet rs = database.executeSelect();

      while (rs.next()) {
        Classe classe = new Classe();
        ArrayList<Module> allModules = new ArrayList<>();
        classe.setId(rs.getInt("id"));
        classe.setLibelle(rs.getString("libelle"));
        classe.setNiveau(rs.getString("niveau"));
        classe.setFiliere(rs.getString("filiere"));
        classe.setPlace(rs.getInt("place"));
        classe.setEtat(rs.getString("etat"));

        if ("0".equals(rs.getString("modules"))) {
          allModules = null;
        } else {
          ArrayList<String> modules = new ArrayList<>(
            Arrays.asList(rs.getString("modules").split(","))
          );

          for (String i : modules) {
            allModules.add(moduleRepository.findByID(Integer.valueOf(i)));
          }
        }
        classe.setModules(allModules);

        if (classe.getEtat().equals(EtatClasseEnum.OPEN.toString())) {
          classes.add(classe);
        }
      }

      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur d'affichage %s", ClasseRepoImpl.class);
    }
    return classes;
  }

  @Override
  public Classe findByID(int id) {
    Classe classe = null;
    try {
      ArrayList<Module> allModules = new ArrayList<>();
      database.openConnection();
      database.InitializePs(SQL_FIND_BY_ID);
      database.getPs().setInt(1, id);
      ResultSet rs = database.executeSelect();
      while (rs.next()) {
        classe = new Classe();
        classe.setId(rs.getInt("id"));
        classe.setLibelle(rs.getString("libelle"));
        classe.setNiveau(rs.getString("niveau"));
        classe.setFiliere(rs.getString("filiere"));
        classe.setPlace(rs.getInt("place"));
        classe.setEtat(rs.getString("etat"));
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
        classe.setModules(allModules);
      }
      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.printf("Erreur de find by id %s", ClasseRepoImpl.class);
    }
    return classe;
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
      System.out.printf("Erreur d'update %s", ClasseRepoImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int indexOf(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
  }

  @Override
  public ArrayList<Module> getModulesForClasse(Classe classe) {
    return classe.getModules();
  }

  @Override
  public ArrayList<Classe> getClassesByModule(Module module) {
    ArrayList<Classe> classes = findAll();
    ArrayList<Classe> AllClasse = new ArrayList<>();
    for (Classe classe : classes) {
      ArrayList<Module> modules = classe.getModules();
      for (Module mod : modules) {
        if (module.getId() == mod.getId()) {
          AllClasse.add(classe);
        }
      }
    }

    return AllClasse;
  }
}
