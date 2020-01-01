package Multithreading;

import java.util.Random;

import static java.lang.System.*;

public class FamilyMember extends Thread {
    private Room r;
    private Random rand = new Random();

    public FamilyMember() {
        r = Room.values()[rand.nextInt(Room.values().length)];
        r.occupiers++;
    }

    enum Room {
        ONE(0),
        TWO(0),
        THREE(0),
        FOUR(0),
        FIVE(0),
        SIX(0),
        SEVEN(0),
        EIGHT(0),
        NINE(0),
        TEN(0);

        public int occupiers;
        private int energyOutput;

        Room(int energyOutput) {
            this.energyOutput = energyOutput;
        }

        public void addEnergyOutput(int energyOutput) {
            this.energyOutput += energyOutput;
        }

        public int getEnergyOutput() {
            return energyOutput;
        }
    }

    @Override
    public void run() {
        int timeInRoom = rand.nextInt(5000);
        try {
            sleep(timeInRoom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // if another is in the room, 15 per millisecond, otherwise 5
        r.addEnergyOutput(r.occupiers > 1 ? ((timeInRoom / 20) * 3) : (timeInRoom / 20));
        moveRooms();
    }

    private void moveRooms() {
        // deducts his presence from the occupied count
        r.occupiers--;
        // switches his room
        this.r = Room.values()[rand.nextInt(Room.values().length)];
        // adds his presence to the occupied count
        r.occupiers++;
    }

    public static void main(String[] args) {
        // add 4 family members
        FamilyMember[] members = new FamilyMember[4];
        for (int i = 0; i < 4; i++) {
            members[i] = new FamilyMember();
        }

        long start = currentTimeMillis();

        while (currentTimeMillis() < start + 5000) {
            for (FamilyMember fm : members){
                fm.run();
            }
        }

        // log the energy from each room
        for (Room r : Room.values()) {
            System.out.println(r.name() + ": " + r.getEnergyOutput());
            System.out.println(r.name() + ": " + r.occupiers);
        }
    }
}