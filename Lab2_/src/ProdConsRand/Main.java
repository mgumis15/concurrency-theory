package ProdConsRand;

import java.util.ArrayList;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
        int m=1;
        int n=1;
        int M=100;
        int w=2*M;

        Buffer buffer=new Buffer(0,w,M);
        ArrayList<Thread> producers=new ArrayList<>();
        ArrayList<Thread> consumers=new ArrayList<>();

        for (int i = 0; i < m ; i++) {
            producers.add(new Thread(new Producer(buffer,i)));
        }
        for (int i = 0; i < n ; i++) {
            consumers.add(new Thread(new Consumer(buffer,i)));
        }

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
