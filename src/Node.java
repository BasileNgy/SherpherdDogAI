
/*
    Cette classe nous permet de parcourir l'ensemble des solutions lors du calcul de minimax et alphabeta
 */
public class Node {

    public Environnement environnement;
    public Dog activeDog;
    public Effecteur mockEffecteur;
    public boolean isFinalState;
    public int utility;
    public int depth;

    /*
        Constructeur utilisé seulement pour créer le noeud initial
     */
    public Node(Environnement environnement, Dog activeDog, int depth){

        this.environnement = new Environnement();
        CloneNode(environnement);

        this.depth = depth;

        if(activeDog.myColor == DogColor.RED)
            this.activeDog = this.environnement.dogAdverse;
        else this.activeDog = this.environnement.dogHeuristic;
    }
    /*
        Nous permet de copier des noeuds et les données qui lui sont associées, pour permettre aux agents adverses de
        "réfléchir" et prévoir l'application de leurs actions sans modifier l'environnement réel
     */

    public Node(Environnement environnement, Dog activeDog, Action action, int depth) {
        this.environnement = new Environnement();
        CloneNode(environnement);

        this.depth = depth;


        if(activeDog.myColor == DogColor.RED)
            this.activeDog = this.environnement.dogAdverse;
        else this.activeDog = this.environnement.dogHeuristic;

        /*
            Permet de générer le prochain noeud sans modifier l'environnement réel
         */
        mockEffecteur = new Effecteur();
        mockEffecteur.Agir(action, this.activeDog, this.environnement);

        isFinalState = this.environnement.MatchEnded();

        CalculUtility();
    }

    /*
        Calcule l'utilité associée à un noeud. Nous avons choisi de mettre l'emphase sur les moutons qui ont été
        effectivement rapportés à l'enclos, puis sur le nombre de moutons portés et enfin sur le nombre de déplacement
        utilisés jusque là (symbolisé par la profondeur de l'arbre)
     */
    private void CalculUtility(){
        if(this.depth%2 == 0){
            this.utility = (10*this.environnement.dogAdverse.score + this.environnement.dogAdverse.sheepCarried
                    - 10*this.environnement.dogHeuristic.score - this.environnement.dogHeuristic.sheepCarried) * 100
                    - this.depth;
        } else{

            utility = (10*this.environnement.dogAdverse.score + this.environnement.dogAdverse.sheepCarried
                    - 10*this.environnement.dogHeuristic.score - this.environnement.dogHeuristic.sheepCarried) * 100
                    + this.depth;
        }
    }

    /*
        Crée une copie des instances de l'environnement réel pour analyser les actions sans le modifier
     */
    public void CloneNode(Environnement environnement) {

        this.environnement.enclosHeuristic = new Enclos(
                environnement.enclosHeuristic.x,
                environnement.enclosHeuristic.y,
                environnement.enclosHeuristic.color);

        this.environnement.dogHeuristic = new Dog(
                environnement.dogHeuristic.maxSheepCarried,
                this.environnement.enclosHeuristic,
                environnement.dogHeuristic.myColor,
                environnement.dogHeuristic.enemyColor,
                environnement.dogHeuristic.x,
                environnement.dogHeuristic.y);
        this.environnement.dogHeuristic.sheepCarried = environnement.dogHeuristic.sheepCarried;
        this.environnement.dogHeuristic.score = environnement.dogHeuristic.score;

        this.environnement.enclosAdverse = new Enclos(
                environnement.enclosAdverse.x,
                environnement.enclosAdverse.y,
                environnement.enclosAdverse.color);

        this.environnement.dogAdverse = new Dog(
                environnement.dogAdverse.maxSheepCarried,
                this.environnement.enclosAdverse,
                environnement.dogAdverse.myColor,
                environnement.dogAdverse.enemyColor,
                environnement.dogAdverse.x,
                environnement.dogAdverse.y);
        this.environnement.dogAdverse.sheepCarried = environnement.dogAdverse.sheepCarried;
        this.environnement.dogAdverse.score = environnement.dogAdverse.score;

        this.environnement.size = environnement.size;
        this.environnement.map = new Room[this.environnement.size][this.environnement.size];
        for (int i = 0 ; i < this.environnement.size ; i++)
            for (int j = 0 ; j < this.environnement.size ; j++)
                this.environnement.map[i][j] = (Room) environnement.map[i][j].clone();


        this.environnement.remainingSheeps = environnement.remainingSheeps;


    }

    /*
        Permet de générer le noeud résultant de l'application d'une action sur le noeud node
     */
    public Node GenerateNextNode(Action action, Dog activeDog, int depth) {
        return new Node(environnement, activeDog, action, depth);
    }
}
