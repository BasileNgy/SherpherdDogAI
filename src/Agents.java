public class Agents {

    private AgentAStar agentAStar;
    private Dog dog2;
    private Environnement environnement;
    private Graphic graphic;

    public Agents(Dog dog1, Dog dog2, Environnement environnement)
    {
        agentAStar = new AgentAStar(dog1, environnement);
        this.dog2 = dog2;
        this.environnement = environnement;
        System.out.println("A* Dog en haut à gauche");
        System.out.println("MiniMax Dog en bas à droite\n");
    }

    public void SetGraphicParameter(Graphic graph)
    {
        this.graphic = graph;
    }

    public void ResolutionAlgorithms()
    {
        System.out.println("Begin A* Algorithm");
        agentAStar.Resolution();
        System.out.print("\n");
        graphic.UpdateGraphic(environnement);
    }





    //Pour dog1
    public void algoMiniMax()
    {
//      action = tourMax(n)
    }
/*
    public void tourMax(Noeud n)
    {
       vérifier qu'un état final n'est pas atteint sinon on retourne l'utilité

       on prends l'ensemble des actions possibles de dog1
            on récupére laction avec la plus haute utilité retourné par TourMin

       return actionChoisie la plus profitable a dog1
    }*/

/*    public void tourMin(Noeud n)
    {
       vérifier qu'un état final n'est pas atteint sinon on retourne l'utilité

       on prends l'ensemble des actions possibles de dog2
            on récupére laction avec la plus faible utilité retourné par TourMax

       return actionChoisie la plus profitable a dog1
    }*/


}
