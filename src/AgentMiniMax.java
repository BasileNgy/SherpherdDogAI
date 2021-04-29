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

        Enclos minimaxEnclos = new Enclos(
                environnement.enclosDogMiniMax.x,
                environnement.enclosDogMiniMax.y,
                environnement.enclosDogMiniMax.color
        );
        Dog minimaxDog = new Dog(environnement.dogMiniMax.maxSheepCarried,
                minimaxEnclos,
                environnement.dogMiniMax.myColor,
                environnement.dogMiniMax.enemyColor,
                environnement.dogMiniMax.x,
                environnement.dogMiniMax.y
        );

        Node initialNode = new Node(environnement, minimaxDog);
        System.out.println("Launching MiniMax");
        Action chosenAction = MiniMax(initialNode);

        System.out.println("MiniMax Done");
        effecteur.Agir(chosenAction, minimaxDog, environnement);

        System.out.println("ActionApplied : "+chosenAction);
    }

    private Action MiniMax(Node initialNode){
        HashMap<Integer, Action> tourMaxResult = TourMax(initialNode, 5, 0);

        System.out.println("Getting Action");
        Action chosenAction = tourMaxResult.values().stream().findFirst().get();

        System.out.println("Got Action");
        return chosenAction;

    }
    private HashMap<Integer, Action> TourMax(Node node, int maxDepth, int currentDepth)
    {
        if(currentDepth >= maxDepth)
            return null;
        HashMap<Integer, Action> result = new HashMap<>();
        if(node.isFinalState){
            System.out.println("[max] Found Final State");
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MIN_VALUE;
        Action bestAction = null;


        Dog currentDog = node.environnement.dogMiniMax;
        ArrayList<Action> possibleActions = new ArrayList<>();



        System.out.println("Depth : "+currentDepth+" [max] Current dog ("+currentDog.myColor+") position : "+currentDog.x+","+currentDog.y);

        if(!capteur.IsThereRemainingSheeps(node.environnement) && currentDog.sheepCarried == 0)
            possibleActions.add(Action.SLEEP);
        if(currentDog.AmIAtEnclos() && currentDog.sheepCarried > 0)
            possibleActions.add(Action.RELEASE);
        if(node.environnement.map[currentDog.x][currentDog.y].containsSheep)
            possibleActions.add(Action.CATCH);
        possibleActions.addAll(capteur.GetActionsPossibles(currentDog, node.environnement));
//        System.out.println("[max] Got Possible Actions");
//        System.out.println(currentDog.myColor+" dog int position ["+currentDog.x+","+currentDog.y+"]");

        int count = 0;
//        for (Action action : possibleActions ) {
//            count++;
//            System.out.println("Action "+count+" : "+action);
//        }
//        count = 0 ;

        for(Action action : possibleActions){
            count++;
//            System.out.println("[max] Action "+count);

            System.out.println("Depth : "+currentDepth+" Action " +count + " [max] Generate node with Action : "+action+" Dog : " + node.environnement.dogMiniMax.myColor+" "+node.environnement.dogMiniMax.x+","+node.environnement.dogMiniMax.y);
            Node testNode = node.GenerateNextNode(action, node.environnement.dogMiniMax);
            System.out.println("Depth : "+currentDepth+" Action " +count + " [max] Test node utility : "+ testNode.utility);
//            System.out.println("[max] Test node Generated, launching TourMin");
            HashMap<Integer, Action> recursiveResult = TourMin(testNode, maxDepth, currentDepth + 1);
            System.out.println("Depth : "+currentDepth+" Action " +count + " [max] TourMin Done");
            int testUtility = 0;
            Action testAction = Action.NOTHING;
            if(recursiveResult != null) {
                testUtility = recursiveResult.keySet().stream().findFirst().get();
                testAction = recursiveResult.values().stream().findFirst().get();
            }
            if( testUtility > bestUtility) {
                System.out.println("Depth : "+currentDepth+" Action " +count + "[max] "+testUtility+":"+bestUtility+" : test accepted");
                bestAction = testAction;
                bestUtility = testUtility;
            }
            else{
                System.out.println("Depth : "+currentDepth+" Action " +count + "[max] "+testUtility+":"+bestUtility+" :  test not accepted");
            }
        }

        result.put(bestUtility, bestAction);
        return result;
    }

    private HashMap<Integer, Action> TourMin(Node node, int maxDepth, int currentDepth)
    {
        if(currentDepth >= maxDepth)
            return null;
        HashMap<Integer, Action> result = new HashMap<>();
        if(node.isFinalState){
            System.out.println("[min] Found Final State");
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MAX_VALUE;
        Action bestAction = null;

        Dog currentDog = node.environnement.dogAStar;
        ArrayList<Action> possibleActions = new ArrayList<>();

        System.out.println("Depth : "+currentDepth+" [min] Current dog ("+currentDog.myColor+") position : "+currentDog.x+","+currentDog.y);

        if(!capteur.IsThereRemainingSheeps(node.environnement) && currentDog.sheepCarried == 0)
            possibleActions.add(Action.SLEEP);
        if(currentDog.AmIAtEnclos() && currentDog.sheepCarried > 0)
            possibleActions.add(Action.RELEASE);
        if(node.environnement.map[currentDog.x][currentDog.y].containsSheep)
            possibleActions.add(Action.CATCH);
        possibleActions.addAll(capteur.GetActionsPossibles(currentDog, node.environnement));
//        System.out.println("[max] Got Possible Actions");
//        System.out.println(currentDog.myColor+" dog int position ["+currentDog.x+","+currentDog.y+"]");
        int count = 0;
//        for (Action action : possibleActions ) {
//            count++;
//            System.out.println("Action "+count+" : "+action);
//        }
//        count = 0 ;
        for(Action action : possibleActions){
            count++;
//            System.out.println("[min] Action "+count);
            System.out.println("Depth : "+currentDepth+" Action " +count + " [min] Generate node with Action : "+action+" Dog : " + node.environnement.dogAStar.myColor+" "+node.environnement.dogAStar.x+","+node.environnement.dogAStar.y);
            Node testNode = node.GenerateNextNode(action, node.environnement.dogAStar);
            System.out.println("Depth : "+currentDepth+" Action " +count + " [mmin] Test node utility : "+ testNode.utility);
//            System.out.println("[min] Test node Generated, launching TourMax");
            HashMap<Integer, Action> recursiveResult = TourMax(testNode, maxDepth, currentDepth +1);
            System.out.println("Depth : "+currentDepth+" Action " +count + " [min] TourMax Done");
            int testUtility = 0;
            Action testAction = Action.NOTHING;
            if(recursiveResult != null) {
                testUtility = recursiveResult.keySet().stream().findFirst().get();
                testAction = recursiveResult.values().stream().findFirst().get();
            }
            if( testUtility < bestUtility) {
                System.out.println("Depth : "+currentDepth+" Action " +count + " [min]  "+testUtility+":"+bestUtility+" : test accepted");
                bestAction = testAction;
                bestUtility = testUtility;
            }
            else{
                System.out.println("Depth : "+currentDepth+" Action " +count + " [min] "+testUtility+":"+bestUtility+" :  test not accepted");
            }

        }

        result.put(bestUtility, bestAction);
        return result;
    }
}
