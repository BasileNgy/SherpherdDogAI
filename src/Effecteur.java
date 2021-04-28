
enum Action {
    HAUT, DROITE, BAS, GAUCHE, CATCH, RELEASE, SLEEP
}

public class Effecteur {

    public void Agir(Action act, Dog dog, Environnement env) {
        env.map[dog.x][dog.y].containsDog = false;
        env.map[dog.x][dog.y].color = DogColor.BLACK;

        switch (act) {
            case HAUT -> {
                dog.y--;
                System.out.println("Mooving "+dog.myColor+" dog up");
            }
            case DROITE -> {
                dog.x++;
                System.out.println("Mooving "+dog.myColor+" dog right");
            }
            case BAS -> {
                dog.y++;
                System.out.println("Mooving "+dog.myColor+" dog down");
            }
            case GAUCHE -> {
                dog.x--;
                System.out.println("Mooving "+dog.myColor+" dog left");
            }
            case CATCH -> {
                dog.sheepCarried++;
                env.map[dog.x][dog.y].containsSheep = false;
                env.remainingSheeps--;
                System.out.println(dog.myColor+" dog caught a sheep");
            }
            case RELEASE -> {
                dog.enclos.sheepsBrought += dog.sheepCarried;
                dog.score += dog.sheepCarried;
                System.out.println("Dog "+dog.myColor+" score : "+dog.score);
                dog.sheepCarried = 0;

            }
        }
        env.map[dog.x][dog.y].containsDog = true;
        env.map[dog.x][dog.y].color = dog.myColor;
        env.map[dog.enclos.x][dog.enclos.y].color = dog.myColor;
    }
}
