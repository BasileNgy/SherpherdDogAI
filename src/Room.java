public class Room {

    public int x,y;
    public String graphicText = "";
    public boolean containsSheep;
    public boolean containsEnclos;
    public boolean containsDog;
    public DogColor color;

    public Room(int x, int y)
    {
        this.x = x;
        this.y = y;
        containsSheep = false;
        containsEnclos = false;
        containsDog = false;
    }
}
