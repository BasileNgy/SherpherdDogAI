public class Node {

    public Room[][] map;
    public int remainingSheeps;
    public int size;
    public int maxDogX;
    public int maxDogY;
    public int minDogX;
    public int minDogY;
    public boolean isFinalState;
    public int utility;

    public Node(Room[][] map, int size, Dog maxDog, Dog minDog)
    {
        this.map = map.clone();
        this.maxDogX = maxDog.x;
        this.maxDogY = maxDog.y;
        this.minDogX = minDog.x;
        this.minDogY = minDog.y;

        this.size = size;
        isFinalState = false;
        remainingSheeps = 0;
        utility = 0;
        getRemainingSheep();

        if(remainingSheeps == 0 && maxDog.sheepCarried == 0 && minDog.sheepCarried == 0)
        {
            isFinalState = true;
            utility = maxDog.score - minDog.score;
        }

    }

    public void getRemainingSheep()
    {
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                if(map[i][j].containsSheep)
                    remainingSheeps++;
    }

}
