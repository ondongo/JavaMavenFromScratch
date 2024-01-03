package com.ism.services.interf;

import java.util.ArrayList;

public interface Service<T> {
    int add(T value);
    ArrayList<T> getAll();
    int update(T value);
    T show(int id);
    int remove(int id);
    
}
