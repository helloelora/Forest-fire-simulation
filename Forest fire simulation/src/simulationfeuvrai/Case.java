/* Simulation de feu 
 * EL GHAOUTY Hajar
 * DROUILLET Elora
 * 11 Mars 2022
 */

package simulationfeuvrai;
import java.awt.Color;
import static java.lang.Math.sqrt;

public class Case  {
    private String etat;            //état de la case (intacte, enflamée, ...)
    private int vegetation;         //végétation de la case (0 = sans vege, 1 = avec vege, 2 = enflammé, 3 = brûlé chaud,...)
    private static final Color[]tabColors = new Color[] {
        Color.gray, Color.green, Color.red, Color.orange, Color.yellow, Color.black
    };
    
    public Case(int vegetation, String etat) {
        this.etat = etat;
        this.vegetation = vegetation;
    }
    
    public int getVege() {
        return this.vegetation;
    }

    public String getEtat() {
        return this.etat = etat;
    }
    
    public Case[][] changeEtat(Case[][]forest,int i, int j) {       //donne l'état enflammé à la toute première case qui brûle et change la valeur de sa végétation pour le voir sur le tableau
         forest[i][j].vegetation = 2;
         forest[i][j].etat = "enflamme";
         return forest;
    }
    
    public Case[][] changeEtatsuite(Case[][]forest, int taille) {               //change l'état d'une case en fonction de son état précédent
        
        for (int i = 0; i < sqrt(taille); i++) {
            for (int j = 0; j < sqrt(taille); j++) {
                if ("enflamme".equals(forest[i][j].etat)) {
                    forest[i][j].etat = "enflamme1";
                    forest[i][j].vegetation = 2;
                }
                else if ("enflamme1".equals(forest[i][j].etat)){
                    forest[i][j].etat = "enflamme2";
                }
                else if ("enflamme2".equals(forest[i][j].etat)){
                    forest[i][j].etat = "chaud";
                    forest[i][j].vegetation = 3;
                }
                else if ("chaud".equals(forest[i][j].etat)) {
                    double nb = Math.random();                      //génère un nbr aléatoire entre 0 inclus et 1 exclu
                    if (nb < 0.4) {                                 //probabilité de 40% que la case passe à l'état froid
                        forest[i][j].etat = "froid";
                        forest[i][j].vegetation = 4;
                    }
                }
                else if ("froid".equals(forest[i][j].etat)){
                    forest[i][j].etat = "froid1";
                }
                else if ("froid1".equals(forest[i][j].etat)){
                    forest[i][j].etat = "froid2";
                }
                else if ("froid2".equals(forest[i][j].etat)){
                    forest[i][j].etat = "cendre";
                    forest[i][j].vegetation = 5;
                }
            }
        }
        return forest;
    }
    
    public Case[][] changeEtatcours(Case[][]forest, int i, int j,int force, double humidite, double[][]sens, int intensite, int taille) {  //calcul la probabilité qu'une case à de s"enflammer et l'enflamme
        int cptcol = -3;                //cases voisines sur la colonne de la case qui brûle (3 avant la case et 3 après la case)
        int cptligne = -3 ;             //cases voisines sur la ligne de la case qui brûle (3 avant la case et 3 après la case)
        for (int l = 0; l < 7; l++) {          //parcourt la matrice matVent de taille 7*7 (matrice de proba)
            for (int c = 0; c < 7; c++) {      //parcourt la matrice matVent de taille 7*7 (matrice de proba)
                double prob = sens[l][c];                           //donne la proba associé à la case traité depuis la matrice matVent
                double probFinal = calculProba(force, humidite, prob, intensite);
                //double probFinal = (0.5*(1+2*force)*prob*humidite)/2;   //calcul de la proba que la case s'enflamme
                double nb = Math.random();                          //génère un nbr aléatoire entre 0 inclus et 1 exclu
                int taille2 = (int) sqrt(taille);
                if (nb < probFinal) {                            //si le nbr aléatoire est inférieur à la proba alors la case change d'état et s'enflamme
                    if ((i+cptligne>=taille2) | (j+cptcol>=taille2) | (i+cptligne<0) | (j+cptcol<0)) {  //vérifie que les cases font parties du domaine
                    } else {
                        if ((forest[i+cptligne][j+cptcol].vegetation == 0) | (forest[i+cptligne][j+cptcol].etat != "intact")) {    //vérifie que la case est intact et bien inflamable
                        }else {
                            forest[i+cptligne][j+cptcol].etat = "enflamme"; 
                        }
                    }    
                }
                cptcol ++;
                }
            cptcol=-3;
            cptligne ++;
            }
        return forest;
    }
    
       
    public double calculProba(int force, double humidite, double prob, int intensite) {
        double probFinal;
        if (intensite == 2) {                               //case qui brûle est égale à 2
            probFinal = (0.5*(1+2*force)*prob*humidite);   
        } else {                                            //case qui brûle est égale à 3
            probFinal = (0.5*(1+2*force)*prob*humidite)/3;  
        }
        return probFinal;
    }
    
    public Color getColorFromCase() {
        return tabColors[vegetation];  //attribut une couleur à l'état de la case
    }
}

