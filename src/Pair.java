public class Pair {
    int utility;
    Action action;

    public Pair(int utility, Action action){
        this.utility = utility;
        this.action = action;
    }

    public int getUtility() {
        return utility;
    }

    public Action getAction() {
        return action;
    }

    public void Put(int utility, Action action){
        this.utility = utility;
        this.action = action;
    }
}
