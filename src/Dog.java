enum DogColor{
    BLUE,RED,BLACK
}

public class Dog {

    public int x,y;
    public Enclos enclos;
    public int maxSheepCarried;
    public int sheepCarried;
    public int score;
    public DogColor myColor;
    public DogColor enemyColor;

    public Dog(int maxSheepCarried, Enclos enclos, DogColor myColor, DogColor enemyColor, int x, int y)
    {
        this.maxSheepCarried = maxSheepCarried;
        this.enclos = enclos;
        sheepCarried = 0;
        score = 0;
        this.x = x;
        this.y = y;
        this.myColor = myColor;
        this.enemyColor = enemyColor;
    }

    public boolean AmIAtEnclos(){
        return (x == enclos.x && y == enclos.y) ? true : false;
    }
}
