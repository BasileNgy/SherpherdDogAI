public class Main {

    public static void main(String[] args)
    {
        Enclos enclosAStar = new Enclos(0,0, DogColor.BLUE);
        Enclos enclosMiniMax = new Enclos(2,2, DogColor.RED);
        Dog dog1 = new Dog(1,enclosAStar, enclosAStar.color, enclosMiniMax.color);
        Dog dog2 = new Dog(1,enclosMiniMax, enclosMiniMax.color, enclosAStar.color);
        Environnement envir = new Environnement(3, 1, enclosAStar, enclosMiniMax, dog1, dog2);
        Agents agentsManager = new Agents(envir);
        Graphic graph = new Graphic(envir, agentsManager);
        agentsManager.SetGraphicParameter(graph);
    }
}
