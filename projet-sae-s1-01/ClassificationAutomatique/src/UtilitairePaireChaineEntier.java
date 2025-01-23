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
        int inf = 0, sup = listePaires.size(),m =0;
        boolean b = false;
        while(inf<sup && !b) {
            m = (inf + sup) / 2;
            if (listePaires.get(m).getChaine().compareToIgnoreCase(chaine) == 0)
                b = true;
            else if (listePaires.get(m).getChaine().compareToIgnoreCase(chaine) < 0)
                inf = m + 1;
            else
                sup = m - 1;
        }
        if (b)
            return m;
        else
            return -1;
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
