package BarberShop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Barber extends Thread {
    private String barberName;
    private static LinkedList<Client> couch;
    private Random haircutSpeed = new Random();

    Barber(String name, LinkedList<Client> couch) {
        this.barberName = name;
        Barber.couch = couch;
    }

    public void run() {
        try {
            Thread.sleep(haircutSpeed.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            this.giveHaircut(couch);
    }

    private void giveHaircut(LinkedList<Client> couch) {
        Iterator iter = couch.iterator();
        if (!iter.hasNext())
            return;
        System.out.printf("BarberShop.Client%s is getting a haircut with %s\n", couch.get(0).getClientName(), this.barberName);
        //run();
        System.out.printf("BarberShop.Client%s has finished getting a haircut\n", couch.get(0).getClientName());
        couch.remove(0);
    }
}