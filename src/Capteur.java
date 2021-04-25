import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.ArrayList;

public class Capteur {

    public Point2D GetNearestObjective(Dog dog, Environnement envir)
    {
        int shortestManhattanDistance = 100;
        int nearestSheepX = -1;
        int nearestSheepY = -1;

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
                        nearestSheepX = i;
                        nearestSheepY = j;
                    }
                }

            }
        }
        return new Point2D.Float(nearestSheepX, nearestSheepY);
    }

    public int CalculManhanttanDistance(int dogX, int dogY, int objX, int objY)
    {
        return Math.abs(Math.abs(dogX) - Math.abs(objX)) + Math.abs(Math.abs(dogY) - Math.abs(objY));
    }

    public ArrayList<Action> GetActionsPossibles(Dog dog, Environnement envir)
    {
        ArrayList<Action> actionList = new ArrayList<>();
        if(dog.y >= 1 )
            actionList.add(Action.HAUT);
        if(dog.x <= envir.size-2)
            actionList.add(Action.DROITE);
        if(dog.y <= envir.size-2)
            actionList.add(Action.BAS);
        if(dog.x >= 1 )
            actionList.add(Action.GAUCHE);

        return actionList;
    }
}
