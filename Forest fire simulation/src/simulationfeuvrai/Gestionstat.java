/* Simulation de feu 
 * EL GHAOUTY Hajar
 * DROUILLET Elora
 * 11 Mars 2022
 */

package simulationfeuvrai;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Gestionstat {
    private double stat;                                      //indique la densite des cases (50%, 75%...) 
    

    public Gestionstat(double stat) {
        this.stat = stat;
    }
    
    public void creationFichier(String fichier) throws IOException {            //recevoir le fichier
        String nomFichier = fichier;
        if (!new File(nomFichier).exists()) {
            new File(nomFichier).createNewFile();
        }
    }
    public void versFichierSimu(double type, String force, String sens, String humidite, String FICHSTATS) throws IOException {
        FileWriter fich = new FileWriter(FICHSTATS,true);    //ouverture du fichier en écriture
            String terrain = null;
            if (type==0.5) {
                terrain="clairseme";
            } else if (type==0.75) {
                terrain="espace";
            } else if (type==0.9) {
                terrain = "touffu";
            } else if (type==1) {
                terrain = "continu";
            }
            String infos = "\nType de terrain : "+terrain+"\nForce du vent : "+force+"\nSens du vent : "+sens+"\nHumidité du terrain : "+humidite;
            fich.append(infos + System.lineSeparator());     //écriture dans le fichier
        
        fich.close();
        }
    
    public void versFichierSimu2(int stat, String FICHSTATS, int taille) throws IOException {
        FileWriter fich = new FileWriter(FICHSTATS,true);    //ouverture du fichier en écriture
            double stats = (stat*100)/taille;   //pour le mettre en pourcentage
            String infos = "Proportion de cases qui ont brûlés : "+stats+"%";
            fich.append(infos + System.lineSeparator());     //écriture dans le fichier
            fich.append(System.lineSeparator());
            fich.append("-----------------------");
            fich.append(System.lineSeparator());
        fich.close();
        }
}
    

