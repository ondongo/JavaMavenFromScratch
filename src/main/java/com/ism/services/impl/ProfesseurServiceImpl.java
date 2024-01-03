package com.ism.services.impl;

import java.util.ArrayList;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.entities.Professeur;
import com.ism.entities.ProfesseurClasse;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.ProfesseurClasseRepo;
import com.ism.repositories.bd.ProfesseurRepo;
import com.ism.services.interf.ProfesseurService;

public class ProfesseurServiceImpl implements ProfesseurService{
    private ProfesseurRepo professeurRepo;
    private ProfesseurClasseRepo profClasseRepo;
    private ClasseRepo classeRepo;
    public ProfesseurServiceImpl(ProfesseurRepo professeurRepo,ProfesseurClasseRepo professeurClasseRepo,ClasseRepo classeRepo) {
        this.professeurRepo = professeurRepo;
        this.profClasseRepo= professeurClasseRepo;
        this.classeRepo = classeRepo;
    }

    @Override
    public int add(Professeur value) {
      return   professeurRepo.insert(value);
    }

    @Override
    public ArrayList<Professeur> getAll() {
        return   professeurRepo.findAll();
    }

    @Override
    public int update(Professeur value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Professeur show(int id) {
        return professeurRepo.findByID(id);
    }

    @Override
    public int remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public int addClasseModule(ProfesseurClasse value) {
        return profClasseRepo.insert(value);
    }

    @Override
    public ArrayList<ProfesseurClasse> getClasseByPro(int id) {
        return profClasseRepo.findBy(id);
    }

    @Override
    public ArrayList<Professeur> getProfesseursByModule(int idM) {
       ArrayList<Professeur> professeurs =   new ArrayList<>();
       ArrayList<Module> modules = null;
       ArrayList<ProfesseurClasse> professeurClasse = profClasseRepo.findAll();
       for (ProfesseurClasse profClasse : professeurClasse) {
             
                 for (Module module: profClasse.getModules()) {
                    if (module.getId()==idM ) {
                        int vrai=0;
                        for (Professeur professeur : professeurs) {
                            if(professeur.getId()==profClasse.getIdProfesseur()){
                                vrai=1;
                            }
                        }
                        if (vrai==0) {
                              professeurs.add(professeurRepo.findByID(profClasse.getIdProfesseur()));
                        }
                        
                   }
                 }
       }
        return professeurs;
    }

    @Override
    public ArrayList<Classe> getClassesByProfesseurByModule(int idM, int idProfesseur) {
        ArrayList<Classe> classes = new ArrayList<>();
       ArrayList<Module> modules = null;
       ArrayList<ProfesseurClasse> professeurClasse = profClasseRepo.findAll();
       for (ProfesseurClasse profClasse : professeurClasse) {
                 for (Module module: profClasse.getModules()) {
                    if (module.getId()==idM && profClasse.getIdProfesseur()==idProfesseur) {
                          classes.add(classeRepo.findByID(profClasse.getIdClasse()));
                   }
                 }
       }
        return classes;
    }
    
}
