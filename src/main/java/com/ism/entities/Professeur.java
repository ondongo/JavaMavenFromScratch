package com.ism.entities;

import lombok.Data;

@Data
public class Professeur {

  private int id;
  private String nomComplet;
  private String adresse;
  private String telephone;
  private String grade;
  private int etat;
}
