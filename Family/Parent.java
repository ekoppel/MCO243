package Family;

import java.util.concurrent.Semaphore;

import static Family.Child.childrenReturned;
import static Family.Child.door;
import static Family.Child.food;
import static Family.Main.*;

public class Parent extends Member {

    private boolean wokeUp = false;
    static final Object bed = new Object();
    static final Object reading = new Object();
    private int childrenReadTo = 0;

    Parent(int i) {
        super("parent" + i);
    }

    @Override
    public void run() {
        wasWokenUp();
        wakeUpOnHisOwn();
        feedChildren();
        giveBaths();
        eatDinner();
        readBooks();
        putChildrenToSleep();
    }

    private void wasWokenUp() {
        synchronized (bed) {
            try {
                bed.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!wokeUp) {
            wokeUp = true;
            System.out.println(getName() + " was woken up");
        }
    }

    private void wakeUpOnHisOwn() {
        try {
            sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!wokeUp) {
            wokeUp = true;
            System.out.println(getName() + " woke up");
        }
    }

    private synchronized void feedChildren() {
        for (Child child : children) {
            while (!child.isAwake) ;
            if (!child.hasBeenFed) {
                System.out.println(child.getName() + " is eating breakfast and going to school");
                child.hasBeenFed = true;
            }
        }
    }

    private synchronized void giveBaths() {
        if (!childrenReturned) {
            synchronized (door) {
                try {
                    door.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Child c : children) {
            if (c.hasBathed) ;
            else {
//                Semaphore bath = new Semaphore(1);
//                try {
//                    bath.acquire();
                System.out.println(c.getName() + " has bathed");
//                    Thread.sleep((long) (Math.random() * 1000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    bath.release();
//                }
                c.hasBathed = true;
            }
        }
        synchronized (food) {
            food.notifyAll();
        }
    }

    private void readBooks() {
        while (seatsAvailable < 4) {
            try {
                sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            if (childrenReadTo == 5)
                break;
            if (children[i].hasRead)
                continue;
            children[i].hasRead = true;
            System.out.println(getName() + " is reading to " + children[i].getName());
            childrenReadTo++;
        }
    }

    private void putChildrenToSleep() {
        synchronized (reading) {
            reading.notifyAll();
        }
    }
}