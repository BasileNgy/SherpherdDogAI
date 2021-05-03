import java.util.Random;

public class Environnement {

    public Room[][] map;
    public int size;
    public int remainingSheeps;
    public Enclos enclosDogAStar;
    public Enclos enclosDogMiniMax;
    public Dog dogAStar;
    public Dog dogMiniMax;

    public Environnement(){
        remainingSheeps = 0;
    }
    public Environnement(int n, int nbreSheep, Enclos enclosDogAStar, Enclos enclosDogMiniMax, Dog dogAStar, Dog dogMiniMax)
    {
        this.size = n;
        this.enclosDogAStar = enclosDogAStar;
        this.enclosDogMiniMax = enclosDogMiniMax;
        this.dogAStar = dogAStar;
        this.dogMiniMax = dogMiniMax;
        remainingSheeps = 0;

        map = new Room[n][n];
        SetupInitialState(nbreSheep);
    }

    public void SetupInitialState(int nbreSheep)
    {
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);


        map[enclosDogAStar.x][enclosDogAStar.y].containsEnclos = true;
        map[enclosDogAStar.x][enclosDogAStar.y].color = enclosDogAStar.color;
        map[dogAStar.x][dogAStar.y].containsDog = true;

        map[enclosDogMiniMax.x][enclosDogMiniMax.y].containsEnclos = true;
        map[enclosDogMiniMax.x][enclosDogMiniMax.y].color = enclosDogMiniMax.color;
        map[dogMiniMax.x][dogMiniMax.y].containsDog = true;

        SetupSheeps(nbreSheep);
    }

    public void SetupSheeps(int n)
    {/*
        Random rand = new Random();
        while (n > 0){
            Room room = map[rand.nextInt(size)][rand.nextInt(size)];
            if(!room.containsSheep && !room.containsEnclos){
                room.containsSheep = true;
                remainingSheeps ++;
                n--;
            }
        }*/
        //map[2][0].containsSheep = true;
        //map[1][1].containsSheep = true;
        map[1][2].containsSheep = true;
        remainingSheeps ++;
    }

    public boolean MatchEnded(){
        return remainingSheeps == 0 && dogAStar.sheepCarried == 0 && dogMiniMax.sheepCarried == 0;
    }
}

