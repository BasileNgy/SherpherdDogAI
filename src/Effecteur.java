
enum Direction
{
    HAUT,DROITE,BAS,GAUCHE
}

public class Effecteur {

    public void Deplacement(Direction dir, Dog dog)
    {
        switch(dir)
        {
            case HAUT -> dog.y --;
            case DROITE -> dog.x ++;
            case BAS -> dog.y ++;
            case GAUCHE -> dog.x --;
        }
    }
}
