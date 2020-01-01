package Family;

import java.util.LinkedList;
import java.util.Queue;

import static Family.Parent.bed;
import static Family.Parent.reading;

public class Child extends Member {

    private static int childrenAwake = 0;
    private static Queue<Child> childQueue = new LinkedList<>();
    static boolean childrenReturned = false;
    private static final Object house = new Object();
    static final Object food = new Object();
    static final Object door = new Object();
    private boolean isInside = false;
    boolean isAwake = false;
    boolean hasBeenFed = false;
    boolean hasBathed = false;
    boolean hasRead = false;

    Child(int i) {
        super("Child" + i);
    }

    @Override
    public void run() {
        wakeUp();
        goToSchool();
        comeHome();
        synchronized (food) {
            try {
                food.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eatDinner();
        synchronized (reading) {
            try {
                reading.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fallAsleep();
    }

    private void wakeUp() {
        System.out.println(getName() + " woke up");
        isAwake = true;
        childrenAwake++;
        if (childrenAwake == 6) {
            synchronized (bed) {
                bed.notifyAll();
            }
        }
    }

    private void goToSchool() {
        try {
            sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void comeHome() {
        childQueue.add(this);
        synchronized (house) {
            if (childQueue.size() < 10 && !childQueue.isEmpty()) {
                try {
                    house.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (childQueue.peek() != null) {
                    childQueue.peek().isInside = true;
                    System.out.println(childQueue.poll().getName() + " has come inside");
                }
            } else if (childQueue.size() == 10) {
                childrenReturned = true;
                house.notifyAll();
                for (int i = 0; i < childQueue.size(); i++) {
                    if (childQueue.peek() != null && !childQueue.peek().isInside)
                        System.out.println(childQueue.poll().getName() + " has come inside");
                }
            }
        }
        if (childQueue.isEmpty()) {
            childrenReturned = true;
            synchronized (door) {
                door.notifyAll();
            }
        }
    }

    private void fallAsleep() {
        if (hasRead)
            System.out.println(getName() + " has fallen asleep");
        else {
            try {
                sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fallAsleep();
        }
    }
}