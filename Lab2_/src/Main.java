import java.util.ArrayList;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
        int m=1;
        int n=1;
        int w=1;

        Buffer buffer=new Buffer(0,w);
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
// producent 1 - P1, producent 2 - P2, konsument  1 - K1
//         Na samym początku dostęp do buforu otrzymuje P1 i wypełnia bufor wartością "1"
//         Wywołuje notify() i opuszcza bufor
//         Teraz do buforu bedzie wpuszczony P2, ale przez zapełniony bufor przejdzie w wait()
//         Do buforu może wejść K lub P1, zakładamy, że wchodzi konsument, zmienia "1" na "0" i wywołuje notify()
//         Wchodzi K1, wiesza się wait()
//         Wchodzi P1, wpisuje "1"
//         Wchodzo P2, wiesza się wait()
//         wchodzi P1, wiesza się wait()
//         Wchodzi K1, zeruje i wybudza P1
//         Wchodzi K1, wiesza się wait()
//         Wchodzi P1, wpisuje "1" i wybudza przez notify() P2
//         Jesteśmy w stanie, gdzie w buforze jest 1, a na wejście w stanie Ready-to-Run oczekuje 2 producentów
//         Mazur Mateusz i Michał Godek