package com.ism.services.impl;

import java.util.ArrayList;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.ProfesseurClasseRepo;
import com.ism.services.interf.ClasseService;

public class ClasseServiceImpl implements ClasseService{

    private ClasseRepo classeRepository;
    private ProfesseurClasseRepo professeurClasseRepository;
    
    public ClasseServiceImpl(ClasseRepo classeRepository) {
        this.classeRepository = classeRepository;
    }

    @Override
    public int add(Classe value) {
      return classeRepository.insert(value);
    }

    @Override
    public ArrayList<Classe> getAll() {
       return classeRepository.findAll();
    }

    @Override
    public int update(Classe value) {
        return classeRepository.update(value);
    }

    @Override
    public Classe show(int id) {
      return classeRepository.findByID(id);
    }

    @Override
    public int remove(int id) {
      return classeRepository.delete(id);
    }

    @Override
    public int addModulesInClasse(Classe classe, ArrayList<Module> modules) {
      return classeRepository.addModulesInClasse(modules, classe);
    }

    @Override
    public ArrayList<Module> getModulesClasse(Classe classe) {
      return classeRepository.getModulesForClasse(classe);
    }

    @Override
    public ArrayList<Classe> getClassesByModule(Module module) {
      
        return classeRepository.getClassesByModule(module);
        
    }

   
    
}
