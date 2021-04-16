public class Environnement {

    public Room[][] map;
    private int size;

    public Environnement(int n, int nbreSheep)
    {
        this.size = n;

        SetupInitialState(nbreSheep);
    }

    public void SetupInitialState(int nbreSheep)
    {
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);
    }
}
