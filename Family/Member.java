package Family;

import static Family.Main.seatsAvailable;

abstract class Member extends Thread {
    Member(String s) {
        super(s);
    }

    synchronized void eatDinner() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
            System.out.println(getName() + " is eating dinner");
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seatsAvailable++;
            System.out.println(getName() + " is done eating dinner");
        } else {
            try {
                sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eatDinner();
        }
    }
}
