public class Enclos /*implements Cloneable*/{

    public int x,y;
    public int sheepsBrought;
    public DogColor color;

    public Enclos(int x, int y, DogColor color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        sheepsBrought = 0;
    }

    /*public Object clone(){
        try{
            Enclos obj = (Enclos) super.clone();
            return obj;
        }catch (CloneNotSupportedException e){
            return new InternalError("Impossible to clone Enclos' instance");
        }
    }*/
}
