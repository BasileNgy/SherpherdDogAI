import java.util.ArrayList;

public class AgentAlphaBeta {

    private final Dog maxDog; //minimax dog
    private final Environnement environnement;
    private final Capteur capteur;
    private final Effecteur effecteur;
    private final int maxDepth;
    public int minElagage;
    public int maxElagage;

    public AgentAlphaBeta(Environnement environnement, Dog maxDog, int maxDepth)
    {
        this.environnement = environnement;
        this.maxDog  = maxDog;
        this.maxDepth = maxDepth;

        capteur = new Capteur();
        effecteur = new Effecteur();
    }

    /*
        Dans notre problème, nous sommes obligés de fixer une profondeur maximum car le jeu peu potentiellement
        etre infini.
     */
    public void Resolution()
    {
        minElagage = 0;                 //Permet de compter le nombre de branches élaguées par le joueur min
        maxElagage = 0;                 //Permet de compter le nombre de branches élaguées par le joueur max

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
            On crée ensuite le noeud initial, puis on lance l'élagage alpha beta pour choisir notre action
         */
        Node initialNode = new Node(environnement, minimaxDog, 0);
        System.out.println("Launching AlphaBeta");
        Action chosenAction = AlphaBeta(initialNode);

        effecteur.Agir(chosenAction, maxDog, environnement);
        System.out.println("ActionApplied : "+chosenAction);

        System.out.print("Position minimaxDog "+ environnement.dogAdverse.x +":"+environnement.dogAdverse.y + " Carrying "+environnement.dogAdverse.sheepCarried);
        System.out.print("\n");
    }

    private Action AlphaBeta(Node initialNode){
        System.out.print("Actions possibles pour MAX/RED : ");
        for(Action action : capteur.GetActionsPossibles(initialNode.activeDog, initialNode.environnement))
        {
            System.out.print(action +" ");
        }
        System.out.println();

        /*
            AlphaBeta choisit sa solution en simulant la partie pour deux joueurs, min et max. Max va vouloir maximiser
            son gain, min va chercher à minimiser le gain de max.
            On cherche à choisir la meilleure action possible pour notre chien, on commence donc par lancer TourMax
            Il s'agit du même fonctionnement que Minimax, seulement on va introduire une notion d'élaguage des branches
            ne pouvant pas améliorer notre solution en maintenant un intervalle de valeurs acceptables. L'elagage alpha
            beta est censé nous retourner la meme solution que minimax en un temps moindre du a l'elagage des branches
         */
        Pair tourMaxResult = TourMax(initialNode, maxDepth, initialNode.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);

        Action chosenAction = (Action) tourMaxResult.getSecond();

        System.out.println("Got Action with Utility : "+tourMaxResult.getFirst());
        return chosenAction;

    }


    /*
        TourMax va comparer l'utilité de tous ses noeuds enfants et va choisir l'action menant au noeud
        le plus prometteur
     */
    private Pair<Integer, Action> TourMax(Node node, int maxDepth, int currentDepth, int alpha, int beta)
    {
        Pair result = new Pair(0, Action.NOTHING);

        /*
            Si on trouve un noeud but ou que l'on atteint la profondeur max de l'arbre, on returne l'utilité du noeud
            exploré
         */
        if(node.isFinalState || currentDepth >= maxDepth)
        {
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
            les utilités, tout en surveillant l'acceptabilité de la solution
         */

        for(Action action : possibleActions){

            Node testNode = node.GenerateNextNode(action, node.environnement.dogAdverse, node.depth+1);
            Pair<Integer, Action> recursiveResult = TourMin(testNode, maxDepth, currentDepth + 1, alpha, beta);

            int testUtility = recursiveResult.getFirst();
            /*
                Si on trouve une meilleure utilité pour une action que la meilleure utilité en mémoire, on vérifie que
                cette valeur n'est pas en contradiction avec notre intervalle [alpha, beta].
                *   Si on trouve une valeur >= beta dans TourMax, on peut arreter l'exploration ici.
                    En effet, nous nous trouvons dans TourMax, on est donc assurés de choisir une valeur au
                    moins > beta.
                    Or le max acceptable par l'itération récursive de TourMin précédant n'acceptera qu'une
                    valeur < à beta.
                    Inutile donc de continuer l'exploration, et on retourne l'utilité trouvée pour le noeud fils
                    que nous sommes en train de regarder.
                *   Si on a une valeur < beta, on met à jour les bornes de notre intervalle en changeant alpha pour la
                    valeur trouvée
             */
            if(testUtility > bestUtility) {
                bestAction = action;
                bestUtility = testUtility;
                if(bestUtility >= beta){
                    maxElagage++;
                    result.Put(bestUtility, bestAction);
                    return result;
                } else
                    alpha = Math.max(alpha, bestUtility);
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
        le moins satisfaisant pour max, tout en surveillant l'acceptabilité de la solution
     */

    private Pair<Integer, Action> TourMin(Node node, int maxDepth, int currentDepth, int alpha, int beta)
    {
        Pair result = new Pair(0, Action.NOTHING);


        /*
            Si on trouve un noeud but ou que l'on atteint la profondeur max de l'arbre, on retourne l'utilité du noeud
            exploré
         */

        if(node.isFinalState || currentDepth >= maxDepth)
        {
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

            Pair<Integer, Action>  recursiveResult = TourMax(testNode, maxDepth, currentDepth +1, alpha, beta);

            int testUtility = recursiveResult.getFirst();
            /*
                Si on trouve une moins bonne utilité pour une action que la moins bonne des utilités en mémoire, on
                vérifie que cette valeur n'est pas en contradiction avec notre intervalle [alpha, beta].
                *   Si on trouve une valeur <= alpha dans TourMin, on peut arreter l'exploration ici.
                    En effet, nous nous trouvons dans TourMin, on est donc assurés de choisir une valeur au
                    moins < alpha.
                    Or le min acceptable par l'itération récursive de TourMax précédant n'acceptera qu'une
                    valeur > à alpha.
                    Inutile donc de continuer l'exploration, et on retourne l'utilité trouvée pour le noeud fils
                    que nous sommes en train de regarder.
                *   Si on a une valeur > alpha, on met à jour les bornes de notre intervalle en changeant beta pour la
                    valeur trouvée
             */
            if( testUtility < bestUtility) {
                bestAction = action;
                bestUtility = testUtility;

                if(bestUtility <= alpha){
                    minElagage++;
                    result.Put(bestUtility, bestAction);
                    return result;
                } else
                    beta = Math.min(beta, bestUtility);
            }

        }


        /*
            Enfin on retourne les valeurs retenues
         */
        result.Put(bestUtility, bestAction);
        return result;
    }
}
