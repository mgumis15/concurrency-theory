package Philosophers;

import java.util.ArrayList;

public class Philosopher implements Runnable {
    private  final Fork leftFork;
    private final Fork rightFork;
    private final int id;

    public  Philosopher(Fork leftFork,Fork rightFork,int id){
        this.leftFork=leftFork;
        this.rightFork=rightFork;
        this.id=id;
    }

    @Override
    public void run() {
        try {
            for (int i=0;i<1000;i++){


                leftFork.takeFork();
//                System.out.println("Philo: "+id+" take fork: "+leftFork.getId());
                rightFork.takeFork();
//                System.out.println("Philo: "+id+" take fork: "+leftFork.getId());
                rightFork.leaveFork();
//                System.out.println("Philo: "+id+" leave fork: "+leftFork.getId());
                leftFork.leaveFork();
//                System.out.println("Philo: "+id+" leave fork: "+leftFork.getId());


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("AAAAAAAAA Philo finished "+id);
    }
}
