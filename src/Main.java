public class Main {

    public static void main(String[] args)
    {
        int n = 3;
        Enclos enclosAStar = new Enclos(0,0, DogColor.BLUE);
        Enclos enclosMiniMax = new Enclos(n-1,n-1, DogColor.RED);

        Dog dog1 = new Dog(3,enclosAStar, enclosAStar.color, enclosMiniMax.color, enclosAStar.x, enclosAStar.y);
        Dog dog2 = new Dog(3,enclosMiniMax, enclosMiniMax.color, enclosAStar.color, enclosMiniMax.x, enclosMiniMax.y);

        Environnement envir = new Environnement(n, 3, enclosAStar, enclosMiniMax, dog1, dog2);

        Agents agentsManager = new Agents(envir);

        Graphic graph = new Graphic(envir, agentsManager);

        agentsManager.SetGraphicParameter(graph);
    }
}
