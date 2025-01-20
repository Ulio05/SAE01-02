public class PaireChaineEntier {
    private String chaine;
    private int entiers;

    public String getChaine() {
        return chaine;
    }

    public void setChaine(String chaine) {
        this.chaine = chaine;
    }

    public int getEntiers() {
        return entiers;
    }

    public void setEntiers(int entiers) {
        this.entiers = entiers;
    }
    public PaireChaineEntier(String str, int ent){
        chaine=str;
        entiers = ent;
    }
    @Override
    public String toString(){
        return chaine + " : " +entiers;
    }
}
