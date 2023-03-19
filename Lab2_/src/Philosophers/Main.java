package Philosophers;

import ProducerConsumer.Buffer;
import ProducerConsumer.Consumer;
import ProducerConsumer.Producer;

import java.util.ArrayList;

public class Main {
    public static void   main(String[] args) throws InterruptedException {
        ArrayList<Philosopher> philosophers = new ArrayList<>();
        ArrayList<Fork> forks = new ArrayList<>();

        for(int i=0;i<5;i++){
            forks.add(new Fork(i));
        }

        for(int i=0;i<4;i++){
            Philosopher philosopher=new Philosopher(forks.get(i),forks.get((i+1)%5),i);
            philosophers.add(philosopher);
            Thread philosopherThread=new Thread(philosopher);
            philosopherThread.start();
        }
        Philosopher philosopher=new Philosopher(forks.get(0),forks.get(4),5);
        philosophers.add(philosopher);
        Thread philosopherThread=new Thread(philosopher);
        philosopherThread.start();

    }
}
