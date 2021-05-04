import java.util.ArrayList;

public class AgentMiniMax {

    private Dog maxDog; //minimax dog
    private Dog minDog; //a* dog
    private Environnement environnement;
    private Capteur capteur;
    private Effecteur effecteur;
    private ArrayList<Node> nodeList;
    private int maxDepth;

    public AgentMiniMax(Environnement environnement, Dog minDog, Dog maxDog, int maxDepth)
    {
        this.environnement = environnement;
        this.maxDog  = maxDog;
        this.minDog = minDog;

        capteur = new Capteur();
        effecteur = new Effecteur();
        this.maxDepth = maxDepth;
    }

    public void Resolution()
    {
        Enclos minimaxEnclos = new Enclos(
                environnement.enclosAdverse.x,
                environnement.enclosAdverse.y,
                environnement.enclosAdverse.color
        );
        Dog minimaxDog = new Dog(environnement.dogAdverse.maxSheepCarried,
                minimaxEnclos,
                environnement.dogAdverse.myColor,
                environnement.dogAdverse.enemyColor,
                environnement.dogAdverse.x,
                environnement.dogAdverse.y
        );
        minimaxDog.score = environnement.dogAdverse.score;
        minimaxDog.sheepCarried = environnement.dogAdverse.sheepCarried;

        Node initialNode = new Node(environnement, minimaxDog, 0);
        System.out.println("Launching MiniMax");
        Action chosenAction = MiniMax(initialNode, maxDepth);

        effecteur.Agir(chosenAction, maxDog, environnement);
        System.out.println("ActionApplied : "+chosenAction);

        System.out.println("Position minimaxDog "+ environnement.dogAdverse.x +":"+environnement.dogAdverse.y);
    }

    private Action MiniMax(Node initialNode, int maxDepth){
        System.out.print("Actions possibles pour MAX/RED : ");
        for(Action action : capteur.GetActionsPossibles(initialNode.activeDog, initialNode.environnement))
        {
            System.out.print(action +" ");
        }
        System.out.println();

        Pair tourMaxResult = TourMax(initialNode, maxDepth, initialNode.depth);

        Action chosenAction = (Action) tourMaxResult.getSecond();

        System.out.println("Got Action with Utility : "+tourMaxResult.getFirst());
        return chosenAction;

    }

    private Pair TourMax(Node node, int maxDepth, int currentDepth)
    {
        Pair result = new Pair(0, Action.NOTHING);

        if(currentDepth >= maxDepth || node.isFinalState){
            result.Put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MIN_VALUE;
        Action bestAction = null;


        Dog currentDog = node.environnement.dogAdverse;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        for(Action action : possibleActions){

            Node testNode = node.GenerateNextNode(action, node.environnement.dogAdverse, node.depth+1);

            Pair recursiveResult = TourMin(testNode, maxDepth, currentDepth + 1);

            int testUtility = (Integer) recursiveResult.getFirst();

            if(testUtility > bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }
        }

        result.Put(bestUtility, bestAction);
        return result;
    }

    private Pair TourMin(Node node, int maxDepth, int currentDepth)
    {
        Pair result = new Pair(0, Action.NOTHING);

        if(currentDepth >= maxDepth || node.isFinalState){
            result.Put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MAX_VALUE;
        Action bestAction = null;

        Dog currentDog = node.environnement.dogHeuristic;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        for(Action action : possibleActions){
            Node testNode = node.GenerateNextNode(action, node.environnement.dogHeuristic, node.depth+1);

            Pair recursiveResult = TourMax(testNode, maxDepth, currentDepth +1);

            int testUtility = (Integer) recursiveResult.getFirst();

            if( testUtility < bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }

        }
        result.Put(bestUtility, bestAction);
        return result;
    }

}
