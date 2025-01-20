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


    public static void classementDepeches(ArrayList<Depeche> depeches, ArrayList<Categorie> categories, String nomFichier) {
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
        }*/
        Categorie sport = new Categorie("sport");
        sport.initLexique("lexique/sport.txt");
//        for (int i = 0; i < cate.getLexique().size(); i++) {
//            System.out.println(cate.getLexique().get(i));
//        }
//        Scanner lec = new Scanner(System.in);
//        System.out.print("Saisissez un mot : ");
//        String str = lec.nextLine();
//        System.out.println(UtilitairePaireChaineEntier.entierPourChaine(sport.getLexique(),str));
//        System.out.println(sport.score(depeches.getLast()));
        ArrayList<String> theme = new ArrayList<>();
        theme.add("sport");
        theme.add("economie");
        theme.add("culture");
        theme.add("politique");
        theme.add("science");
        ArrayList<Categorie> cate = new ArrayList<Categorie>();
        ArrayList<PaireChaineEntier> score_cate = new ArrayList<PaireChaineEntier>();
        for (int i = 0; i<theme.size();i++){
            cate.add(new Categorie(theme.get(i)));
            cate.get(i).initLexique("lexique/" + theme.get(i) + ".txt");
            score_cate.add(new PaireChaineEntier(theme.get(i),cate.get(i).score(depeches.get(0))));
        }
        System.out.println(UtilitairePaireChaineEntier.chaineMax(score_cate));

    }


}

