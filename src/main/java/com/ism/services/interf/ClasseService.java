package com.ism.services.interf;

import java.util.ArrayList;

import com.ism.entities.Classe;
import com.ism.entities.Module;

public interface ClasseService extends Service<Classe>{
    int addModulesInClasse(Classe classe,ArrayList<Module> modules);
    ArrayList<Module> getModulesClasse(Classe classe);
    ArrayList<Classe> getClassesByModule(Module module);
}
