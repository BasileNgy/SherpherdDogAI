public class Room implements Cloneable{

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
        color = DogColor.BLACK;
    }
    public Object clone(){
        try
        {
            Room obj = (Room) super.clone();
            obj.graphicText = "";
            return obj;
        }
        catch (CloneNotSupportedException x)
        {
            return new InternalError("N'arrive jamais");
        }
    }

}
