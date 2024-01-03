package com.ism.services.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import com.ism.entities.Classe;
import com.ism.entities.Cours;
import com.ism.entities.arrayList;
import com.ism.repositories.bd.CoursRepository;
import com.ism.services.interf.CoursService;

public class CoursServiceImpl implements CoursService{
    private CoursRepository coursRepository;
    
    public CoursServiceImpl(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    @Override
    public int add(Cours value) {
     return coursRepository.insert(value);
    }

    @Override
    public ArrayList<Cours> getAll() {
       return coursRepository.findAll();
    }

    @Override
    public int update(Cours value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Cours show(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public int remove(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public  ArrayList<Cours> findCoursByDate(Date date) {
         ArrayList<Cours> allCours = null;
         try {
              allCours = new ArrayList<>();
               for (Cours cour : coursRepository.findAll()) {
                    if(cour.getDate().compareTo(date)==0){
                        allCours.add(cour);
                    }
                 }
         } catch (Exception e) {
            // TODO: handle exception
         }
      return allCours;
    }

    @Override
    public int findCoursByHeure(Time time1,Time time2,ArrayList<Cours> cours) {
       for (Cours oneCours : cours) {
             String hourMin1 = oneCours.getHeureDebut().toString();
             String[] temps1 = hourMin1.split(":");
             String hourMin2 = oneCours.getHeureFin().toString();
             String[] temps2 = hourMin2.split(":");
             String hourMin3 = time1.toString();
             String[] temps3 = hourMin3.split(":");
            String hourMin4 = time2.toString();
             String[] temps4 = hourMin4.split(":");
             int  min1 = Integer.parseInt(temps1[0]) *60 + Integer.parseInt(temps1[1]);
             int  min2 = Integer.parseInt(temps2[0]) *60 + Integer.parseInt(temps2[1]);
             int  min3 = Integer.parseInt(temps3[0]) *60 + Integer.parseInt(temps3[1]);
              int  min4 = Integer.parseInt(temps4[0]) *60 + Integer.parseInt(temps4[1]);
             int ecartEx=min2-min1;
             int ecartSv=min4-min3;
             int ecart;
             if (min3>min1) {
                ecart=min3-min1;
                if (ecartEx>ecart) {
                    return 0;
                   
                }
             }else{
                ecart=min1-min3;
                ecartSv=min4-min3;
                  System.out.println(ecart);
                  System.out.println(ecartSv);
                if (ecart<ecartSv) {
                    return 0; 
                }
             }      
       }
       return 1;

    }

    @Override
    public ArrayList<Cours> findCoursByProf(int idP, ArrayList<Cours> cours) {
        ArrayList<Cours> coursProf=null;
        try {
             coursProf= new ArrayList<>();
                 for (Cours cours2 : cours) {
                    if (cours2.getProfesseur().getId()==idP) {
                        coursProf.add(cours2);
                }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        return coursProf;
    }

    @Override
    public int getClasseByModule(ArrayList<Cours> cours,int idC) {
         ArrayList<Classe> classeList = new ArrayList<Classe>();
      for (Cours cours2 : cours) {
        //cours2.getClasses().forEach(System.out::println);
           classeList=cours2.getClasses();
           for (Classe classe : classeList) {
                if (classe.getId()==idC) {
                    return 1;
                }
           }
            
      }
           
      return 0;
    }

    @Override
    public ArrayList<Cours> findCoursByModule(int idM, ArrayList<Cours> cours) {
      ArrayList<Cours> coursMod=null;
        try {
                coursMod= new ArrayList<>();
                 for (Cours cours2 : cours) {
                    if (cours2.getModule().getId()==idM) {
                        coursMod.add(cours2);
                }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        return coursMod;
    }

    @Override
    public ArrayList<Cours> findCoursBySale(int idS, ArrayList<Cours> cours) {
       ArrayList<Cours> coursSale=null;
        try {
             coursSale= new ArrayList<>();
                 for (Cours cours2 : cours) {
                    if (cours2.getSale().getId()==idS) {
                        coursSale.add(cours2);
                }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        return coursSale;
    }

    @Override
    public ArrayList<Cours> findCoursByClasse(int idC, ArrayList<Cours> cours) {
         ArrayList<Cours> coursClasse=null;
        try {
             coursClasse= new ArrayList<>();
                 for (Cours cours2 : cours) {
                    
                    for (Classe c: cours2.getClasses()) {
                          
                          if (c.getId()==idC) {
                            coursClasse.add(cours2);
                         }
                  
                    }
                }
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        return coursClasse;
    }
    
}
