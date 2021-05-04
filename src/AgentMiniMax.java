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

    /*
        Dans notre problème, nous sommes obligés de fixer une profondeur maximum car le jeu peu potentiellement
        etre infini.
     */
    public void Resolution()
    {

        /*
            On commence par créer une copie des éléments réels, afin de ne pas les modifier lors du calcul des actions
         */
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

        /*
            On crée ensuite le noeud initial, puis on lance minimax pour choisir notre action
         */
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

        /*
            Minimax choisit sa solution en simulant la partie pour deux joueurs, min et max. Max va vouloir maximiser
            son gain, min va chercher à minimiser le gain de max.
            On cherche à choisir la meilleure action possible pour notre chien, on commence donc par lancer TourMax
         */
        Pair tourMaxResult = TourMax(initialNode, maxDepth, initialNode.depth);

        Action chosenAction = (Action) tourMaxResult.getSecond();

        System.out.println("Got Action with Utility : "+tourMaxResult.getFirst());
        return chosenAction;

    }


    /*
        TourMax va comparer l'utilité de tous ses noeuds enfants et va choisir l'action menant au noeud
        le plus prometteur
     */
    private Pair TourMax(Node node, int maxDepth, int currentDepth)
    {
        Pair result = new Pair(0, Action.NOTHING);


        /*
            Si on trouve un noeud but ou que l'on atteint la profondeur max de l'arbre, on returne l'utilité du noeud
            exploré
         */
        if(currentDepth >= maxDepth || node.isFinalState){
            result.Put(node.utility, Action.NOTHING);
            return result;
        }

        /*
            Sinon, on initialise une variable temporaire à l'infini négative
         */
        int bestUtility = Integer.MIN_VALUE;
        Action bestAction = null;

        /*
            On va ensuite générer les noeuds fils pour chaque action possible à partir du noeud courant. Pour cela on
            commence par récupérer les actions possibles à partir du noeud courant
         */
        Dog currentDog = node.environnement.dogAdverse;
        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        /*
            Pour chaque action possible de la liste, on va générer le noeud qui découle de cette action. On va appeler
            TourMin à partir de ce noeud, et on va choisir l'action qui nous mène au meilleur noeud en comparant
            les utilités
         */
        for(Action action : possibleActions){

            Node testNode = node.GenerateNextNode(action, node.environnement.dogAdverse, node.depth+1);
            Pair recursiveResult = TourMin(testNode, maxDepth, currentDepth + 1);

            int testUtility = (Integer) recursiveResult.getFirst();
            /*
                *   Si on trouve une meilleure utilité pour une action que la meilleure utilité en mémoire,
                    on sauvegarde sa valeur et l'action menant au noeud possédant cette utilité
                *   Sinon on continue notre exploration
             */
            if(testUtility > bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }
        }
        /*
            Enfin on retourne les valeurs retenues
         */

        result.Put(bestUtility, bestAction);
        return result;
    }

    /*
        TourMin va comparer l'utilité de tous ses noeuds enfants et va choisir l'action menant au noeud
        le moins satisfaisant pour max
     */
    private Pair TourMin(Node node, int maxDepth, int currentDepth)
    {
        Pair result = new Pair(0, Action.NOTHING);

        /*
            Si on trouve un noeud but ou que l'on atteint la profondeur max de l'arbre, on retourne l'utilité du noeud
            exploré
         */

        if(currentDepth >= maxDepth || node.isFinalState){
            result.Put(node.utility, Action.NOTHING);
            return result;
        }

        /*
            Sinon, on initialise une variable temporaire à l'infini positive
         */
        int bestUtility = Integer.MAX_VALUE;
        Action bestAction = null;


        /*
            On va ensuite générer les noeuds fils pour chaque action possible à partir du noeud courant. Pour cela on
            commence par récupérer les actions possibles à partir du noeud courant
         */
        Dog currentDog = node.environnement.dogHeuristic;
        ArrayList<Action> possibleActions = capteur.GetActionsPossibles(currentDog, node.environnement);

        /*
            Pour chaque action possible de la liste, on va générer le noeud qui découle de cette action. On va appeler
            TourMax à partir de ce noeud, et on va choisir l'action qui nous mène au noeud le moins satisfaisant pour
            max en comparant les utilités
         */
        for(Action action : possibleActions){
            Node testNode = node.GenerateNextNode(action, node.environnement.dogHeuristic, node.depth+1);

            Pair recursiveResult = TourMax(testNode, maxDepth, currentDepth +1);

            int testUtility = (Integer) recursiveResult.getFirst();
            /*
                *   Si on trouve une moins bonne utilité pour une action que la moins bonne des utilités en mémoire,
                    on sauvegarde sa valeur et l'action menant au noeud possédant cette utilité
                *   Sinon on continue notre exploration
             */

            if( testUtility < bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
            }

        }


        /*
            Enfin on retourne les valeurs retenues
         */
        result.Put(bestUtility, bestAction);
        return result;
    }

}
