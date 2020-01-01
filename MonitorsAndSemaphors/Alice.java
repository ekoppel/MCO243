package MonitorsAndSemaphors;

import static MonitorsAndSemaphors.Bob.bobInRoom;
import static MonitorsAndSemaphors.Main.food;

public class Alice extends Member {
    static boolean aliceInRoom = false;

    Alice() {
        super("Alice");
    }

    @Override
    public void run() {
        prepareDinner();
        eatDinner();
        goToRoom();
    }

    void giveLunch(Member member) {
        System.out.println("Alice gives " + member.getName() + " lunch and a kiss");
    }

    private void prepareDinner() {
        System.out.println("Alice is preparing dinner");
        synchronized (food) {
            food.notifyAll();
        }
    }

    private void goToRoom() {
        aliceInRoom = true;
        if (!bobInRoom)
            System.out.println("lights have turned on");
        System.out.println(getName() + " is reading");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        aliceInRoom = false;
        if (!bobInRoom)
            System.out.println("lights have turned off");
        System.out.println(getName() + " went to the couch");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + " went to sleep");
    }
}