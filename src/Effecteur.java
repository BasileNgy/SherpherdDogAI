
enum Action {
    HAUT, DROITE, BAS, GAUCHE, CATCH, RELEASE, SLEEP, NOTHING
}

public class Effecteur {

    public void Agir(Action act, Dog dog, Environnement env) {

        env.map[dog.x][dog.y].containsDog = false;
        env.map[dog.x][dog.y].color = DogColor.BLACK;

        switch (act) {
            case HAUT :
                dog.y--;
                break;
            case DROITE :
                dog.x++;
                break;

            case BAS :
                dog.y++;
                break;

            case GAUCHE :
                dog.x--;
                break;
            case CATCH :
                dog.sheepCarried++;
                env.map[dog.x][dog.y].containsSheep = false;
                env.remainingSheeps--;
                break;
            case RELEASE :
                dog.score += dog.sheepCarried;
                dog.sheepCarried = 0;
                break;
            default :
                break;
        }
        env.map[dog.x][dog.y].containsDog = true;
        env.map[dog.x][dog.y].color = dog.myColor;
        env.map[dog.enclos.x][dog.enclos.y].color = dog.myColor;
    }
}
