package com.ism.entities;

import com.ism.entities.enums.EtatClasseEnum;
import com.ism.entities.enums.FiliereEnum;
import com.ism.entities.enums.NiveauEnum;
import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Classe {

  private int id;
  private String libelle;
  private String niveau;
  private String filiere;
  private int place;
  private String etat;
  private ArrayList<Module> modules = new ArrayList<>();

  public Classe() {}

  public Classe(String libelle, String niveau, String filiere, int place) {
    this.libelle = libelle;
    this.niveau = niveau;
    this.filiere = filiere;
    this.place = place;
    this.etat = EtatClasseEnum.OPEN.toString();
    this.modules = null;
  }

  @Override
  public String toString() {
    return (
      "Classe [id=" +
      id +
      ", libelle=" +
      libelle +
      ", niveau=" +
      niveau +
      ", filiere=" +
      filiere +
      ", place=" +
      place +
      ", etat=" +
      etat +
      "]"
    );
  }
}
