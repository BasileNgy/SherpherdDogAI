public class Agents {

    private AgentGreedy agentGreedy;
//    private AgentMiniMax agentMiniMax;
    private AgentAlphaBeta agentAlphaBeta;
    private Environnement environnement;
    private Graphic graphic;
    private Dog previousDogMoving;

    public Agents(Environnement environnement)
    {
        this.environnement = environnement;
        agentGreedy = new AgentGreedy(environnement, environnement.dogHeuristic);
//        agentMiniMax = new AgentMiniMax(environnement, environnement.dogMiniMax, 12);
        agentAlphaBeta = new AgentAlphaBeta(environnement, environnement.dogAdverse, 22);
        System.out.println("A* Dog en haut à gauche");
        System.out.println("MiniMax Dog en bas à droite\n");
        previousDogMoving = environnement.dogAdverse;
    }

    public void SetGraphicParameter(Graphic graph)
    {
        this.graphic = graph;
    }

    public void ResolutionAlgorithms()
    {

        if(environnement.MatchEnded()){
            System.out.println("Match done !");
            System.out.println("Score : Dog 1 ["+environnement.dogHeuristic.score+"] - Dog 2 ["+environnement.dogAdverse.score+"]");
            if(environnement.dogHeuristic.score == environnement.dogAdverse.score){
                System.out.println("Draw");
                return;
            }
            String winner = (environnement.dogHeuristic.score > environnement.dogAdverse.score) ? "Dog 1" :  "Dog 2";
            System.out.println("Winner : " + winner);
            return;
        }
        if(previousDogMoving == environnement.dogAdverse){
            System.out.println("Begin greedy Algorithm");
            agentGreedy.Resolution();
            System.out.print("\n");
            previousDogMoving = environnement.dogHeuristic;
        }
        else {
//            System.out.println("Begin MiniMax Algorithm");
//            agentMiniMax.Resolution();
            System.out.println("Begin alpha beta Algorithm");
            agentAlphaBeta.Resolution();

            System.out.println("MinElagage : "+agentAlphaBeta.minElagage + " MaxElagage : "+ agentAlphaBeta.maxElagage +"\n");

            previousDogMoving = environnement.dogAdverse;
        }

        graphic.UpdateGraphic(environnement);
    }


}
