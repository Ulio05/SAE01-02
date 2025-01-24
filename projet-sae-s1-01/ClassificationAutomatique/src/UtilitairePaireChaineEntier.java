import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int res = -1;
        int i = 0;
        while (i<listePaires.size()&& res == -1){
            if (listePaires.get(i).getChaine().compareTo(chaine)==0)
                res = i;
            i++;
        }
        return res;
    }
    public static int indicePourChaineOpti(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int inf = 0, sup = listePaires.size() - 1;

        while (inf < sup) {
            int m = (inf + sup) / 2;
            int comparaison = listePaires.get(m).getChaine().compareToIgnoreCase(chaine);

            if (comparaison == 0) {
                return m;  // La chaîne a été trouvée à l'indice m
            } else if (comparaison < 0) {
                inf = m + 1;  // La chaîne est plus grande, on cherche à droite
            } else {
                sup = m - 1;  // La chaîne est plus petite, on cherche à gauche
            }
        }

        return -1;  // La chaîne n'a pas été trouvée
    }


    public static int entierPourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        int res = 0;
        for (int i = 0; listePaires.size()>i;i++){
            if(listePaires.get(i).getChaine().compareTo(chaine)==0)
                res = listePaires.get(i).getEntiers();
        }
        return res;
    }

    public static String chaineMax(ArrayList<PaireChaineEntier> listePaires) {
        String max = listePaires.get(0).getChaine();
        int ind_max = listePaires.get(0).getEntiers();
        for (int i = 1; i< listePaires.size();i++){
            if (ind_max<listePaires.get(i).getEntiers()){
                max = listePaires.get(i).getChaine();
                ind_max = listePaires.get(i).getEntiers();
            }
        }
        return max;
    }


    public static float moyenne(ArrayList<PaireChaineEntier> listePaires) {
        float moy = 0;
        for (int i = 0; i<listePaires.size();i++){
            moy+=listePaires.get(i).getEntiers();
        }
        return moy/listePaires.size();
    }

}
