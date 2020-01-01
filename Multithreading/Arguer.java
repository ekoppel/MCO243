package Multithreading;

public class Arguer extends Thread {

    private boolean hasArgued = false;

    private void respond(){
        System.out.println("No I'm better");
        hasArgued = false;

    }

    private void reiterate(){
        System.out.println("I'm really better");
        hasArgued = false;
    }

    @Override
    public void run() {
        System.out.println("I'm better than you");
        hasArgued = true;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Arguer t1 = new Arguer();
        Arguer t2 = new Arguer();
        while (System.currentTimeMillis() < start + 5000) {
            t1.run();
            t2.run();
            if (t1.hasArgued)
                t2.respond();
            else
                t2.reiterate();
            if (t2.hasArgued)
                t1.respond();
            else t1.reiterate();
        }
    }
}