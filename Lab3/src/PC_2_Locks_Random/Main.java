package PC_2_Locks_Random;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
        int m=3;
        int n=3;
        int M=10;
        int w=20;

        Random rand=new Random();

        Buffer buffer=new Buffer(w);
        ArrayList<Thread> producers=new ArrayList<>();
        ArrayList<Thread> consumers=new ArrayList<>();

        for (int i = 0; i < m-1 ; i++) {
            producers.add(new Thread(new Producer(buffer,i,rand.nextInt(2)+1)));
        }
        producers.add(new Thread(new Producer(buffer,m-1,M)));

        for (int i = 0; i < n-1 ; i++) {
            consumers.add(new Thread(new Consumer(buffer,i,rand.nextInt(2)+1)));
        }
        consumers.add(new Thread(new Consumer(buffer,m-1,M)));

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        for (int i = 0; i < m ; i++) {
            producers.get(i).join();
        }
        for (int i = 0; i < n ; i++) {
            consumers.get(i).join();
        }
    }
}
//Zakleszczenie: P1-7,P2-9,C1-8,C2-10 (M=10) MaxBuff=15

//P1 - produkuje => buff=7
//C1 - czeka, bo 7<8
//C2 - czeka, bo 7<10
//P2 - czeka, bo 7+9=16>15
//P1 - wchodzi, buff=14 - budzi P2
//P1 - czeka bo 21>15
//P2 - czeka bo 23>15
//Wszystkie wątki czękają

//Zagłodzenie - Wiele wątków porducentów i konsumentów
