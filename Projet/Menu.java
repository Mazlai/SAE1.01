/**
* SAE1.01 - Implémentation d'un besoin
*
* @author Mickaël FERNANDEZ et Tilian HURÉ
*/
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.text.*;
public class Menu {
    //Texte du menu
    public static String [][] promo = null;
    public static int nbItemMenu = 4;
    public static String texteMenu = "\n/**********************************************/\n"+" Selectionner :\n 1 - Tirer des élèves de la promo au hasard\n"
            +" 2 - Vérifier si un(e) étudiant(e) est dans la promo\n 3 - Afficher les élèves de la promo\n 4 - Quitter\n"+"/**********************************************/\n\n\nChoix : ";       
                
    /**
     * Permet de retourner une valeur entiere saisie au clavier comprise entre pfBorneInf et pfBorneSup
     * @param pfBorneSup In la borne sup
     * @param pfBorneInf In la borne inf
     * @return valeur entiere comprise entre pfBorneInf et pfBorneSup
     **/
    public static int saisieIntC(int pfBorneInf,  int pfBorneSup) {
        int valeur; //Valeur saisie bornée
        Scanner clavier = new Scanner(System.in);        
        valeur=clavier.nextInt();
        while (valeur<pfBorneInf || valeur>pfBorneSup) {
            System.out.print("\nErreur ! Donnez une valeur comprise entre " + pfBorneInf +" et "+pfBorneSup+ " : ");
            valeur=clavier.nextInt();
        }
        return valeur;
    }
    
    /** 
     * Traite le choix 1
     * @param pfPromo IN : tableau de strings de dimension 2
     * @param pfNbEtudiants IN : nombre de données dans le tableau pfPromo
     **/
    public static void tirageEtudiant(String pfPromo[][], int pfNbEtudiants) {
        //Initialisation variables
        Scanner clavier = new Scanner(System.in); //Accès au clavier    
        
        boolean valide = false; //Tester si la saisie est valide
        boolean continuer = true; //Tester si l'utilisateur veut continuer de saisir
        
        String historiqueEtu[][] = new String[pfNbEtudiants][2]; //Historique des étudiants déjà tirés au hasard
        int nbEtudiantsTirés = 0; //Nombre d'étudiants déjà tirés au hasard
        String dernierEtuTiré[] = new String[2]; //Dernier étudiant tiré au hasard lors du dernier tour
        String etuTiré[] = new String[2]; //Etudiant tiré au hasard
        
        System.out.println("Tirer au hasard un(e) étudiant(e) ? [Oui/non]");
        String saisie = clavier.nextLine(); //Valeur de la saisie de l'utilisateur
        while (continuer) {
            while (!valide) {                
                switch (saisie.toLowerCase()) {                  
                        //Continuer le tirage
                        case "oui":
                            valide = true;
                            saisie = "o";
                            break;                   
                        case "o":
                            valide = true;
                            saisie = "o";
                            break;
                        case "":                        
                            valide = true;
                            saisie = "o";
                            break;
                        //Arrêter le tirage
                        case "non":
                            valide = true;
                            saisie = "n";
                            break;
                        case "n":
                            valide = true;
                            saisie = "n";
                            break;
                        default:
                            System.out.println("\nErreur ! Répondez par Oui/o ou par Non/n :");
                            saisie = clavier.nextLine();
                            break;
                }                
            }
            //Si l'utilisateur veut continuer le tirage
            if (saisie.equals("o")) {
                continuer = true;
                valide = false;
                etuTiré = tirerEtuAleatoire(pfPromo, pfNbEtudiants);
                
                //Pour ne pas tirer au hasard le dernier étudiant choisi au dernier tour
                while (etuTiré == dernierEtuTiré) {
                    etuTiré = tirerEtuAleatoire(pfPromo, pfNbEtudiants);
                }                
                
                //Si l'étudiant tiré au hasard est déjà passé
                while (etuEstPresent(historiqueEtu, etuTiré, pfNbEtudiants)) {
                    etuTiré = tirerEtuAleatoire(pfPromo, pfNbEtudiants);
                }    
                
                //Ajout de l'étudiant tiré au hasard dans l'historique
                historiqueEtu[nbEtudiantsTirés] = etuTiré;                        
                nbEtudiantsTirés++;
                
                //Affichage de l'étudiant tiré au hasard
                System.out.println("L'étudiant tiré au hasard est : " + etuTiré[0] + " " + etuTiré[1]);
                
                //Dans le cas où l'historique est rempli (lorsqu'un tour est fini, soit quand tout le monde est passé)
                if (pfNbEtudiants == nbEtudiantsTirés) {
                    nbEtudiantsTirés = 0;
                    dernierEtuTiré = historiqueEtu[pfNbEtudiants-1];
                    //Pour vider/réinitialiser l'historique
                    for (int i=0; i<pfNbEtudiants; i++) {
                        historiqueEtu[i] = null;
                    }
                    System.out.println("\nOn recommence un tour !");
                }
                
                System.out.println("Tirer au hasard un(e) autre étudiant(e) ? [Oui/non]");
                saisie = clavier.nextLine(); //Valeur de la saisie de l'utilisateur
            } else {
                continuer = false;
            }            
        }
    }
    
