package com.ism.services.impl;

import java.util.ArrayList;

import com.ism.entities.Sale;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.SaleRepository;
import com.ism.services.interf.SaleService;

public class SaleServiceImpl implements SaleService{
    private SaleRepository saleRepository;
   

    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public int add(Sale value) {
        return saleRepository.insert(value);
    }

    @Override
    public ArrayList<Sale> getAll() {
       return saleRepository.findAll();
    }

    @Override
    public int update(Sale value) {
       return saleRepository.update(value);
    }

    @Override
    public Sale show(int id) {
        return saleRepository.findByID(id);
    }

    @Override
    public int remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    
}
