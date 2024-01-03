package com.ism.entities;

import java.util.ArrayList;
import lombok.Data;

@Data
public class ProfesseurClasse {

  private int id;
  private int idProfesseur;
  private int idClasse;
  private ArrayList<Module> modules = new ArrayList<>();
}
