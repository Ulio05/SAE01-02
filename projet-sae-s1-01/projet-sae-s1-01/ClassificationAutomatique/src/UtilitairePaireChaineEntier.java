import java.util.ArrayList;

public class UtilitairePaireChaineEntier {


    public static int indicePourChaine(ArrayList<PaireChaineEntier> listePaires, String chaine) {
        return 0;

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
        return 0;
    }

}
