package Family;

public class Main {

    static Child[] children = new Child[10];
    static Parent[] parents = new Parent[2];
    static int seatsAvailable = 4;

    public static void main(String[] args) {
        for (int i = 1; i <= children.length; i++) {
            children[i - 1] = new Child(i);
            children[i - 1].start();
            if (i <= parents.length) {
                parents[i - 1] = new Parent(i);
                parents[i - 1].start();
            }
        }
    }
}