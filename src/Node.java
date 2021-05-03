public class Node {

    public Environnement environnement;
    public Dog activeDog;
    public Effecteur mockEffecteur;
    public boolean isFinalState;
    public int utility;

    public Node(Environnement environnement, Dog activeDog){
        this.environnement = new Environnement();
        InitializeNodeEnvironment(environnement);
        if(activeDog.myColor == DogColor.RED)
            this.activeDog = this.environnement.dogMiniMax;
        else this.activeDog = this.environnement.dogAStar;
    }

    public Node(Environnement environnement, Dog activeDog, Action action) {
        this.environnement = new Environnement();
        InitializeNodeEnvironment(environnement);

//        System.out.println("Node initialized correctly ! Size : "+this.environnement.size+" RemainingSheeps : "+this.environnement.remainingSheeps);

        if(activeDog.myColor == DogColor.RED)
            this.activeDog = this.environnement.dogMiniMax;
        else this.activeDog = this.environnement.dogAStar;

//        System.out.println("["+activeDog.myColor+"] Active dog position : "+activeDog.y+","+activeDog.y);

        mockEffecteur = new Effecteur();

        /*System.out.println("[bef] Active dog ("+this.activeDog.myColor+") sheeps : " + this.activeDog.sheepCarried + " " +
                "dogAStar sheeps : "+ this.environnement.dogAStar.sheepCarried+" " +
                "dogMiniMax sheeps : "+this.environnement.dogAStar.sheepCarried);*/


        mockEffecteur.Agir(action, this.activeDog, this.environnement);

        /*System.out.println("[af] Active dog ("+this.activeDog.myColor+") sheeps : " + this.activeDog.sheepCarried + " " +
                "dogAStar sheeps : "+ this.environnement.dogAStar.sheepCarried+" " +
                "dogMiniMax sheeps : "+this.environnement.dogMiniMax.sheepCarried);*/

        isFinalState = this.environnement.MatchEnded();
        utility = 2*this.environnement.dogMiniMax.score + this.environnement.dogMiniMax.sheepCarried
                - 2*this.environnement.dogAStar.score - this.environnement.dogAStar.sheepCarried;

//        System.out.println("New node generated. Node active dog : "+activeDog.myColor+". " +
//                "Node utility : "+utility+". Node is a final state ? "+isFinalState);
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
                environnement.dogAStar.enemyColor,
                environnement.dogAStar.x,
                environnement.dogAStar.y);
        this.environnement.dogAStar.sheepCarried = environnement.dogAStar.sheepCarried;
        this.environnement.dogAStar.score = environnement.dogAStar.score;

        this.environnement.enclosDogMiniMax = new Enclos(
                environnement.enclosDogMiniMax.x,
                environnement.enclosDogMiniMax.y,
                environnement.enclosDogMiniMax.color);

        this.environnement.dogMiniMax = new Dog(
                environnement.dogMiniMax.maxSheepCarried,
                this.environnement.enclosDogMiniMax,
                environnement.dogMiniMax.myColor,
                environnement.dogMiniMax.enemyColor,
                environnement.dogMiniMax.x,
                environnement.dogMiniMax.y);
        this.environnement.dogMiniMax.sheepCarried = environnement.dogMiniMax.sheepCarried;
        this.environnement.dogMiniMax.score = environnement.dogMiniMax.score;

        this.environnement.size = environnement.size;
        this.environnement.map = new Room[this.environnement.size][this.environnement.size];
        for (int i = 0 ; i < this.environnement.size ; i++)
            for (int j = 0 ; j < this.environnement.size ; j++) {
//                System.out.println("Initial map contains sheep ? "+environnement.map[i][j].containsSheep);
                this.environnement.map[i][j] = (Room) environnement.map[i][j].clone();
//                System.out.println("New map contains sheep ? "+this.environnement.map[i][j].containsSheep);
            }

        this.environnement.remainingSheeps = environnement.remainingSheeps;


    }

    public Node GenerateNextNode(Action action, Dog activeDog) {
        return new Node(environnement, activeDog, action);
    }
}
