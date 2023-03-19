package PC_4_Locks_Random;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
        int m=1;
        int n=4;
        int M=10;
        int w=20;

        Random rand=new Random();

        Buffer buffer=new Buffer(w);
        ArrayList<Thread> producers=new ArrayList<>();
        ArrayList<Thread> consumers=new ArrayList<>();

        for (int i = 0; i < m-1 ; i++) {
            producers.add(new Thread(new Producer(buffer,i,M)));
        }
        producers.add(new Thread(new Producer(buffer,m-1,M)));

        for (int i = 0; i < n-1 ; i++) {
            consumers.add(new Thread(new Consumer(buffer,i,5)));
        }
        consumers.add(new Thread(new Consumer(buffer,n-1,5)));

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

//Zakleszczenie:
//1 - P10, 4 - C5 buff<=20

//Wchodzi C5_0, hasWaiters()==false -> wchodzi do buff i sprawdza następny warunek
//W tym czasie wchodzą pozostałe 3 C5, oczekując na sprawdzenie warunku buffera w trybie Ready-To-Run
//Ponieważ buff=0, wszystkie wątki się więszają (tak dochodzi do zagłodzenia)
//Wchodzi P10 -> buff=10, budzi C5_0 i sam kończąc działąnie wraca, czekając na cond od buffera
//C5_0 konsumuje -> buff=5, budzi P10 oraz cond hasWaiters() konsumentów (nikgoo tam nie ma)
// i wraca właśnie na ten cond
//P10 produkuje-> buff=15 budzi C5_1 i sam kończąc działąnie wraca,
//C5_1 konsumuje -> buff=10, budzi P10 oraz cond hasWaiters() konsumentów (Jest tam C5_0, ale hasWaiters()==true, więc się wiesza)
// i staje na hasWaiters()
//P10 produkuje-> buff=20 budzi C5_2 i sam kończąc działąnie wraca,
//C5_2 konsumuje -> buff=15, budzi P10 oraz cond hasWaiters() konsumentów (Jest tam C5_0 i C5_1, ale hasWaiters()==true, więc się wiesza)
// i staje na hasWaiters()
//P10 się wiesza, bo nie jest w stanie produkować nie budząc konsumenta, więc wszyscy są powieszeni



