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
        HashMap<Integer, Action> tourMaxResult = TourMax(initialNode, 9, 0);

        System.out.println("Getting Action");
        Action chosenAction = tourMaxResult.values().stream().findFirst().get();

        System.out.println("Got Action with Utility : "+tourMaxResult.keySet().stream().findFirst().get());
        return chosenAction;

    }

    private HashMap<Integer, Action> TourMax(Node node, int maxDepth, int currentDepth)
    {
        HashMap<Integer, Action> result = new HashMap<>();

        if(currentDepth >= maxDepth)
        {
            //System.out.println("[max] Max Depth Reached");
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        if(node.isFinalState){
            System.out.println("MAX/RED est arrivé à un état final dont l'utilité est "+ node.utility+" au niveau "+currentDepth);
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MIN_VALUE;
        Action bestAction = null;


        Dog currentDog = node.environnement.dogMiniMax;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        for(Action action : possibleActions){

            Node testNode = node.GenerateNextNode(action, node.environnement.dogMiniMax);
            String txt = "";
            for (int i = 0; i < currentDepth; i++) {
                txt += "        ";
            }
            int txtcalcul = currentDepth +1;
            txt += "Création d'un node MIN/BLUE de niveau " + txtcalcul +" si MAX/RED choisi "+action;
            System.out.println(txt);
            HashMap<Integer, Action> recursiveResult = TourMin(testNode, maxDepth, currentDepth + 1);

            int testUtility = recursiveResult.keySet().stream().findFirst().get();

            if(testUtility > bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }
            else{
            }
        }

        //System.out.println("MAX/RED a conclu a une utilité de "+bestUtility+" pour l'action "+bestAction+" au niveau "+currentDepth);

        result.put(bestUtility, bestAction);
        return result;
    }

    private HashMap<Integer, Action> TourMin(Node node, int maxDepth, int currentDepth)
    {
        HashMap<Integer, Action> result = new HashMap<>();

        if(currentDepth >= maxDepth)
        {
            //System.out.println("[min] Max Depth Reached");
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        if(node.isFinalState){
            System.out.println("MIN/BLUE est arrivé à un état final dont l'utilité est "+ node.utility+" au niveau "+currentDepth);
            result.put(node.utility, Action.NOTHING);
            return result;
        }

        int bestUtility = Integer.MAX_VALUE;
        Action bestAction = null;

        Dog currentDog = node.environnement.dogAStar;

        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        for(Action action : possibleActions){
            Node testNode = node.GenerateNextNode(action, node.environnement.dogAStar);
            String txt = "";
            for (int i = 0; i < currentDepth; i++) {
                txt += "        ";
            }
            int txtcalcul = currentDepth +1;
            txt += "Création d'un node MAX/RED de niveau " + txtcalcul +" si MIN/BLUE choisi "+action;
            System.out.println(txt);

            HashMap<Integer, Action> recursiveResult = TourMax(testNode, maxDepth, currentDepth +1);

            int testUtility = recursiveResult.keySet().stream().findFirst().get();

            if( testUtility < bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }
            else{
            }

        }

        //System.out.println("MIN/BLUE a conclu a une utilité de "+bestUtility+" pour l'action "+bestAction+" au niveau "+currentDepth);
        result.put(bestUtility, bestAction);
        return result;
    }
}
