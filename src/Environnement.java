import java.util.Random;

public class Environnement {

    public Room[][] map;
    public int size;
    public int remainingSheeps;
    public Enclos enclosHeuristic;
    public Enclos enclosAdverse;
    public Dog dogHeuristic;
    public Dog dogAdverse;

    /*
        Crée une instance de la classe pour copier un environnement par la suite
     */
    public Environnement(){
        remainingSheeps = 0;
    }

    /*
    Crée une instance de la classe et l'initialise
     */
    public Environnement(int n, int nbreSheep, Enclos enclosHeuristic, Enclos enclosAdverse, Dog dogHeuristic, Dog dogAdverse)
    {
        this.size = n;
        this.enclosHeuristic = enclosHeuristic;
        this.enclosAdverse = enclosAdverse;
        this.dogHeuristic = dogHeuristic;
        this.dogAdverse = dogAdverse;
        remainingSheeps = 0;

        map = new Room[n][n];
        SetupInitialState(nbreSheep);
    }

    /*
        Initialise un environnement
     */
    public void SetupInitialState(int nbreSheep)
    {
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);


        map[enclosHeuristic.x][enclosHeuristic.y].containsEnclos = true;
        map[enclosHeuristic.x][enclosHeuristic.y].color = enclosHeuristic.color;
        map[dogHeuristic.x][dogHeuristic.y].containsDog = true;

        map[enclosAdverse.x][enclosAdverse.y].containsEnclos = true;
        map[enclosAdverse.x][enclosAdverse.y].color = enclosAdverse.color;
        map[dogAdverse.x][dogAdverse.y].containsDog = true;

        SetupSheeps(nbreSheep);
    }

    /*
        Fait spawn des moutons a des positions aleatoires de la carte. Les moutons ne peuvent pas spawn sur les enclos.
        Il ne peut y avoir qu'un mouton max par case
     */
    public void SetupSheeps(int n)
    {

        Random rand = new Random();
        while (n > 0){

            Room room = map[rand.nextInt(size)][rand.nextInt(size)];
            if(!room.containsSheep && !room.containsEnclos){

                room.containsSheep = true;
                remainingSheeps ++;
                n--;
            }
        }
    }

    /*
        Test l'etat final. Dans ce probleme un etat est considéré comme final s'il ne reste aucun mouton en liberté sur
        le terrain et qu'aucun ne porte encore de moutons. Si un chien n'est pas encore rentré dans son enclos mais qu'il
        ne porte par de mouton, l'etat est considéré comme final (on autorise le chien à s'endormir dans la prairie)
     */
    public boolean MatchEnded(){
        return remainingSheeps == 0 && dogHeuristic.sheepCarried == 0 && dogAdverse.sheepCarried == 0;
    }
}

