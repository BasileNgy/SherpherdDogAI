import java.util.ArrayList;
import java.util.HashMap;

public class AgentMiniMax {

    private Dog maxDog; //minimax dog
    private Dog minDog; //a* dog
    private Environnement environnement;
    private Capteur capteur;
    private Effecteur effecteur;
    private ArrayList<Node> nodeList;

    public AgentMiniMax(Environnement environnement, Dog minDog, Dog maxDog)
    {
        this.environnement = environnement;
        this.maxDog  = maxDog;
        this.minDog = minDog;

        capteur = new Capteur();
        effecteur = new Effecteur();
    }

    public void Resolution()
    {
        System.out.println("Initializing node");
        Node initialNode = new Node(environnement, maxDog);
        System.out.println("Launching MiniMax");
        Action chosenAction = MiniMax(initialNode);
        System.out.println("MiniMax Done");
        effecteur.Agir(chosenAction, maxDog, environnement);
        System.out.println("ActionApplied");
    }

    private Action MiniMax(Node initialNode){
        System.out.println("Launching TourMax from Minimax");
        HashMap<Integer, Action> tourMaxResult = TourMax(initialNode);
        System.out.println("Getting Action");
        Action chosenAction = tourMaxResult.values().stream().findFirst().get();
        System.out.println("Got Action");
        return chosenAction;

    }
    private HashMap<Integer, Action> TourMax(Node node)
    {
        HashMap<Integer, Action> result = new HashMap<>();
        if(node.isFinalState){
            System.out.println("[max] Found Final State");
            result.put(node.utility, Action.SLEEP);
            return result;
        }

        int bestUtility = Integer.MIN_VALUE;
        Action bestAction = null;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(maxDog, environnement);
        System.out.println("[max] Got Possible Actions");
        possibleActions.add(Action.CATCH);
        possibleActions.add(Action.RELEASE);
        possibleActions.add(Action.SLEEP);
        int count = 0;
        for(Action action : possibleActions){
            count++;
            System.out.println("[max] Action "+count);
            Node testNode = node.GenerateNextNode(action, maxDog);
            System.out.println("[max] Test node Generated, launching TourMin");
            HashMap<Integer, Action> recursiveResult = TourMin(testNode);
            System.out.println("[max] TourMin Done");
            int testUtility = recursiveResult.keySet().stream().findFirst().get();
            Action testAction = recursiveResult.values().stream().findFirst().get();
            if( testUtility > bestUtility) {
                System.out.println("[max] test accepted");
                bestAction = testAction;
                bestUtility = testUtility;
            }
            else{
                System.out.println("[max] test not accepted");
            }
        }

        result.put(bestUtility, bestAction);
        return result;
    }

    private HashMap<Integer, Action> TourMin(Node node)
    {
        HashMap<Integer, Action> result = new HashMap<>();
        if(node.isFinalState){
            System.out.println("[min] Found Final State");
            result.put(node.utility, Action.SLEEP);
            return result;
        }

        int bestUtility = Integer.MAX_VALUE;
        Action bestAction = null;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(minDog, environnement);
        System.out.println("[min] Got Possible Actions");
        possibleActions.add(Action.CATCH);
        possibleActions.add(Action.RELEASE);
        possibleActions.add(Action.SLEEP);
        int count = 0;
        for(Action action : possibleActions){
            count++;
            System.out.println("[min] Action "+count);
            Node testNode = node.GenerateNextNode(action, minDog);
            System.out.println("[min] Test node Generated, launching TourMax");
            HashMap<Integer, Action> recursiveResult = TourMax(testNode);
            System.out.println("[min] TourMax Done");
            int testUtility = recursiveResult.keySet().stream().findFirst().get();
            Action testAction = recursiveResult.values().stream().findFirst().get();
            if( testUtility < bestUtility) {
                System.out.println("[min] test accepted");
                bestAction = testAction;
                bestUtility = testUtility;
            }
            else{
                System.out.println("[min] test not accepted");
            }

        }

        result.put(bestUtility, bestAction);
        return result;
    }
}
