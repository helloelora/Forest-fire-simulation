/* Simulation de feu 
 * EL GHAOUTY Hajar
 * DROUILLET Elora
 * 11 Mars 2022
 */
package simulationfeuvrai;

import java.awt.Color;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class SimulationFeuvrai {
    private String sens = "0";            //etat de la case (intacte, enflamée, ...)
    private double nbr = 0;
    private String humidite = "0";
    private String vent = "0";
    private Case[][]forest;
    private Climat cli;
    private int taille;
    
 
    public SimulationFeuvrai (String sens, String vent, String humidite, double nbr, int ligne, int colonne, int taille) throws IOException {
        forest = creationTerrain(nbr,taille);
        cli = recupereDonnes(sens,vent,humidite);
        taille = (int) sqrt(taille);
        if (ligne==1000 && colonne==1000) {
            brulerTerrain(forest,taille);
        } else {
            brulerTerrain2(forest, ligne, colonne,taille);
        }
        Fichiertext(nbr, sens, vent, humidite,taille); 
    }
    
    public Case[][] creationTerrain(double nbr, int taille) {
        Terrain te = new Terrain();                 //faux objet terrain pour créer notre vrai objet terrain
        Case[][] forest = te.randomForest(nbr,taille);
        Terrain t = new Terrain(nbr, forest, taille);
        this.forest = forest;
        return forest;
    }
     public Case[][] brulerTerrain(Case[][] forest, int taille) { //brûler aléatoirement
        Random random = new Random();
        int ligne = random.nextInt(taille);
        int colonne = random.nextInt(taille);
        int ligne2 = random.nextInt(taille);
        int colonne2 = random.nextInt(taille);
        while (ligne == ligne2 && colonne == colonne2) {  //test si les deux premières case qui brûlent ne sont pas identique
            ligne = random.nextInt(taille);
            colonne = random.nextInt(taille);
            ligne2 = random.nextInt(taille);
            colonne2 = random.nextInt(taille);
        }
        
        Case c = new Case(0, "intact");
        c.changeEtat(forest, ligne, colonne);    //donne la valeur 2 à la première case qui s'enflamme
        c.changeEtat(forest, ligne2, colonne2);
        System.out.println("Les deux cases qui vont s'enflammer sont celles à la ligne : " + (ligne + 1) + " et à la colonne : " + (colonne + 1) + " et à la ligne : " + (ligne2 + 1) + " et à la colonne : " + (colonne2+1));
        return forest;
     }
     
     public Case[][] brulerTerrain2(Case[][] forest,int ligne, int colonne, int taille) {  //brûle 2 cases choisi par l'utilisateur
        Terrain te = new Terrain();
        Case c = new Case(0, "intact");
        boolean faute = true;
                    faute = true;
                    try {
                        te.check3(ligne, forest, taille);                              //permet de vérifier que le nombre est bien compris dans le bon intervalle
                    } catch (InputMismatchException ex) {
                        System.out.println("Vous devez taper un entier ");
                        faute = false;
                    } catch (Erreur ex) {
                        System.out.println(ex.getMessage());
                        faute = false;
                    }
                
                    faute = true;
                    try {
                        te.check3(colonne, forest, taille);                                 //permet de vérifier que le nombre est bien compris entre 1 et la taille du terrain
                    } catch (InputMismatchException ex) {
                        System.out.println("Vous devez taper un entier ");
                        faute = false;
                    } catch (Erreur ex) {
                        System.out.println(ex.getMessage());
                        faute = false;
                    }
                if (faute!=false) {
                    c.changeEtat(forest, ligne-1, colonne-1);    //donne la valeur 2 à la première case qui s'enflamme 
                }
        return forest;
     }
     
     public Climat recupereDonnes(String sens, String vent, String humidite) {
        Climat cli = new Climat();              //instance de la classe Climat 
        int forceVent = cli.ForceVent(vent);                    //appel de la méthode ForceVent de la classe Climat

        int sensint = 0;
        if (sens.equals("nord")) {
            sensint = 1;
        } else if (sens.equals("sud")) {
            sensint = 2;
        } else if (sens.equals("ouest")) {
            sensint = 3;
        } else if (sens.equals("est")) {
            sensint = 4;
        } 
            
        double[][] sensVent = null;
        switch (sensint) {
            case 1:
                sens = "nord";
                sensVent = cli.ventNord(forceVent);     //appel méthode pour récupérer matrice du nord selon la force du vent(choix1)
                break;
            case 2:
                sens = "sud";
                sensVent = cli.inverser(cli.ventNord(forceVent));
                break;
            case 3:
                sens = "ouest";
                sensVent = cli.ventOuest(forceVent);
                break;
            case 4:
                sens = "est";
                sensVent = cli.inverser(cli.ventOuest(forceVent));
                break;
        }
        double pH = cli.ProbHumidite(humidite);             //appel de la méthode ProbHumidite de la classe Climat

        return cli;
     }
   
     
    public Case[][] pasapas(Case[][]forest,Climat cli, int taille) {
        Case c = new Case(0, "intact");
        
        for (int i = 0; i <= sqrt(taille) - 1; i++) {
            for (int j = 0; j <= sqrt(taille) - 1; j++) {
                    if (forest[i][j].getVege() == 2) {
                        forest = c.changeEtatcours(forest, i, j, cli.valeurForcevent(), cli.valeurhumidite(), cli.valeurmatVent(), 2, taille);
                    }
                    if (forest[i][j].getVege() == 3) {                     //regarde si une case est enflammée et envoie donc des brandons
                        forest = c.changeEtatcours(forest, i, j, cli.valeurForcevent(), cli.valeurhumidite(), cli.valeurmatVent(), 3, taille);
                    }
                }
            }
        forest = c.changeEtatsuite(forest, taille);
        this.forest = forest;  
        return forest;
    }
    
    public boolean verif(Case[][]forest, int taille) {
        
        taille = (int) sqrt(taille);
        boolean test = true;
            outerloop:
            //Boucle permettant de vérifier si les cases sont soient en cendre ou intact et donc que la simulation est terminée
            for (int l = 0; l <= taille - 1; l++) {
                for (int n = 0; n <= taille - 1; n++) {
                    if ((forest[l][n].getVege() == 2) || (forest[l][n].getVege() == 3) || (forest[l][n].getVege() == 4)) {
                        test = true;
                        break outerloop;
                    } else {
                        test = false;
                    }
                }
              
            }
        this.forest = forest;  
        return test;
    }
    public void Fichiertext(double nbr, String sens, String vent, String humidite, int taille) throws IOException {
        double[] stats = new double[100];
        final String FICHSTATS = "SimulationFeu.txt";
        int stat=0;
        for (int i = 0; i <= taille - 1; i++) {
                for (int j = 0; j <= taille - 1; j++) {
                    if (forest[i][j].getVege() == 5) {
                        stat+=1;
                    }
                }
            }
        int i=0;
        stats[i]=nbr;
        stats[i+1]=stat;
        i+=2;
        Gestionstat statistiques = new Gestionstat(stat);
        statistiques.creationFichier(FICHSTATS);
        statistiques.versFichierSimu(nbr, vent, sens, humidite, FICHSTATS);
    }
    
    public void Fichiertext2(int taille) throws IOException {
        double[] stats = new double[100];
        final String FICHSTATS = "SimulationFeu.txt";
        int stat=0;
        for (int i = 0; i <= sqrt(taille) - 1; i++) {
                for (int j = 0; j <= sqrt(taille) - 1; j++) {
                    if (forest[i][j].getVege() == 5) {
                        stat+=1;
                    }
                }
            }
        int i=0;
        stats[i]=nbr;
        stats[i+1]=stat;
        i+=2;
        Gestionstat statistiques = new Gestionstat(stat);
        statistiques.creationFichier(FICHSTATS);
        statistiques.versFichierSimu2(stat, FICHSTATS, taille);
    }
    
    public Case[][] getForest() {
        return this.forest;
    }
   public Climat getClimat(){
       return this.cli;
   }
   
   public String nbrCaseBrulees(Case[][]forest, int taille){
       int cpt = 0;
       taille = (int) sqrt(taille);
       for (int i = 0; i <= taille - 1; i++) {
                for (int j = 0; j <= taille - 1; j++) {
                    if (forest[i][j].getVege() == 2 ||forest[i][j].getVege() == 3 || forest[i][j].getVege() == 4 ||forest[i][j].getVege() == 5) {
                        cpt++;
                    }
                }
            }
       String compteur = String.valueOf(cpt);
       return compteur;
   }
}


    

