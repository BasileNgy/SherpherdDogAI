enum DogColor{
    BLUE,RED
}

public class Dog {

    public int x,y;
    public Enclos enclos;
    public int maxSheepCarried;
    public int sheepCarried;
    public int score;
    public Room[][] map;
    public DogColor color;

    public Dog(int maxSheepCarried, Enclos enclos, Room[][] map)
    {
        this.maxSheepCarried = maxSheepCarried;
        this.enclos = enclos;
        this.map = map;
        sheepCarried = 0;
        score = 0;
        x = enclos.x;
        y = enclos.y;
        color = enclos.color;
        map[x][y].containsDog = true;
        map[x][y].containsEnclos = true;
        map[x][y].color = enclos.color;
    }
}
