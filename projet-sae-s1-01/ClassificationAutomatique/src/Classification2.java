import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Classification2 {


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
                Depeche unedepeche = new Depeche(id, date, categorie, lignes);
                depeches.add(unedepeche);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depeches;
    }

    public static ArrayList<Categorie> InitCategorie(ArrayList<Depeche> depeches) {
        ArrayList<Categorie> categories = new ArrayList<>();
        for(Depeche unedepeche : depeches){
            int j = 0;
            boolean b = false;
            while (j < categories.size() && !b) {
                if (categories.get(j).getNom().compareToIgnoreCase(unedepeche.getCategorie()) == 0)
                    b = true;
                j++;
            }
            if (!b)
                categories.add(new Categorie(unedepeche.getCategorie().toLowerCase()));
        }
        return categories;
    }

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
                correct.get(k).setEntiers(correct.get(k).getEntiers() + 1);
            }
        }

        // Calcul des pourcentages et génération du rapport
        for (int l = 0; l < categories.size(); l++) {
            // Calcul du pourcentage en évitant la division entière
            double pourcentage = (double) correct.get(l).getEntiers() / nb_total.get(l)  * 100;
            res += categories.get(l).getNom() + ": " + String.format("%.2f", pourcentage) + "%\n";
            correct.get(l).setEntiers((int)pourcentage);
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
        for (Depeche dep : depeches) {
            if (categorie.compareTo(dep.getCategorie().toLowerCase())==0) {
                for (String chaine: dep.getMots()){
                    if (UtilitairePaireChaineEntier.indicePourChaine(resultat,chaine) == -1){
                        resultat.add(new PaireChaineEntier(chaine,0));
                    }
                }
            }
        }
        triFusion(resultat,0,resultat.size()-1);
        return resultat;
    }

    public static void fusionTabGTabD(ArrayList<PaireChaineEntier> vInt, int inf, int m, int sup) {
        ArrayList<PaireChaineEntier> temp = new ArrayList<>();
        int p1 = inf, p2 = m + 1;

        // Merge process
        while (p1 <= m && p2 <= sup) {
            if (vInt.get(p1).getChaine().compareToIgnoreCase(vInt.get(p2).getChaine()) < 0) {
                temp.add(vInt.get(p1));
                p1++;
            } else {
                temp.add(vInt.get(p2));
                p2++;
            }
        }

        // Add remaining elements from the left half (if any)
        while (p1 <= m) {
            temp.add(vInt.get(p1));
            p1++;
        }

        // Add remaining elements from the right half (if any)
        while (p2 <= sup) {
            temp.add(vInt.get(p2));
            p2++;
        }

        // Copy the sorted elements back to the original list
        for (int i = 0; i < temp.size(); i++) {
            vInt.set(i + inf, temp.get(i));
        }
    }

    public static void triFusion(ArrayList<PaireChaineEntier> vInt, int inf, int sup) {
        if (inf < sup) {
            int m = (inf + sup) / 2;
            triFusion(vInt, inf, m);  // Sort the left half
            triFusion(vInt, m + 1, sup);  // Sort the right half
            fusionTabGTabD(vInt, inf, m, sup);  // Merge the two sorted halves
        }
    }


    public static void calculScores(ArrayList<Depeche> depeches, String categorie, ArrayList<PaireChaineEntier> dictionnaire) {
        for (Depeche dep: depeches){

            for(String chaine : dep.getMots()){
                int inf = 0, sup = dictionnaire.size(),m;
                boolean b = false;
                while(inf<sup && !b){
                    m = (inf + sup)/2;
                    if (dictionnaire.get(m).getChaine().compareToIgnoreCase(chaine)==0){
                        if(dep.getCategorie().compareToIgnoreCase(categorie)==0)
                            dictionnaire.get(m).setEntiers(dictionnaire.get(m).getEntiers()+1);
                        else
                            dictionnaire.get(m).setEntiers(dictionnaire.get(m).getEntiers()-1);
                        b=true;
                    }else if (dictionnaire.get(m).getChaine().compareToIgnoreCase(chaine)<0)
                        inf = m+1;
                    else
                        sup = m -1;
                }
            }
        }
    }

    public static int poidsPourScore(int score) {
        if (score>4){
            return 3;
        } else if (score>0) {
            return 2;
        }else
            return 1;
    }

    public static void generationLexique(ArrayList<Depeche> depeches, String categorie, String nomFichier) {
        String res = "";
        ArrayList<PaireChaineEntier> dictionnaire = initDico(depeches,categorie);
        if(!dictionnaire.isEmpty()){
            calculScores(depeches, categorie, dictionnaire);
            res += dictionnaire.getFirst().getChaine() + ":" + dictionnaire.getFirst().getEntiers();
            for (int i = 1; i < dictionnaire.size(); i++) {
                res += '\n' + dictionnaire.get(i).getChaine() + ":" + poidsPourScore(dictionnaire.get(i).getEntiers());
            }
            try {
                FileWriter file = new FileWriter(nomFichier);
                file.write(res);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {

        //Chargement des dépêches en mémoire
        System.out.println("chargement des dépêches");
        ArrayList<Depeche> depeche = lectureDepeches("./depeches.txt");
        ArrayList<Depeche> depeches2 = lectureDepeches("../autre jeu de données/depeches2.txt");


        ArrayList<Categorie> cate = InitCategorie(depeches2);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i<cate.size();i++){
            generationLexique(depeche,cate.get(i).getNom(),"lexiqueIA/"+cate.get(i).getNom()+".txt");
        }
        for (int i = 0; i<cate.size();i++){
            cate.get(i).initLexique("lexiqueIA/" + cate.get(i).getNom() + ".txt");
        }
        classementDepeches(depeches2,cate,"./resultatIA.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("votre saisie a été réalisée en : " + (endTime-startTime) + " ms");
    }


}