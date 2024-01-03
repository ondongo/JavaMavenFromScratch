package com.ism.services.interf;

import java.util.ArrayList;

import com.ism.entities.Classe;
import com.ism.entities.Cours;
import com.ism.entities.Professeur;
import com.ism.entities.ProfesseurClasse;

public interface ProfesseurService extends Service<Professeur>{
    int addClasseModule(ProfesseurClasse value);
    ArrayList<ProfesseurClasse> getClasseByPro(int id);
    ArrayList<Professeur> getProfesseursByModule(int idM);
    ArrayList<Classe> getClassesByProfesseurByModule(int idM,int idProfesseur);
   
}
