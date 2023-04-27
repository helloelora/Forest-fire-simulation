/* Simulation de feu 
 * EL GHAOUTY Hajar
 * DROUILLET Elora
 * 11 Mars 2022
 */
package simulationfeuvrai;

public class Climat {

    private String humidite;          //humidité du terrain
    private String ventForce;         //force du vent(nul=0, modéré=1, fort=2, volent=3)
    private String ventDirection;     //direction du vent (nord, sud, ouest, est)
    private double pH;                //probabilité selon l'humidité
    private int f;                    // valeur de la force du vent (en entier)
    private double[][] matVent = new double[5][5];  //matrice répartition des brandons en fonction de la force du vent

    public Climat() {
        this.humidite = humidite;
        this.ventForce = ventForce;
        this.ventDirection = ventDirection;
        this.pH=pH;
    }

    public double ProbHumidite(String humidite) {
        //probabilité qu'une case s'enflamme selon l'humidité de la case
        if (humidite.equals("humide")) {
            pH = 0.1;
        }
        if (humidite.equals("normal")) {
            pH = 0.35;
        }
        if (humidite.equals("sec")) {
            pH = 0.6;
        }
        if (humidite.equals("très sec")) {
            pH = 0.9;
        }
        return pH;
    }

    public int ForceVent(String force) {
        //renvoie un entier f selon la force du vent (entrée par l'utilisateur)
        if (force.equals("nul")) {
            f = 0;
        }
        if (force.equals("modéré")) {
            f = 1;
        }
        if (force.equals("fort")) {
            f = 2;
        }
        if (force.equals("violent")) {
            f = 3;
        }
        return f;
    }

    public double[][] ventNord(int choix) {
        //création des matrices pour la répartition de la proba des envoies des brandons
        //vent du NORD
        switch (choix) {      //choix entré par l'utilisateur de la force du vent
            case 0:
                matVent = new double[][] //Vent nul
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.01, 0.01, 0.01, 0, 0},
                    {0, 0.01, 0.2, 0.3, 0.2, 0.01, 0},
                    {0, 0.01, 0.3, 0, 0.3, 0.01, 0},
                    {0, 0.01, 0.2, 0.3, 0.2, 0.01, 0},
                    {0, 0, 0.01, 0.01, 0.01, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;
            case 1:
                matVent = new double[][] //Vent modéré
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.1, 0.2, 0.1, 0, 0},
                    {0, 0, 0.3, 0, 0.3, 0, 0},
                    {0, 0, 0.3, 0.4, 0.3, 0, 0},
                    {0, 0, 0.02, 0.05, 0.02, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;
            case 2:
                matVent = new double[][] //Vent fort
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.05, 0.1, 0.05, 0, 0},
                    {0, 0, 0.25, 0, 0.25, 0, 0},
                    {0, 0, 0.4, 0.5, 0.4, 0, 0},
                    {0, 0, 0.05, 0.1, 0.05, 0, 0},
                    {0, 0, 0, 0.01, 0, 0, 0}
                };
                break;
            case 3:
                matVent = new double[][] //vent violent
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 01, 0, 0.1, 0, 0},
                    {0, 0, 0.5, 0.7, 0.5, 0, 0},
                    {0, 0, 0.2, 0.3, 0.2, 0, 0},
                    {0, 0, 0.01, 0.05, 0.01, 0, 0}
                };
                break;
        }
        return matVent;
    }

    public double[][] inverser(double[][] matVent) {     //inverser les tableaux de vent pour le SUD et l'EST
        if (matVent == null) {
            return null;
        }
        double tmp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <= matVent.length - 1; j++) {
                tmp = matVent[i][j];
                matVent[i][j] = matVent[matVent.length - 1 - i][j];
                matVent[matVent.length - 1 - i][j] = tmp;
            }
        }
        return matVent;
    }

    public double[][] ventOuest(int choix) {
        //création des matrices pour la répartition de la proba des envoies des brandons
        //vent du OUEST
        switch (choix) {      //choix entré par l'utilisateur de la force du vent
            case 1:
                matVent = new double[][] //vent nul
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.01, 0.01, 0.01, 0, 0},
                    {0, 0.01, 0.2, 0.3, 0.2, 0.01, 0},
                    {0, 0.01, 0.3, 0, 0.3, 0.01, 0},
                    {0, 0.01, 0.2, 0.3, 0.2, 0.01, 0},
                    {0, 0, 0.01, 0.01, 0.01, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;
            case 2:
                matVent = new double[][] //vent modéré
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.1, 0.3, 0.3, 0.02, 0},
                    {0, 0, 0.2, 0, 0.4, 0.05, 0},
                    {0, 0, 0.1, 0.3, 0.3, 0.02, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;
            case 3:
                matVent = new double[][] //Vent fort
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0.05, 0.25, 0.4, 0.5, 0},
                    {0, 0, 0.1, 0, 0.5, 0.1, 0.01},
                    {0, 0, 0.05, 0.25, 0.4, 0.05, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;
            case 4:
                matVent = new double[][] //vent violent
                {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0.1, 0.5, 0.2, 0.01},
                    {0, 0, 0, 0, 0.7, 0.3, 0.05},
                    {0, 0, 0, 0.1, 0.5, 0.02, 0.01},
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
                };
                break;

        }
        /*PERMET D'AFFICHER LA MATRICE DE PROBAS
        for (int l = 0; l <= matVent.length - 1; l++) {             
            for (int n = 0; n <= matVent.length - 1; n++) {
                System.out.print(matVent[l][n] + " ");
            }
            System.out.println();
        }*/
        return matVent;
    }
    
    public int valeurForcevent(){
        return f;
    }
    
    public double[][]valeurmatVent(){
        return matVent;
    }
    
    public double valeurhumidite(){
        return pH;
    }
}