    /** 
     * Teste si un élève fait partie d'un tableau d'étudiants donné
     * @param pfTabEtu IN : tableau de strings de dimension 2
     * @param pfEtudiant IN : tableau de strings de dimension 1
     * @param pfNbEtudiants IN : nombre de données dans le tableau pfPromo
     * @return vrai si l'étudiant est présent dans la promo, sinon faux
     **/
    public static boolean etuEstPresent(String pfTabEtu[][], String pfEtudiant[], int pfNbEtudiants) {
        boolean present = false; //Si l'étudiant est présent dans le tableau d'étudiants donné
        int i = 0; //Indice de parcours
        while (i<pfNbEtudiants && !present && pfTabEtu[i] != null) {
            if (pfEtudiant[0].equals(pfTabEtu[i][0]) && pfEtudiant[1].equals(pfTabEtu[i][1])) {
                present = true;
            }            
            i++;
        }    
        return present;
    }
    
    /** 
     * Tire un étudiant au hasard dans la promo
     * @param pfPromo IN : tableau de strings de dimension 2
     * @param pfNbEtudiants IN : nombre de données dans le tableau pfPromo
     * @return un étudiant correspondant aux critères
     **/
    public static String[] tirerEtuAleatoire(String pfPromo[][], int pfNbEtudiants) {
        int indiceEtu = (int) (Math.random()*(pfNbEtudiants)); //Indice de l'étudiant sélectionné aléatoirement
        return pfPromo[indiceEtu];               
    }
    
    /** 
     * Teste si un étudiant est dans la promo
     * @param pfPromo IN : tableau de strings de dimension 2
     * @param pfNbEtudiants IN : nombre de données dans le tableau pfPromo
     **/
    public static void saisieEtudiant(String[][] pfPromo, int pfNbEtudiants) {
        //Initialisation variables
        Scanner clavier = new Scanner(System.in);        
        String etudiant[] = new String[2];
        
        //Saisie nom et prénom
        System.out.println("Veuillez renseigner le nom de l'étudiant : ");
        String nom = clavier.next(); 
        System.out.println("\nVeuillez renseigner le prénom de l'étudiant : ");
        String prenom = clavier.next();
        
        etudiant[0] = nom;
        etudiant[1] = prenom;     
        boolean estPresent = etuEstPresent(pfPromo, etudiant, pfNbEtudiants);
            
        if (estPresent) {
            System.out.println("\n" + etudiant[0] + " " + etudiant[1] + " est bien dans la promo.");
        } else {
            System.out.println("\n" + etudiant[0] + " " + etudiant[1] +  " ne fait pas parti de la promo."
                + "\n(Ou peut être avez-vous mal orthographié son nom ou son prénom ?)"
                + "\nSyntaxe : <Nom> et <Prenom>");
        }    
    }
    
    /**  
     * Affiche le menu et exécute les choix
     * @param pfPromo IN : tableau de strings de dimension 2
     * @param pfNbEtudiants IN : nombre de données dans le tableau pfPromo
     **/
    public static void testMenu(String pfPromo[][], int pfNbEtudiants) {
        int choixUtilisateur; //Numéro du choix de l'utilisateur
        do {
            System.out.println(texteMenu);
            choixUtilisateur = saisieIntC(1, nbItemMenu);
            try {
                switch (choixUtilisateur) {    
                    case 1:
                        //Choix #1 --> Tirer des étudiants de la promo au hasard
                        tirageEtudiant(promo, pfNbEtudiants);
                        break;
                    case 2:
                        //Choix #2 --> Vérifier si un étudiant est dans la promo
                        saisieEtudiant(promo, pfNbEtudiants);
                        break;
                    case 3:
                        //Choix #3 --> Affichage des étudiants de la promo
                        ListeEtudiants.main(null);
                        break;
                    case 4:
                        //Choix #4 --> Quitter
                        System.out.println ("\n\n\nArrêt du programme,\nmerci de l'avoir utilisé.");                             
                        break;             
                    default:
                        //Aucun des choix proposés
                        System.out.println("\n\n\nඞඞඞ SUS ඞඞඞ");
                        break;
                } 
            } catch (Exception e) {
                System.out.println("\nErreur: " + e.getMessage() + "\nLigne : " + e.getStackTrace()[0].getLineNumber());
            }
        }
        while (choixUtilisateur != 4); //Si le choix est 4, la boucle et donc le programme s'arrêtent.
    }  
    
    /**  
     * Fonction principale (appeler lors de l'appel de la classe Menu avec un cmd)
     * @param args IN : tableau vide (puisque main ne traite pas d'arguments, pas besoin d'en renseigner)
     **/
    public static void main(String args[]) {
        try {
            promo = ListeEtudiants.getListe("listenomssansaccent.csv", ","); //appel du sous programme précédé du nom de la classe où elle est définie
            System.out.println("Il y a : " + ListeEtudiants.nbEtudiant(promo) + " etudiants"); 
            System.out.println();
            testMenu(promo, ListeEtudiants.nbEtudiant(promo));
        } catch (Exception e) {  
            System.out.println("\nErreur: " + e.getMessage() + "\nLigne : " + e.getStackTrace()[0].getLineNumber());
        }
  } // fin main
}

