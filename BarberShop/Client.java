package BarberShop;

public class Client extends Thread {
    private String clientName;
    Client(int name){
        this.clientName = "" + name;
        this.start();
    }
    public void run(){
        System.out.printf("BarberShop.Client%s has entered the shop\n", clientName);
    }

    String getClientName() {
        return clientName;
    }
}
