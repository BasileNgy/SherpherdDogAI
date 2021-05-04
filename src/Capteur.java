import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.ArrayList;

public class Capteur {

    /*
        Retourne vrai s'il reste des moutons à capturer, faux sinon
     */
    public boolean IsThereRemainingSheeps(Environnement envir) {
        return (envir.remainingSheeps > 0);
    }

    /*
        Retourne la position du mouton le plus proche s'il en reste, celle de l'enclos associé au chien sinon
     */
    public Point2D GetNearestObjective(Dog dog, Environnement envir) {
        int shortestManhattanDistance = 100;
        int nearestObj = -1;
        int nearestObjY = -1;

        if(IsThereRemainingSheeps(envir)){
            for (int j = 0; j < envir.size; j++)
            {
                for (int i = 0; i < envir.size; i++)
                {
                    if(envir.map[i][j].containsSheep)
                    {
                        int valueCalculated = CalculManhanttanDistance(dog.x, dog.y, i, j);
                        if(valueCalculated < shortestManhattanDistance)
                        {
                            shortestManhattanDistance = valueCalculated;
                            nearestObj = i;
                            nearestObjY = j;
                        }
                    }

                }
            }
        }
        else{
            nearestObj = dog.enclos.x;
            nearestObjY = dog.enclos.y;
        }
        return new Point2D.Float(nearestObj, nearestObjY);
    }

    /*
        Calcule le nombre de déplacement à effectuer pour aller de la position actuelle du chien à celle de l'objet
        passée en parametre
     */
    public int CalculManhanttanDistance(int dogX, int dogY, int objX, int objY) {
        return Math.abs(Math.abs(dogX) - Math.abs(objX)) + Math.abs(Math.abs(dogY) - Math.abs(objY));
    }

    /*
        Retourne les actions possibles d'un chien pour une case à un moment donné de l'exécution
     */
    public ArrayList<Action> GetActionsPossibles(Dog dog, Environnement envir) {
        ArrayList<Action> actionList = new ArrayList<>();

        if(!IsThereRemainingSheeps(envir) && dog.sheepCarried == 0)
            actionList.add(Action.SLEEP);

        if (dog.AmIAtEnclos() && dog.sheepCarried > 0)
            actionList.add(Action.RELEASE);

        if (envir.map[dog.x][dog.y].containsSheep && dog.sheepCarried < dog.maxSheepCarried) {
            actionList.add(Action.CATCH);
        }

        if (dog.y <= envir.size - 2) {
            if (envir.map[dog.x][dog.y + 1].color != dog.enemyColor)
                actionList.add(Action.BAS);
        }

        if (dog.x <= envir.size - 2) {
            if (envir.map[dog.x + 1][dog.y].color != dog.enemyColor)
                actionList.add(Action.DROITE);
        }
        if (dog.y >= 1) {
            if (envir.map[dog.x][dog.y - 1].color != dog.enemyColor)
                actionList.add(Action.HAUT);
        }
        if (dog.x >= 1) {
            if (envir.map[dog.x - 1][dog.y].color != dog.enemyColor)
                actionList.add(Action.GAUCHE);
        }

        return actionList;
    }
}
