package MonitorsAndSemaphors;

import static MonitorsAndSemaphors.Main.*;

public class Minion extends Member {

    private boolean isInside = false;

    Minion(int i) {
        super("Minion" + i);
    }

    @Override
    public void run() {
        goToWork();
        comeBackFromWork();
        synchronized (food) {
            try {
                food.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        eatDinner();
        goToSleep();
    }

    private void goToWork() {
        synchronized (alice) {
            alice.giveLunch(this);
            System.out.println(getName() + " goes to work in the deli shop");
        }
        minionsThatLeft++;
        if (minionsThatLeft == minions.length)
            synchronized (door) {
                door.notifyAll();
            }
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void comeBackFromWork() {
        minionQueue.add(this);
        synchronized (house) {
            if (minionQueue.size() < minions.length) {
                try {
                    house.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (minionQueue.peek() != null) {
                    minionQueue.peek().isInside = true;
                    System.out.println(minionQueue.poll().getName() + " has come home to play games");
                }
            } else if (minionQueue.size() == minions.length) {
                house.notifyAll();
                for (int i = 0; i < minionQueue.size(); i++) {
                    System.out.println(minionQueue.poll().getName() + " has come home to play games");
                }
            }
        }
        if (minionQueue.isEmpty()) {
            minionsCameHome = true;
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }

    private void goToSleep() {
        System.out.println(getName() + " went to sleep");
    }
}