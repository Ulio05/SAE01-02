import java.util.ArrayList;

public class KNN {
    public static ArrayList<Integer> Ressemble(ArrayList<Depeche> depeches){
        ArrayList<Integer> resultat = new ArrayList<>();
        for (int i = 1;i<depeches.size();i++){
            if (KNN.scoreKNN(depeches.getFirst(),depeches.get(i))>25)
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
    public static
}
