package BarberShop;

import java.util.LinkedList;

public class BarberShop {
    private static LinkedList<Client> clients = new LinkedList<>();

    private void takeASeat(){
        for (int i = 1; i <= 10; i++) {
            clients.add(new Client(i));
        }
    }

    public static void main(String[] args) {
        Barber b1 = new Barber("BarberShop.Barber 1", clients);
        Barber b2 = new Barber("BarberShop.Barber 2", clients);
        BarberShop barberShop = new BarberShop();
        barberShop.takeASeat();
        b1.start();
        b2.start();
        while (!clients.isEmpty()) {
            b1.run();
            b2.run();
        }
    }
}
