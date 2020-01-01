package MonitorsAndSemaphors;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {
    static final Alice alice = new Alice();
    static final Bob bob = new Bob();
    static final Minion[] minions = new Minion[10];
    static final Gnome[] gnomes = new Gnome[7];
    static Queue<Minion> minionQueue = new LinkedList<>();
    static Stack<Gnome> gnomeStack = new Stack<>();
    static int minionsThatLeft = 0;
    static boolean minionsCameHome = false;
    static int gnomesThatLeft = 0;
    static boolean gnomesCameHome = false;
    static final Object house = new Object();
    static final Object door = new Object();
    static final Object monitor = new Object();
    static final Object office = new Object();
    static final Object food = new Object();
    static int seatsAvailable = 5;

    public static void main(String[] args) {
        for (int i = 0; i < minions.length; i++) {
            minions[i] = new Minion(i + 1);
            minions[i].start();
            if (i < gnomes.length) {
                gnomes[i] = new Gnome(i + 1);
                gnomes[i].start();
            }
        }
    }
}