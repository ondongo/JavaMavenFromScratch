package com.ism.repositories.bd.impl;

import com.ism.entities.Classe;
import com.ism.entities.Cours;
import com.ism.entities.Module;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.CoursRepository;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.ProfesseurClasseRepo;
import com.ism.repositories.bd.ProfesseurRepo;
import com.ism.repositories.bd.SaleRepository;
import com.ism.repositories.core.Database;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class CoursRepositoryImpl implements CoursRepository {

  private Database database;
  private ClasseRepo classeRepository;
  private ModuleRepo moduleRepository;
  private ProfesseurRepo professeurRepository;
  private SaleRepository saleRepository;
  private ProfesseurClasseRepo professeurClasseRepository;

  private final String SQL_INSERT =
    "INSERT INTO `cours` (`code`,`date`,`heureDebut`,`heureFin`,`idModule`,`idProfesseur`,`idSale`,`classes`,`etat`) VALUES (?,?,?,?,?,?,?,?,?)";
  private final String SQL_UPDATE =
    "UPDATE `classe` SET `libelle`=?,`niveau`=?,`filiere`=?,`place`=?,`etat`=? WHERE `id` = ?";
  private final String SQL_UPDATE_MODULES =
    "UPDATE `cours` SET `modules`=? WHERE `id` = ?";
  private final String SQL_FIND_ALL = "SELECT * FROM cours  ";
  private final String SQL_DELETE = "DELETE FROM `cours` WHERE id = ?";
  private final String SQL_FIND_BY_ID = "SELECT * FROM `cours` WHERE id = ?";

  public CoursRepositoryImpl(
    Database database,
    ClasseRepo classeRepository,
    ModuleRepo moduleRepository,
    ProfesseurRepo professeurRepository,
    SaleRepository saleRepository,
    ProfesseurClasseRepo professeurClasseRepository
  ) {
    this.database = database;
    this.classeRepository = classeRepository;
    this.moduleRepository = moduleRepository;
    this.professeurRepository = professeurRepository;
    this.saleRepository = saleRepository;
    this.professeurClasseRepository = professeurClasseRepository;
  }

  @Override
  public int insert(Cours data) {
    int nbrLigne = 0;
    try {
      ArrayList<String> idClasses = new ArrayList<>();
      int idSale;
      database.openConnection();
      database.InitializePs(SQL_INSERT);
      database.getPs().setString(1, data.getCode());
      database.getPs().setDate(2, data.getDate());
      database.getPs().setTime(3, data.getHeureDebut());
      database.getPs().setTime(4, data.getHeureFin());
      database.getPs().setInt(5, data.getModule().getId());
      database.getPs().setInt(6, data.getProfesseur().getId());
      if (data.getSale() == null) {
        idSale = 0;
      } else {
        idSale = data.getSale().getId();
      }
      database.getPs().setInt(7, idSale);

      for (Classe classe : data.getClasses()) {
        idClasses.add(classe.getId() + "");
      }
      database.getPs().setString(8, String.join(",", idClasses));
      database.getPs().setInt(9, data.getEtat());
      nbrLigne = database.executeUpdate();
      database.closeConnexion();
    } catch (Exception e) {
      System.out.printf("Erreur d'insertion %s", CoursRepositoryImpl.class);
    }
    return nbrLigne;
  }

  @Override
  public int update(Cours data) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByID'");
  }

  @Override
  public ArrayList<Cours> findAll() {
    ArrayList<Cours> cours = new ArrayList<>();
    try {
      database.openConnection();
      database.InitializePs(SQL_FIND_ALL);
      database.getPs();
      ResultSet rs = database.executeSelect();

      while (rs.next()) {
        Cours data = new Cours();
        ArrayList<Classe> allClasses = new ArrayList<>();
        data.setId(rs.getInt("id"));
        data.setCode(rs.getString("code"));
        data.setDate(rs.getDate("date"));
        data.setHeureDebut(rs.getTime("heureDebut"));
        data.setHeureFin(rs.getTime("heureFin"));
        data.setModule(moduleRepository.findByID(rs.getInt("idModule")));
        data.setProfesseur(
          professeurRepository.findByID(rs.getInt("idProfesseurfesseuresseur"))
        );
        data.setSale(saleRepository.findByID(rs.getInt("idSale")));
        data.setEtat(rs.getInt("etat"));

        if (rs.getString("classes") == "0") {
          allClasses = null;
        } else {
          ArrayList<String> classess = new ArrayList<>(
            Arrays.asList(rs.getString("classes").split(","))
          );
          for (String i : classess) {
            allClasses.add(classeRepository.findByID(Integer.valueOf(i)));
          }
        }
        data.setClasses(allClasses);
        if (data.getEtat() == 1) {
          cours.add(data);
        }
      }

      database.closeConnexion();
      rs.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      // System.out.printf("Erreur d'affichage %s", CoursRepositoryImpl.class);
    }
    return cours;
  }

  @Override
  public Cours findByID(int id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByID'");
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
