package com.ism.entities;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Cours {

  private int id;
  private String code;
  private Date date;
  private Time heureDebut;
  private Time heureFin;
  private ArrayList<Classe> classes = new ArrayList<>();
  private int etat;
  private Module module;

  private Professeur professeur;
  private Sale sale;

  public Cours(
    String code,
    Date date,
    Time heureDebut,
    Time heureFin,
    Module module,
    Professeur professeur,
    Sale sale,
    ArrayList<Classe> classes
  ) {
    this.code = code;
    this.date = date;
    this.heureDebut = heureDebut;
    this.heureFin = heureFin;
    this.module = module;
    this.professeur = professeur;
    this.sale = sale;
    this.classes = classes;
    this.etat = 1;
  }

  public Cours(
    String code,
    Date date,
    Time heureDebut,
    Time heureFin,
    Module module,
    Professeur professeur,
    ArrayList<Classe> classes
  ) {
    this.code = code;
    this.date = date;
    this.heureDebut = heureDebut;
    this.heureFin = heureFin;
    this.module = module;
    this.professeur = professeur;
    this.classes = classes;
    this.sale = null;
    this.etat = 1;
  }

  public Cours() {}

  @Override
  public String toString() {
    return (
      "Cours [id=" +
      id +
      ", code=" +
      code +
      ", date=" +
      date +
      ", heureDebut=" +
      heureDebut +
      ", heureFin=" +
      heureFin +
      ", module=" +
      module +
      ", professeur=" +
      professeur +
      ", sale=" +
      sale +
      ", etat=" +
      etat +
      "]"
    );
  }
}
