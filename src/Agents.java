public class Agents {

    private AgentAStar agentAStar;
//    private AgentAStar agentAStar2;
    private AgentMiniMax agentMiniMax;
    private Environnement environnement;
    private Graphic graphic;
    private Dog previousDogMoving;

    public Agents(Environnement environnement)
    {
        this.environnement = environnement;
        agentAStar = new AgentAStar(environnement, environnement.dogAStar);
//        agentAStar2 = new AgentAStar(environnement, environnement.dogMiniMax);
        agentMiniMax = new AgentMiniMax(environnement, environnement.dogAStar, environnement.dogMiniMax);
        System.out.println("A* Dog en haut à gauche");
        System.out.println("MiniMax Dog en bas à droite\n");
        previousDogMoving = environnement.dogMiniMax;
    }

    public void SetGraphicParameter(Graphic graph)
    {
        this.graphic = graph;
    }

    public void ResolutionAlgorithms()
    {

        if(environnement.MatchEnded()){
            System.out.println("Match done !");
            System.out.println("Score : Dog 1 ["+environnement.dogAStar.score+"] - Dog 2 ["+environnement.dogMiniMax.score+"]");
            if(environnement.dogAStar.score == environnement.dogMiniMax.score){
                System.out.println("Draw");
                return;
            }
            String winner = (environnement.dogAStar.score > environnement.dogMiniMax.score) ? "Dog 1" :  "Dog 2";
            System.out.println("Winner : " + winner);
            return;
        }
        if(previousDogMoving == environnement.dogMiniMax){
            System.out.println("Begin A* Algorithm");
            agentAStar.Resolution();
            System.out.print("\n");
            previousDogMoving = environnement.dogAStar;
        }
        else {
            System.out.println("Begin MiniMax Algorithm");
            agentMiniMax.Resolution();
            System.out.print("\n");
            previousDogMoving = environnement.dogMiniMax;
        }

        graphic.UpdateGraphic(environnement);
    }


}
