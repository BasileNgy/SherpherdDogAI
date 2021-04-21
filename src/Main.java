public class Main {

    public static void main(String[] args)
    {
        Environnement envir = new Environnement(15, 10);
        Enclos enclos1 = new Enclos(0,0, DogColor.BLUE);
        Enclos enclos2 = new Enclos(14,14, DogColor.RED);
        Dog dog1 = new Dog(3,enclos1, envir.map);
        Dog dog2 = new Dog(3,enclos2, envir.map);

        Graphic graph = new Graphic(envir.map);
    }
}
