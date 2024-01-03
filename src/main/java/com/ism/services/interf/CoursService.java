package com.ism.services.interf;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import com.ism.entities.Cours;

public interface CoursService extends Service<Cours>{
    ArrayList<Cours> findCoursByDate(Date date);
    int findCoursByHeure(Time time1 ,Time time2,ArrayList<Cours> cours);
    ArrayList<Cours> findCoursByProf(int idP, ArrayList<Cours> cours);
    ArrayList<Cours> findCoursBySale(int idS, ArrayList<Cours> cours);
    ArrayList<Cours> findCoursByModule(int idM, ArrayList<Cours> cours);
    ArrayList<Cours> findCoursByClasse(int idC, ArrayList<Cours> cours);
    int getClasseByModule(ArrayList<Cours> cours,int idC);

}
