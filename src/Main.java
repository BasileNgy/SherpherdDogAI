public class Main {

    public static void main(String[] args)
    {
        Environnement envir = new Environnement(15, 10);
        Enclos enclos1 = new Enclos(0,0);
        Enclos enclos2 = new Enclos(14,14);
        Dog dog1 = new Dog(3,enclos1);
        Dog dog2 = new Dog(3,enclos2);

        Graphic graph = new Graphic();
    }
}
