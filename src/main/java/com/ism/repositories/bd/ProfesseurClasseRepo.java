package com.ism.repositories.bd;

import com.ism.entities.Classe;
import com.ism.entities.ProfesseurClasse;
import com.ism.repositories.core.Repository;
import java.util.ArrayList;

public interface ProfesseurClasseRepo extends Repository<ProfesseurClasse> {
  ArrayList<ProfesseurClasse> findBy(int idProfesseur);
}
