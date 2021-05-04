
/*
    Cette classe nous permet de sauvegarder une paire d'objets.
    On l'utilise dans Minimax et AlphaBeta pour sauvegarder l'utilité associée à un noeud ainsi que l'action qui a
    permis de le générer.
 */
public class Pair <S,T>{
    private S first;
    private T second;

    public Pair(S first, T second){
        this.first = first;
        this.second = second;
    }


    public void Put(S first, T second){
        this.first = first;
        this.second = second;
    }

    public S getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
