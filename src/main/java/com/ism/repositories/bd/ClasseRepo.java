package com.ism.repositories.bd;

import com.ism.entities.Classe;
import com.ism.entities.Module;
import com.ism.repositories.core.Repository;
import java.util.ArrayList;

public interface ClasseRepo extends Repository<Classe> {
  int addModulesInClasse(ArrayList<Module> modules, Classe classe);
  ArrayList<Module> getModulesForClasse(Classe classe);
  ArrayList<Classe> getClassesByModule(Module module);
}
