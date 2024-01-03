package com.ism.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class Module {

  private int id;
  private String libelle;
  private int heure;
  private int etat;

  public Module(String libelle, int heure) {
    this.libelle = libelle;
    this.heure = heure;
    this.etat = 1;
  }

  public Module() {}
}
