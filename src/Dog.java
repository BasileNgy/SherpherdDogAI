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

    public Dog(int maxSheepCarried, Enclos enclos, DogColor myColor, DogColor enemyColor)
    {
        this.maxSheepCarried = maxSheepCarried;
        this.enclos = enclos;
        sheepCarried = 0;
        score = 0;
        x = enclos.x;
        y = enclos.y;
        this.myColor = myColor;
        this.enemyColor = enemyColor;
    }

    public boolean AmIAtEnclos(){
        return (x == enclos.x && y == enclos.y) ? true : false;
    }
}
