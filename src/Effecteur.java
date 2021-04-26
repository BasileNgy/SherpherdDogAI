
enum Action {
    HAUT, DROITE, BAS, GAUCHE, CATCH, RELEASE, NOTHING
}

public class Effecteur {

    public void Agir(Action act, Dog dog, Environnement env) {
        env.map[dog.x][dog.y].containsDog = false;
        env.map[dog.x][dog.y].color = DogColor.BLACK;

        switch (act) {
            case HAUT -> dog.y--;
            case DROITE -> dog.x++;
            case BAS -> dog.y++;
            case GAUCHE -> dog.x--;
            case CATCH -> {
                dog.sheepCarried++;
                env.map[dog.x][dog.y].containsSheep = false;
                env.remainingSheeps--;
            }
            case RELEASE -> {
                dog.enclos.sheepsBrought += dog.sheepCarried;
                dog.score += dog.sheepCarried;
                dog.sheepCarried = 0;

            }
        }
        env.map[dog.x][dog.y].containsDog = true;
        env.map[dog.x][dog.y].color = dog.color;
        env.map[dog.enclos.x][dog.enclos.y].color = dog.color;
    }
}
