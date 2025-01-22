import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Categorie {

    private String nom; // le nom de la catégorie p.ex : sport, politique,...
    private ArrayList<PaireChaineEntier> lexique; //le lexique de la catégorie

    // constructeur
    public Categorie(String nom) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }


    public  ArrayList<PaireChaineEntier> getLexique() {
        return lexique;
    }


    // initialisation du lexique de la catégorie à partir du contenu d'un fichier texte
    public void initLexique(String nomFichier) {
        lexique = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String ligne = scanner.nextLine();
                String[] subligne = ligne.split(":");
                int entier = Integer.parseInt(subligne[1]);
                /*try {
                    entier = Integer.parseInt(subligne[1]);
                }catch (NumberFormatException e ){
                    entier = 0;
                    System.out.println("Erreur dans " + nomFichier + " à l'endroit" + subligne[0] + " " + subligne[1]);
                }*/
                PaireChaineEntier PaireChaine = new PaireChaineEntier(subligne[0],entier);
                lexique.add(PaireChaine);
            }
            scanner.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //calcul du score d'une dépêche pour la catégorie
    public int score(Depeche d) {
        int score = 0;
        for (int i = 0; i<d.getMots().size();i++){
            int j = 0;
            boolean trouv = true;
            while(j<lexique.size()&&trouv){
                if(d.getMots().get(i).toLowerCase().compareTo(lexique.get(j).getChaine())==0){
                    score += lexique.get(j).getEntiers();
                    trouv = false;
                }
                j++;
            }
        }
        return score;
    }
    public int scoreKNN(Depeche d) {
        int score = 0;
        for (int i = 0; i<d.getMots().size();i++){
            int j = 0;
            boolean trouv = true;
            while(j<lexique.size()&&trouv){
                //if(d.getMots().get(i).toLowerCase().compareTo(lexique.get(j).getChaine())==0){

                j++;
            }
        }
        return score;
    }


}
