package com.ism;

import com.ism.entities.Classe;
import com.ism.entities.Cours;
import com.ism.entities.Module;
import com.ism.entities.Professeur;
import com.ism.entities.ProfesseurClasse;
import com.ism.entities.Sale;
import com.ism.entities.enums.EtatClasseEnum;
import com.ism.repositories.bd.ClasseRepo;
import com.ism.repositories.bd.CoursRepository;
import com.ism.repositories.bd.ModuleRepo;
import com.ism.repositories.bd.ProfesseurClasseRepo;
import com.ism.repositories.bd.ProfesseurRepo;
import com.ism.repositories.bd.SaleRepository;
import com.ism.repositories.bd.impl.ClasseRepoImpl;
import com.ism.repositories.bd.impl.CoursRepositoryImpl;
import com.ism.repositories.bd.impl.ModuleRepoImpl;
import com.ism.repositories.bd.impl.ProfesseurClasseRepoImpl;
import com.ism.repositories.bd.impl.ProfesseurRepoImpl;
import com.ism.repositories.bd.impl.SaleRepositoryImpl;
import com.ism.repositories.core.Database;
import com.ism.repositories.core.MySQLImpl;
import com.ism.services.impl.ClasseServiceImpl;
import com.ism.services.impl.CoursServiceImpl;
import com.ism.services.impl.ModuleServiceImpl;
import com.ism.services.impl.ProfesseurServiceImpl;
import com.ism.services.impl.SaleServiceImpl;
import com.ism.services.interf.ClasseService;
import com.ism.services.interf.CoursService;
import com.ism.services.interf.ModuleService;
import com.ism.services.interf.ProfesseurService;
import com.ism.services.interf.SaleService;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    Database db = new MySQLImpl();
    ModuleRepo moduleRepo = new ModuleRepoImpl(db);
    ModuleService moduleService = new ModuleServiceImpl(moduleRepo);
    SaleRepository saleRepository = new SaleRepositoryImpl(db);
    SaleService saleService = new SaleServiceImpl(saleRepository);
    ClasseRepo classeRepo = new ClasseRepoImpl(db, moduleRepo);
    ClasseService classeService = new ClasseServiceImpl(classeRepo);
    ProfesseurRepo profRepo = new ProfesseurRepoImpl(db);
    ProfesseurClasseRepo profClasseRepo = new ProfesseurClasseRepoImpl(
      db,
      moduleRepo,
      classeRepo
    );
    ProfesseurService profService = new ProfesseurServiceImpl(
      profRepo,
      profClasseRepo,
      classeRepo
    );
    CoursRepository coursRepository = new CoursRepositoryImpl(
      db,
      classeRepo,
      moduleRepo,
      profRepo,
      saleRepository,
      profClasseRepo
    );
    CoursService coursService = new CoursServiceImpl(coursRepository);

    int choix;

    do {
      System.out.println(
        "-------------------------GESTION ECOLE--------------------------------"
      );
      System.out.println("1-Gestion Classes------------------------");
      System.out.println("2-Gestion Modules------------------------");
      System.out.println("3-Affecter Modules a une classe----------");
      System.out.println("4-Afficher les Modules d'une classe------");
      System.out.println("5-Les Classes qui font un module----------");
      System.out.println("6-Gestion Professeurs------------------------");
      System.out.println(
        "7-Afficher les Classes d'un professeur ainsi que les modules enseignes"
      );
      System.out.println("8-Gerer les Salles------------------------");
      System.out.println("9-Planifier Cours --------------------------------");
      System.out.println(
        "10-Afficher les cours d'une Classes --------------------------------"
      );
      System.out.println("11-Afficher les Cours d'une Classe ---");
      System.out.println("12-Quitter--------------------------------");
      System.out.println("Faites votre chhoix");
      choix = scanner.nextInt();
      scanner.nextLine();
      switch (choix) {
        case 1:
          System.out.println("1-Ajouter une classe");
          System.out.println("2-Lister les classes");
          System.out.println("3-Modifier une classe ");
          System.out.println("4-Archiver une classe");
          System.out.println("5-Retour au menu Principal");
          System.out.println("Faites votre chhoix");
          int choix1 = scanner.nextInt();
          scanner.nextLine();
          switch (choix1) {
            case 1:
              System.out.println("1-Ajouter une classe");
              System.out.println("Entrer le Niveau");
              String niveau = scanner.nextLine();
              System.out.println("Entrer la Filiere");
              String filiere = scanner.nextLine();
              System.out.println("Entrer le nombre de place");
              int place = scanner.nextInt();
              String libelle = niveau + " " + filiere;
              Classe classe = new Classe(libelle, niveau, filiere, place);
              classeService.add(classe);

              break;
            case 2:
              //System.out.println("2-Lister des classes");
              //classeService.getAll().forEach(System.out::println);
              List<Classe> classesAll = classeService.getAll();
              for (Classe c : classesAll) {
                System.out.println("ID: " + c.getId());
                System.out.println("Libelle: " + c.getLibelle());
                System.out.println("Niveau: " + c.getNiveau());
                System.out.println("Filiere: " + c.getFiliere());
                System.out.println("Place: " + c.getPlace());
                System.out.println("Etat: " + c.getEtat());
                System.out.println("Modules: " + c.getModules());
                System.out.println("-----------------------------------");
              }
              break;
            case 3:
              System.out.println("3-Modifier une classe ");
              int choixQuitter;
              int choixModifier;
              classeService.getAll().forEach(System.out::println);
              System.out.println("Entrer l'id de la classe");
              int idclasse = scanner.nextInt();
              classe = classeService.show(idclasse);
              if (classe == null) {
                System.out.println("La classe n'existe pas");
              } else {
                do {
                  do {
                    System.out.println("1-Modifier Libelle");
                    System.out.println("2-Modifier Niveau");
                    System.out.println("3-Modifier Filiere");
                    System.out.println("4-Modifier nombre de place");
                    System.out.println("Que voulez vous modifier?");
                    choixModifier = scanner.nextInt();
                    scanner.nextLine();
                  } while (choix < 1 || choix > 4);
                  if (choixModifier == 1) {
                    System.out.println("Entrer le Libelle");
                    libelle = scanner.nextLine();
                    classe.setLibelle(libelle);
                  } else {
                    if (choixModifier == 2) {
                      System.out.println("Entrer le Niveau");
                      niveau = scanner.nextLine();
                      classe.setNiveau(niveau);
                    } else {
                      if (choixModifier == 3) {
                        System.out.println("Entrer la Filiere");
                        filiere = scanner.nextLine();
                        classe.setFiliere(filiere);
                      } else {
                        System.out.println("Entrer le nombre de place");
                        place = scanner.nextInt();
                        classe.setPlace(place);
                      }
                    }
                  }
                  System.out.println("1-continuer");
                  System.out.println("2-quitter");
                  choixQuitter = scanner.nextInt();
                } while (choixQuitter != 2);
                int idu = classeService.update(classe);
                System.out.println(idu);
              }

              break;
            case 4:
              System.out.println("4-Archiver une classe");
              classeService.getAll().forEach(System.out::println);
              System.out.println("Entrer l'id de la classe");
              idclasse = scanner.nextInt();
              classe = classeService.show(idclasse);
              if (classe == null) {
                System.out.println("La classe n'existe pas");
              } else {
                classe.setEtat(EtatClasseEnum.CLOSE.toString());
                classeService.update(classe);
              }

              break;
            default:
              break;
          }
          break;
        case 2:
          System.out.println("1-Ajouter un module");
          System.out.println("2-Lister les modules");
          System.out.println("3-Modifier un Module ");
          System.out.println("4-Archiver un Module");
          System.out.println("5-Retour au menu Principal");
          System.out.println("Faites votre chhoix");
          int choix2 = scanner.nextInt();
          scanner.nextLine();
          switch (choix2) {
            case 1:
              System.out.println("1-Ajouter un module");
              System.out.println("Entrer le libelle du module");
              String libelle = scanner.nextLine();
              System.out.println("Entrer le nombre d'heure totale du module");
              int heure = scanner.nextInt();
              Module module = new Module(libelle, heure);
              moduleService.add(module);
              break;
            case 2:
              System.out.println("2-Lister les modules");
              moduleService.getAll().forEach(System.out::println);
              break;
            case 3:
              System.out.println("3-Modifier un Module ");
              moduleService.getAll().forEach(System.out::println);
              System.out.println("Entrer l'id du module a modifier");
              int idModules = scanner.nextInt();
              module = moduleService.show(idModules);
              if (module == null) {
                System.out.println("Le module n'existe pas");
              } else {
                System.out.println("1-Modifier le libelle");
                System.out.println("2-Modifier le nombre d'heure");
                System.out.println("Que voulez-vous modifier");
                choix = scanner.nextInt();
                scanner.nextLine();
                if (choix == 1) {
                  System.out.println("Entrer le nouveau Libelle");
                  libelle = scanner.nextLine();
                  module.setLibelle(libelle);
                } else {
                  System.out.println("Entrer le nombre d'heure");
                  heure = scanner.nextInt();
                  module.setHeure(heure);
                }
              }
              moduleService.update(module);
              break;
            case 4:
              System.out.println("4-Archiver  module");
              moduleService.getAll().forEach(System.out::println);
              System.out.println("Entrer l'id du module a Archiver");
              idModules = scanner.nextInt();
              module = moduleService.show(idModules);
              if (module == null) {
                System.out.println("La module n'existe pas");
              } else {
                module.setEtat(0);
                moduleService.update(module);
              }

              break;
            default:
              break;
          }
          break;
        case 3:
          System.out.println("3-Affecter Modules a une classe----------");
          classeService.getAll().forEach(System.out::println);
          System.out.println("Entrer l'id de la classe");
          int idclasse = scanner.nextInt();

          Classe classe = classeService.show(idclasse);

          ///Gerer les exceptions iciiiiiiiii

          ArrayList<Module> modules = new ArrayList<>();
          int choi;
          do {
            moduleService.getAll().forEach(System.out::println);
            System.out.println("choississez un module");
            int idm = scanner.nextInt();
            modules.add(moduleService.show(idm));
            System.out.println(
              "Voulez-vous continuer? si oui appuyer 1 sinon 0"
            );
            choi = scanner.nextInt();
          } while (choi != 0);
          classeService.addModulesInClasse(classe, modules);
          break;
        case 4:
          System.out.println("4-Afficher les Modules d'une classe------");
          classeService.getAll().forEach(System.out::println);
          System.out.println("Entrer l'id de la classe");
          idclasse = scanner.nextInt();
          classe = classeService.show(idclasse);
          classeService.getModulesClasse(classe).forEach(System.out::println);
          break;
        case 5:
          System.out.println("5-Les Classes qui font un module----------");
          moduleService.getAll().forEach(System.out::println);
          System.out.println("Entrer l'id du modules");
          int idMod = scanner.nextInt();
          Module module = moduleService.show(idMod);
          classeService.getClassesByModule(module).forEach(System.out::println);
          break;
        case 6:
          System.out.println("Gestion du Professeur");
          System.out.println("1-Ajouter un Professeur");
          System.out.println("2-Lister les Professeurs");
          System.out.println("3-Modifier un Professeur ");
          System.out.println("4-Archiver un Professeur");
          System.out.println("5-Retour au menu Principal");
          System.out.println("Faites votre chhoix");
          choix1 = scanner.nextInt();
          scanner.nextLine();
          switch (choix1) {
            case 1:
              System.out.println("1-Ajouter un Professeur");
              Professeur prof = new Professeur();
              System.out.println("Vous allez ajouter un professeur");
              System.out.println("Entrer le nom Complet du prof");
              String nomComplet = scanner.nextLine();
              System.out.println("Entrer l Aadresse du professeur");
              String adresse = scanner.nextLine();
              System.out.println("Entrer le numero de telephone du prof");
              String numero = scanner.nextLine();
              System.out.println("Entrer le grade du prof");
              String grade = scanner.nextLine();
              prof.setAdresse(adresse);
              prof.setTelephone(numero);
              prof.setGrade(grade);
              prof.setNomComplet(nomComplet);
              prof.setEtat(1);
              int idProfesseurfesseur = profService.add(prof);
              int ajoutClasse;

              do {
                classeService.getAll().forEach(System.out::println);
                System.out.println("Entrer l'id de la classe a ajouter");
                int idClasse = scanner.nextInt();
                classe = classeService.show(idClasse);
                if (classe != null) {
                  ArrayList<Module> moduless = new ArrayList<>();

                  do {
                    System.out.println(
                      "Vous allez ajouter les modules de cette classe"
                    );
                    classeService
                      .getModulesClasse(classe)
                      .forEach(System.out::println);
                    int idm = scanner.nextInt();
                    moduless.add(moduleService.show(idm));
                    System.out.println(
                      "Voulez-vous continuer? si oui appuyer 1 sinon 0"
                    );
                    choi = scanner.nextInt();
                  } while (choi != 0);
                  ProfesseurClasse profClass = new ProfesseurClasse();
                  profClass.setIdProfesseur(idProfesseurfesseur);
                  profClass.setIdClasse(idClasse);
                  profClass.setModules(moduless);
                  profService.addClasseModule(profClass);
                }
                System.out.println("1-ajouter autre classe;;;2-Quitter");
                ajoutClasse = scanner.nextInt();
              } while (ajoutClasse == 1);

              break;
            case 2:
              System.out.println("2-Lister les Professeurs");
              profService.getAll().forEach(System.out::println);
              break;
            case 3:
              System.out.println("3-Modifier un Professeur ");
              System.out.println(profService.show(3));
              break;
            default:
              break;
          }
        case 7:
          System.out.println(
            "7-Afficher les Classes d'un professeur ainsi que les modules enseignes"
          );
          profService.getAll().forEach(System.out::println);
          System.out.println("Entrer l'id du Professeur");
          int idP = scanner.nextInt();
          profService.getClasseByPro(idP).forEach(System.out::println);
          break;
        case 8:
          System.out.println("8-Gerer les Salles------------------------");
          System.out.println("1-Ajouter une Salles");
          System.out.println("2-Lister les Salles");
          System.out.println("3-Modifier une salle ");
          System.out.println("4-Archiver une salle");
          System.out.println("5-Retour au menu Principal");
          System.out.println("Faites votre chhoix");
          choix1 = scanner.nextInt();
          scanner.nextLine();
          switch (choix1) {
            case 1:
              Sale sale = new Sale();
              System.out.println("1-Ajouter une classe");
              System.out.println("Entrer le libelle");
              String libelle = scanner.nextLine();
              System.out.println("Entrer le nombre de place");
              int place = scanner.nextInt();
              sale.setLibelle(libelle);
              sale.setPlace(place);
              sale.setEtat(1);
              saleService.add(sale);
              break;
            case 2:
              System.out.println("2-Lister des classes");
              saleService.getAll().forEach(System.out::println);
              break;
            default:
              break;
          }
          break;
        case 9:
          System.out.println(
            "9-Planifier Cours --------------------------------"
          );
          // Date date=saisieDate();
          // System.out.println(date);
          // Time heureD=saisieTime("Debut");

          // Time heureF=saisieTime("Fin");

          // Cours cours=new Cours("PHPL2",date,heureD,heureF,moduleService.show(4),profService.show(2),saleService.show(1),classeService.getAll());
          // coursService.add(cours);
          //coursService.getAll().forEach(System.out::println);
          int rt = 0;
          do {
            System.out.println("2-Liste Des modules");
            moduleService.getAll().forEach(System.out::println);
            System.out.println("Entrer l'ID du module pour le cours");
            module = moduleService.show(scanner.nextInt()); //verifier si le module n'a pas ete programmer
            scanner.nextLine();
            Date date = saisieDate();
            Time heureD = saisieTime("Debut");
            Time heureF = saisieTime("Fin");
            ArrayList<Cours> courseList = coursService.findCoursByDate(date);
            System.out.println("Choisir le professeur ");
            ArrayList<Professeur> professeurs = profService.getProfesseursByModule(
              module.getId()
            );

            if (professeurs.size() == 0) {
              System.out.println(
                "Impossible de creer le cours la liste de professeurs est vide"
              );
              rt = 1;
            } else {
              professeurs.forEach(System.out::println); // verifier la disponibilite du
              int idProfesseurfesseur = scanner.nextInt();
              ArrayList<Cours> coursProfesseur = coursService.findCoursByProf(
                idProfesseurfesseur,
                courseList
              );
              ArrayList<Classe> classesForCourses = null;

              if (
                coursService.findCoursByHeure(
                  heureD,
                  heureF,
                  coursProfesseur
                ) ==
                1
              ) {
                ArrayList<Classe> classes = profService.getClassesByProfesseurByModule(
                  module.getId(),
                  idProfesseurfesseur
                );
                ArrayList<Cours> thisModuleCours = coursService.findCoursByModule(
                  module.getId(),
                  coursService.getAll()
                );
                System.out.println(
                  "Vous allez ajouter les classes pour le cours"
                );
                classesForCourses = new ArrayList<>();
                for (Classe c : classes) {
                  System.out.println(c);
                  System.out.println(
                    "Voulez-vous ajouter cette classe? [1-Oui-----0-Non]"
                  );
                  choi = scanner.nextInt();
                  if (choi == 1) {
                    if (
                      coursService.findCoursByHeure(
                        heureD,
                        heureF,
                        coursService.findCoursByClasse(c.getId(), courseList)
                      ) !=
                      1
                    ) {
                      System.out.println("La classe n'est pas disponible");
                      rt = 1;
                    } else {
                      if (
                        coursService.getClasseByModule(
                          thisModuleCours,
                          c.getId()
                        ) ==
                        0
                      ) {
                        classesForCourses.add(c); //possible dajouter le cours pour cette classe
                      } else {
                        System.out.println(
                          "Le cours a ete deja programmer pour la classe"
                        );
                        rt = 1;
                      }
                    }
                  }
                }
                if (classesForCourses.size() == 0) {
                  System.out.println(
                    "Impossible de creer le cours la liste de classes est vide"
                  );
                  rt = 1;
                } else {
                  int nbP = 0;
                  for (Classe c : classesForCourses) {
                    nbP = nbP + c.getPlace();
                  }

                  int retour = 0;
                  do {
                    int choiC;
                    int idSale;
                    do {
                      System.out.println(
                        "1-Cours en ligne -------2-Cours en presentiel"
                      );
                      choiC = scanner.nextInt();
                    } while (choiC < 1 || choiC > 2);
                    if (choiC == 1) {
                      String code = module.getLibelle() + " ligne";
                      Cours cours = new Cours(
                        code,
                        date,
                        heureD,
                        heureF,
                        module,
                        profService.show(idProfesseurfesseur),
                        classesForCourses
                      );
                      coursService.add(cours);
                    } else {
                      int place = 0;
                      System.out.println(
                        "Entrer l'id de la sale pour le cours"
                      );
                      saleService.getAll().forEach(System.out::println);
                      idSale = scanner.nextInt();
                      ArrayList<Cours> coursSales = coursService.findCoursBySale(
                        idSale,
                        courseList
                      );
                      coursSales.forEach(System.out::println);
                      if (
                        coursService.findCoursByHeure(
                          heureD,
                          heureF,
                          coursSales
                        ) ==
                        1
                      ) {
                        place = saleService.show(idSale).getPlace();
                        if (place < nbP) {
                          System.out.println(
                            "Cette sale ne peut pas contenir l'effectif"
                          );
                          retour = 0;
                        } else {
                          String code =
                            module.getLibelle() +
                            " " +
                            saleService.show(idSale).getLibelle();
                          Cours cours = new Cours(
                            code,
                            date,
                            heureD,
                            heureF,
                            module,
                            profService.show(idProfesseurfesseur),
                            saleService.show(idSale),
                            classesForCourses
                          );
                          coursService.add(cours);
                          System.out.println(
                            "Le cours a ete ajoute avec succes"
                          );
                          retour = 1;
                        }
                      } else {
                        System.out.println(
                          "Impossible cette Sale n'est pas disponible"
                        );
                        retour = 0;
                      }
                    }
                  } while (retour == 0);
                }
              } else {
                System.out.println("Desole le professeur n'est pas disponible");
              }
            }
          } while (rt == 0);

          break;
        case 10:
          System.out.println("La liste des cours d'une classe");
          classeService.getAll().forEach(System.out::println);
          idclasse = scanner.nextInt();
          coursService
            .findCoursByClasse(idclasse, coursService.getAll())
            .forEach(System.out::println);
          break;
        case 11:
          System.out.println("La liste des cours d'un Professeur");
          profService.getAll().forEach(System.out::println);
          System.out.println("Entrer l'id du professeur");
          idP = scanner.nextInt();
          coursService
            .findCoursByProf(idP, coursService.getAll())
            .forEach(System.out::println);
          break;
        default:
          break;
      }
    } while (choix != 12);
  }

  private static Date saisieDate() {
    java.sql.Date sqlDate = null;
    System.out.println("Entrer la date [jj/mm/aaaa]");
    String datesaisie = scanner.nextLine();
    try {
      SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
      SimpleDateFormat targDateFormat = new SimpleDateFormat("yyyy-MM-dd");

      java.util.Date utilDate = currentDate.parse(datesaisie);
      String formatteDate = targDateFormat.format(utilDate);
      sqlDate = java.sql.Date.valueOf(formatteDate);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return sqlDate;
  }

  private static Time saisieTime(String attri) {
    Random random = new Random();

    int hour = random.nextInt(24);
    int minute = random.nextInt(60);
    int second = random.nextInt(60);

    System.out.println("Entrer l'heure" + attri + " [h:mm]");
    String input = scanner.nextLine();

    try {
      String[] parts = input.split(":");
      hour = Integer.parseInt(parts[0]);
      minute = Integer.parseInt(parts[1]);
    } catch (Exception e) {
      System.out.println(
        "Format d'heure invalide. Utilisation de l'heure générée aléatoirement."
      );
    }

    return Time.valueOf(String.format("%02d:%02d:%02d", hour, minute, second));
  }

  private static int timeToInt(Time time) {
    String hourMin1 = time.toString();
    String[] temps1 = hourMin1.split(":");
    int min1 = Integer.parseInt(temps1[0]) * 60 + Integer.parseInt(temps1[1]);

    return min1;
  }
}
