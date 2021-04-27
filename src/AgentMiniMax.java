public class AgentMiniMax {

    private Dog maxDog; //minimax dog
    private Dog minDog; //a* dog
    private Environnement environnement;
    private Capteur capteur;
    private Effecteur effecteur;

    public AgentMiniMax(Dog maxDog, Dog minDog, Environnement environnement)
    {
        this.maxDog  = maxDog;
        this.minDog = minDog;
        this.environnement = environnement;

        capteur = new Capteur();
        effecteur = new Effecteur();
    }

    public void Resolution()
    {
        //créer des objects nodes qui sont des copies de l'environnement pour chaque choix possible de MAX puis de MIN
        //retourner l'utilité la plus adéquate pour chacun
        //utilité de max doit être maximale et utilité de min doit être minimale
        //tester l'ensemble des pairs

        // ETUDIER ELAGAGE ALPHA BETA 
    }

    private int TourMax()
    {
        if(IsFinalState())
            return GetUtility();

        return 0;
    }

    private boolean IsFinalState()
    {
        return (environnement.remainingSheeps == 0 && maxDog.sheepCarried == 0 && minDog.sheepCarried == 0);
    }

    private int GetUtility()
    {
        return maxDog.score - minDog.score;
    }

}
