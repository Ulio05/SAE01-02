import java.util.ArrayList;

public class KNN {

    // Méthode pour trouver les indices des dépêches similaires à une dépêche donnée
    public static ArrayList<Integer> Ressemble(ArrayList<Depeche> depeches, int index) {
        ArrayList<Integer> resultat = new ArrayList<>();
        Depeche exemple = depeches.get(index);

        for (int i = index + 1; i < depeches.size(); i++) {
            if (scoreKNN(exemple, depeches.get(i)) > 2) {
                resultat.add(i);
            }
        }
        return resultat;
    }

    // Méthode pour calculer un score de similarité entre deux dépêches
    public static int scoreKNN(Depeche d1, Depeche d2) {
        int score = 0;
        for (String mot1 : d1.getMots()) {
            for (String mot2 : d2.getMots()) {
                if (mot1.equalsIgnoreCase(mot2)) {
                    score++;
                    break;
                }
            }
        }
        return score;
    }

    // Méthode pour trouver la catégorie la plus représentée
    public static String MoyCate(ArrayList<Depeche> depeches, ArrayList<PaireChaineEntier> categories, ArrayList<Integer> indices) {
        for (int i : indices) {
            int indexCategorie = UtilitairePaireChaineEntier.entierPourChaine(categories, depeches.get(i).getCategorie());
            if (indexCategorie != -1) {
                PaireChaineEntier paire = categories.get(indexCategorie);
                paire.setEntiers(paire.getEntiers() + 1);
            }
        }
        return UtilitairePaireChaineEntier.chaineMax(categories);
    }

    // Méthode pour trouver le plus petit entier manquant dans une liste triée
    public static int plusPetitEnt(ArrayList<Integer> indices) {
        if (indices == null || indices.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < indices.size() - 1; i++) {
            if (indices.get(i) + 1 != indices.get(i + 1)) {
                return indices.get(i) + 1;
            }
        }

        return indices.get(indices.size() - 1) + 1;
    }

    // Méthode pour initialiser les catégories uniques des dépêches
    public static ArrayList<PaireChaineEntier> InitCategorie(ArrayList<Depeche> depeches) {
        ArrayList<PaireChaineEntier> categories = new ArrayList<>();

        for (Depeche dep : depeches) {
            boolean existe = false;
            for (PaireChaineEntier categorie : categories) {
                if (categorie.getChaine().equalsIgnoreCase(dep.getCategorie())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                categories.add(new PaireChaineEntier(dep.getCategorie().toLowerCase(), 0));
            }
        }
        return categories;
    }
    public static void afficherResultats(ArrayList<Depeche> depeches, ArrayList<PaireChaineEntier> categories) {
        ArrayList<Integer> listeJumelages = Ressemble(depeches, 0);

        while (!listeJumelages.isEmpty()) {
            String categorie = MoyCate(depeches, categories, listeJumelages);
            System.out.println("La catégorie pour les dépêches : " + listeJumelages + " est " + categorie);
            listeJumelages = Ressemble(depeches, plusPetitEnt(listeJumelages));
        }
    }

    // Point d'entrée du programme
    public static void main(String[] args) {
        ArrayList<Depeche> depeches = Classification.lectureDepeches("./depeches.txt");
        ArrayList<PaireChaineEntier> categories = InitCategorie(depeches);

        long startTime = System.currentTimeMillis();
        afficherResultats(depeches, categories);
        long endTime = System.currentTimeMillis();
        System.out.println("Votre traitement a été réalisé en : " + (endTime - startTime) + " ms");
    }
}
