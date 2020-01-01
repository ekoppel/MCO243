package MonitorsAndSemaphors;

import static MonitorsAndSemaphors.Alice.aliceInRoom;
import static MonitorsAndSemaphors.Main.*;

public class Bob extends Member {
    static boolean bobInRoom = false;

    Bob() {
        super("Bob");
    }

    @Override
    public void run() {
        wakeUp();
        goToWork();
        cameHome();
        synchronized (food) {
            try {
                food.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eatDinner();
        goToRoom();
    }

    private void wakeUp() {
        System.out.println("Alice woke " + getName() + " up");
    }

    private void goToWork() {
        System.out.println("Alice gives Bob lunch and a kiss");
        System.out.println("Bob goes to work in the accountant office");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cameHome() {
        if (!gnomesCameHome) {
            synchronized (office) {
                try {
                    office.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Bob came home");
        alice.start();
    }

    private void goToRoom() {
        bobInRoom = true;
        if (!aliceInRoom)
            System.out.println("Lights have turned on in the room");
        System.out.println(getName() + " is reading");
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bobInRoom = false;
        if (!aliceInRoom)
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