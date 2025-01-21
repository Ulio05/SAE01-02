import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification {


    private static ArrayList<Depeche> lectureDepeches(String nomFichier) {
        //creation d'un tableau de dépêches
        ArrayList<Depeche> depeches = new ArrayList<>();
        try {
            // lecture du fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String id = ligne.substring(3);
                ligne = scanner.nextLine();
                String date = ligne.substring(3);
                ligne = scanner.nextLine();
                String categorie = ligne.substring(3);
                ligne = scanner.nextLine();
                String lignes = ligne.substring(3);
                while (scanner.hasNextLine() && !ligne.equals("")) {
                    ligne = scanner.nextLine();
                    if (!ligne.equals("")) {
                        lignes = lignes + '\n' + ligne;
                    }
                }
                Depeche uneDepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(uneDepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }


    /*public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        String res = "";
        ArrayList<Integer> nb_total = new ArrayList<>(5);
        ArrayList<PaireChaineEntier> correct = new ArrayList<>(5);
        for (int m = 0; m<categories.size();m++){
            nb_total.add(0);
            correct.add(new PaireChaineEntier(categories.get(m).getNom(),0));
        }

        for(int i = 0; i<depeches.size();i++){
            ArrayList<PaireChaineEntier> listePaires = new ArrayList<>();
            for (int j = 0; j<categories.size();j++){
                listePaires.add(new PaireChaineEntier(categories.get(j).getNom(),categories.get(j).score(depeches.get(i))));
            }
            String chaine_max = UtilitairePaireChaineEntier.chaineMax(listePaires);
            res += depeches.get(i).getId() + " : " + chaine_max + '\n';
            int k = 0;
            while(categories.get(k).getNom().compareTo(chaine_max)!=0){
                k++;
            }
            nb_total.set(k,nb_total.get(k)+1);
            if (chaine_max.compareTo(depeches.get(i).getCategorie())==0)
                correct.set(k,new PaireChaineEntier(correct.get(k).getChaine(),correct.get(k).getEntiers()+1));
        }
        System.out.println(nb_total);
        System.out.println(correct);
        for(int l = 0; l<categories.size();l++){
            PaireChaineEntier set = new PaireChaineEntier(correct.get(l).getChaine(),correct.get(l).getEntiers() / nb_total.get(l)+1);
            correct.set(l,set);
            res += categories.get(l).getNom() + ":      " + (correct.get(l)) + "%\n";
        }
        res += "MOYENNE:    " + UtilitairePaireChaineEntier.moyenne(correct) + '%';
        try {
            FileWriter file = new FileWriter(nomFichier);
            file.write(res);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
        String res = "";
        ArrayList<Integer> nb_total = new ArrayList<>(categories.size());
        ArrayList<PaireChaineEntier> correct = new ArrayList<>(categories.size());

        // Initialisation des catégories et des compteurs
        for (int m = 0; m < categories.size(); m++) {
            nb_total.add(0);
            correct.add(new PaireChaineEntier(categories.get(m).getNom(), 0));
        }

        // Traitement des dépêches
        for (int i = 0; i < depeches.size(); i++) {
            ArrayList<PaireChaineEntier> listePaires = new ArrayList<>();

            for (int j = 0; j < categories.size(); j++) {
                listePaires.add(new PaireChaineEntier(categories.get(j).getNom(), categories.get(j).score(depeches.get(i))));
            }

            String chaine_max = UtilitairePaireChaineEntier.chaineMax(listePaires);
            res += depeches.get(i).getId() + " : " + chaine_max + '\n';

            int k = 0;
            // Recherche de l'indice de la catégorie avec le meilleur score
            while (categories.get(k).getNom().compareTo(chaine_max) != 0) {
                k++;
            }

            // Mise à jour du total pour la catégorie trouvée
            nb_total.set(k, nb_total.get(k) + 1);

            // Si la catégorie prédite est correcte, incrémenter le compteur des corrects
            if (chaine_max.compareTo(depeches.get(i).getCategorie().toLowerCase()) == 0) {
                correct.set(k, new PaireChaineEntier(correct.get(k).getChaine(), correct.get(k).getEntiers() + 1));
            }
        }

        // Calcul des pourcentages et génération du rapport
        for (int l = 0; l < categories.size(); l++) {
            // Calcul du pourcentage en évitant la division entière
            double pourcentage = (double) correct.get(l).getEntiers() / nb_total.get(l) * 100;
            res += categories.get(l).getNom() + ": " + String.format("%.2f", pourcentage) + "%\n";
        }

        // Calcul de la moyenne des pourcentages
        double moyenne = UtilitairePaireChaineEntier.moyenne(correct);
        res += "MOYENNE: " + String.format("%.2f", moyenne) + "%";

        // Écriture dans le fichier
        try (FileWriter file = new FileWriter(nomFichier)) {
            file.write(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static ArrayList<PaireChaineEntier> initDico(ArrayList<Depeche> depeches, String categorie) {
        ArrayList<PaireChaineEntier> resultat = new ArrayList<>();
        return resultat;

    }

    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
    }

    public static int poidsPourScore(int score) {
        return 0;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {

    }

    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeches = lectureDepeches("./depeches.txt");

        /*for (int i = 0; i < depeches.size(); i++) {
            depeches.get(i).afficher();
        }
        Categorie sport = new Categorie("sport");
        sport.initLexique("lexique/sport.txt");*/
//        for (int i = 0; i < cate.getLexique().size(); i++) {
//            System.out.println(cate.getLexique().get(i));
//        }
//        Scanner lec = new Scanner(System.in);
//        System.out.print("Saisissez un mot : ");
//        String str = lec.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(sport.getLexique(),str));
//        System.out.println(sport.score(depeches.getLast()));
        ArrayList<PaireChaineEntier> theme = new ArrayList<>();
        theme.add(new PaireChaineEntier("sport",0));
        theme.add(new PaireChaineEntier("economie",0));
        theme.add(new PaireChaineEntier("culture",0));
        theme.add(new PaireChaineEntier("politique",0));
        theme.add(new PaireChaineEntier("science",0));
        ArrayList<Categorie> cate = new ArrayList<Categorie>();
        for (int i = 0; i<theme.size();i++){
            cate.add(new Categorie(theme.get(i).getChaine()));
            cate.get(i).initLexique("lexique/" + theme.get(i).getChaine() + ".txt");
        }
        classementDepeches(depeches,cate,"./resultat.txt");
    }


}

