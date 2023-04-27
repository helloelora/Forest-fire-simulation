/* Simulation de feu 
 * EL GHAOUTY Hajar
 * DROUILLET Elora
 * 11 Mars 2022
 */

package simulationfeuvrai;
import static java.lang.Math.sqrt;

public class Terrain {
    private double densite;                                      //indique la densite des cases (50%, 75%...)                                   //indique l'état de la case à l'instant t
    private Case[][] forest;
    private int taille;

    public Terrain(double densite, Case[][]forest, int taille) {
        this.densite = densite;
        this.forest = forest;
        this.taille = taille;
    }
    
    public Terrain() {                                            //constructeur du faux terrain
    }
    
    public Case[][] getForest() {
        return this.forest;
    }
  
    public void check3(int nombre, Case[][]forest, int taille) throws Erreur {
        //exception si l'entier est négatif
        if (nombre<1) {
            throw new Erreur("Vous devez rentrer un nombre entre 1 et "+taille);
        }
        //exception si l'entier est supérieur à la taille du terrain
        if (nombre>sqrt(taille)) {
            throw new Erreur("Vous devez rentrer un nombre entre 1 et "+taille);
        }
    }
    
    public Case[][] randomForest(double densite, int taille) {    //densite correspond au taux de végétation et etat a si la case est verte ou non
        int cpt=0;
        forest = new Case[taille][taille];
        taille = (int) sqrt(taille);
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                double nb = Math.random();                     //génère un nbr aléatoire entre 0 inclus et 1 exclu
                if (nb < densite) {                            //si le nbr aléatoire est inférieur à la densite alors la case devient verte
                    forest[i][j] = new Case(1, "intact");      //1 = vegetation
                } else {
                    forest[i][j] = new Case(0, "intact");      //0 = sans vegetation                }
                }
            }
        }        
        this.forest = forest;
        return forest;
    }
    
    public void afficher(Case[][]forest) {                      //affiche le terrain avec les états des cases
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                System.out.print(forest[i][j].getVege() + " ");
            } System.out.println();
        }
    }
}


