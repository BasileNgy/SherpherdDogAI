
enum Action {
    HAUT, DROITE, BAS, GAUCHE, CATCH, RELEASE, NOTHING
}

public class Effecteur {

    public void Agir(Action act, Dog dog, Environnement env) {
        switch (act) {
            case HAUT -> dog.y--;
            case DROITE -> dog.x++;
            case BAS -> dog.y++;
            case GAUCHE -> dog.x--;
            case CATCH -> {
                dog.sheepCarried++;
                env.map[dog.x][dog.y].containsSheep = false;
            }
            case RELEASE -> {
                dog.enclos.sheepsBrought += dog.sheepCarried;
                dog.score += dog.sheepCarried;
                dog.sheepCarried = 0;

            }
        }
    }
}
