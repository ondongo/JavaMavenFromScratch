package com.ism.services.impl;

import java.util.ArrayList;

import com.ism.entities.Module;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.services.interf.ModuleService;

public class ModuleServiceImpl implements ModuleService{
    private ModuleRepo moduleRepository;
    public ModuleServiceImpl(ModuleRepo moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public int add(Module value) {
        return moduleRepository.insert(value);
    }

    @Override
    public ArrayList<Module> getAll() {
        return moduleRepository.findAll();
    }

    @Override
    public int update(Module value) {
       return moduleRepository.update(value);
    }

    @Override
    public Module show(int id) {
      return moduleRepository.findByID(id);
    }

    @Override
    public int remove(int id) {
        return moduleRepository.delete(id);
    }
    
}
