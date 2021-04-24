import java.util.Random;

public class Environnement {

    public Room[][] map;
    public int size;

    public Environnement(int n, int nbreSheep)
    {
        this.size = n;

        map = new Room[n][n];
        SetupInitialState(nbreSheep);
    }

    public void SetupInitialState(int nbreSheep)
    {
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);

        SetupSheeps(nbreSheep);
    }

    public void SetupSheeps(int n)
    {
        Random rand = new Random();
        while (n > 0){
            Room room = map[rand.nextInt(size)][rand.nextInt(size)];
            if( !room.containsSheep && !room.containsEnclos){
                room.containsSheep = true;
                n--;
            }
        }
    }
}
