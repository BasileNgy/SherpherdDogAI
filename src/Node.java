public class Node {

    public Environnement environnement;
    public Dog activeDog;
    public Effecteur mockEffecteur;
    public boolean isFinalState;
    public int utility;

    public Node(Environnement environnement, Dog activeDog){
        this.environnement = new Environnement();
        InitializeNodeEnvironment(environnement);
        this.activeDog = activeDog;
    }

    public Node(Environnement environnement, Dog activeDog, Action action) {
        this.environnement = new Environnement();
        InitializeNodeEnvironment(environnement);

        this.activeDog = activeDog;

        mockEffecteur = new Effecteur();
        mockEffecteur.Agir(action, activeDog, environnement);


        isFinalState = this.environnement.MatchEnded();
        utility = this.environnement.dogMiniMax.score +this.environnement.dogMiniMax.sheepCarried
                - this.environnement.dogAStar.score - this.environnement.dogAStar.sheepCarried;
        System.out.println("New node generated. Node active dog : "+activeDog.myColor+". " +
                "Node utility : "+utility+". Node is a final state ? "+isFinalState);
    }

    public void InitializeNodeEnvironment(Environnement environnement) {

        this.environnement.enclosDogAStar = new Enclos(
                environnement.enclosDogAStar.x,
                environnement.enclosDogAStar.y,
                environnement.enclosDogAStar.color);

        this.environnement.dogAStar = new Dog(
                environnement.dogAStar.maxSheepCarried,
                this.environnement.enclosDogAStar,
                environnement.dogAStar.myColor,
                environnement.dogAStar.enemyColor);

        this.environnement.enclosDogMiniMax = new Enclos(
                environnement.enclosDogMiniMax.x,
                environnement.enclosDogMiniMax.y,
                environnement.enclosDogMiniMax.color);

        this.environnement.dogMiniMax = new Dog(
                environnement.dogMiniMax.maxSheepCarried,
                this.environnement.enclosDogMiniMax,
                environnement.dogMiniMax.myColor,
                environnement.dogMiniMax.enemyColor);


        this.environnement.size = environnement.size;
        this.environnement.map = new Room[this.environnement.size][this.environnement.size];
        for (int i = 0 ; i < this.environnement.size ; i++)
            for (int j = 0 ; j < this.environnement.size ; j++)
                this.environnement.map[i][j] = (Room) environnement.map[i][j].clone();

        this.environnement.remainingSheeps = environnement.remainingSheeps;


    }

    public Node GenerateNextNode(Action action, Dog activeDog) {
        return new Node(environnement, activeDog, action);
    }
}
