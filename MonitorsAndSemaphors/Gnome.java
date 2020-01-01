package MonitorsAndSemaphors;

import java.util.concurrent.Semaphore;

import static MonitorsAndSemaphors.Main.*;

public class Gnome extends Member {

    private boolean isInside = false;

    Gnome(int i) {
        super("Gnome" + i);
    }

    @Override
    public void run() {
        goToWork();
        comeHomeFromWork();
        synchronized (food) {
            try {
                food.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eatDinner();
        goToTheBathroom();
    }

    private void goToWork() {
        if (minionsThatLeft < minions.length) {
            synchronized (door) {
                try {
                    door.wait();
                    alice.giveLunch(this);
                    System.out.println(getName() + " went to work in the mine");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        gnomesThatLeft++;
        if (gnomesThatLeft == gnomes.length) {
            bob.start();
        }
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void comeHomeFromWork() {
        gnomeStack.add(this);
        synchronized (monitor) {
            if (gnomeStack.size() < gnomes.length || !minionsCameHome) {

                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gnomeStack.peek().isInside = true;
                System.out.println(gnomeStack.pop().getName() + " has come home to play outside");

            } else if (gnomeStack.size() == gnomes.length) {
                monitor.notifyAll();
                for (int i = 0; i < gnomeStack.size(); i++) {
                    System.out.println(gnomeStack.pop().getName() + " has come home to play outside");
                }
            }
            if (gnomeStack.isEmpty()) {
                gnomesCameHome = true;
                synchronized (office) {
                    office.notify();
                }
            }
        }
    }

    private void goToTheBathroom() {
        Semaphore bathroom = new Semaphore(1);
        try {
            bathroom.acquire();
            System.out.println(getName() + " went to the bathroom");
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bathroom.release();
            System.out.println(getName() + " left the bathroom");
        }
        System.out.println(getName() + " went to sleep");
    }
}