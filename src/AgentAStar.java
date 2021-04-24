import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AgentAStar {

    private Dog dog;
    private Environnement environnement;
    private int currentObjectiveX;
    private int currentObjectiveY;
    private int distanceCurrentObjective;
    private Capteur capteur;
    private Effecteur effecteur;

    public AgentAStar(Dog dog, Environnement environnement)
    {
        this.dog = dog;
        this.environnement = environnement;
        capteur = new Capteur();
        effecteur = new Effecteur();

        currentObjectiveX = 0;
        currentObjectiveY = 0;
    }

    public void Resolution()
    {
        //Récupération de l'objectif avec les capteurs
        SetupObjective();

        // Choix de l'action possible
        Action action = ActionChoice();

        // application de l'action
    }

    private void SetupObjective()
    {
        //test si le nmbre max de moutons transportés et atteint et retour à l'enclos
        if(dog.sheepCarried == dog.maxSheepCarried)
        {
            currentObjectiveX = dog.enclos.x;
            currentObjectiveY = dog.enclos.y;
        }
        else
        {
            //test si le mouton objectif est toujours présent sinon recalcule l'objectif
            if(!environnement.map[currentObjectiveX][currentObjectiveY].containsSheep)
            {
                Point2D objective = capteur.GetNearestObjective(dog, environnement);
                currentObjectiveX = (int) objective.getX();
                currentObjectiveY = (int) objective.getY();
            }
        }
    }

    private Action ActionChoice()
    {
        Action actionChosen = Action.NOTHING;
        distanceCurrentObjective = capteur.CalculManhanttanDistance(dog, currentObjectiveX, currentObjectiveY);

        //si le chien est arrivé à  son objectif
        if (distanceCurrentObjective == 0)
        {
            if(environnement.map[currentObjectiveX][currentObjectiveX].containsSheep)
                actionChosen = Action.CATCH;
            if(dog.enclos.x == currentObjectiveX && dog.enclos.y == currentObjectiveY)
                actionChosen = Action.RELEASE;
        }
        else
        {
            ArrayList<Action> actionList = capteur.GetActionsPossibles(dog, environnement);
            actionChosen = actionList.get(0);

            int potentialDistance = distanceCurrentObjective;

            //Choisi l'action qui réduit le plus la distance entre le dog et son objectif
            for (Action action: actionList)
            {
                int x = dog.x;
                int y = dog.y;

                switch(action)
                {
                    case HAUT -> y--;
                    case DROITE -> x++;
                    case BAS -> y++;
                    case GAUCHE -> x--;
                }
                if(capteur.CalculManhanttanDistance(dog, x,y) < potentialDistance)
                {
                    potentialDistance = capteur.CalculManhanttanDistance(dog, x,y);
                    actionChosen = action;
                }
            }
        }

        return actionChosen;
    }
}
