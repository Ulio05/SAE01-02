import java.util.ArrayList;

public class KNN {
    public static ArrayList<Integer> Ressemble(ArrayList<Depeche> depeches, int j){
        ArrayList<Integer> resultat = new ArrayList<>();
        Depeche Exemple = depeches.get(j);
        for (int i = j+1;i<depeches.size();i++){
            if (KNN.scoreKNN(Exemple,depeches.get(i))>25)
                resultat.add(i);
        }
        return resultat;
    }
    public static int scoreKNN(Depeche d, Depeche dep) {
        int score = 0;
        for (int i = 2; i<d.getMots().size();i++){
            int j = 0;
            boolean trouv = false;
            while(j<dep.getMots().size()&&!trouv){
                if(d.getMots().get(i).toLowerCase().compareTo(dep.getMots().get(j))==0) {
                    score += 1;
                    trouv = true;
                }
                j++;
            }
        }
        return score;
    }
    public static String MoyCate(ArrayList<Depeche> depeche, ArrayList<PaireChaineEntier> categorie, ArrayList<Integer> vInt){

        for (Integer i : vInt){
            int ind = UtilitairePaireChaineEntier.entierPourChaine(categorie,depeche.get(i).getCategorie());
            categorie.get(ind).setEntiers(categorie.get(ind).getEntiers()+1);
        }
        return UtilitairePaireChaineEntier.chaineMax(categorie);

    }
    public static int plusPetitEnt(ArrayList<Integer> vInt){
        if (!vInt.isEmpty()){
            int res = vInt.getFirst(), j = 1;
            while (res + 1 == vInt.get(j)) {
                res++;
                j++;
            }
            return res;
        }
        return 0;
    }
    public static ArrayList<PaireChaineEntier> InitCategorie(ArrayList<Depeche> depeches) {
        ArrayList<PaireChaineEntier> categories = new ArrayList<>();
        for(Depeche unedepeche : depeches){
            int j = 0;
            boolean b = false;
            while (j < categories.size() && !b) {
                if (categories.get(j).getChaine().compareToIgnoreCase(unedepeche.getCategorie()) == 0)
                    b = true;
                j++;
            }
            if (!b)
                categories.add(new PaireChaineEntier(unedepeche.getCategorie().toLowerCase(),0));
        }
        return categories;
    }
    public static void main(String[] args) {
        ArrayList<Depeche> depeches = Classification.lectureDepeches("../autre jeu de données/depeches2.txt");
        ArrayList<PaireChaineEntier> cate = InitCategorie(depeches);
        long startTime = System.currentTimeMillis();
        ArrayList<Integer> lst_jum = Ressemble(depeches,0);

        for (int i = 0; i<cate.size();i++){
            String categorie =  MoyCate(depeches,cate,lst_jum);
            System.out.println("La catégorie pour les depeches : " + lst_jum + " est " + categorie);
            lst_jum = Ressemble(depeches,plusPetitEnt(lst_jum));
        }

        long endTime = System.currentTimeMillis();
        System.out.println("votre saisie a été réalisée en : " + (endTime-startTime) + " ms");
    }
}
