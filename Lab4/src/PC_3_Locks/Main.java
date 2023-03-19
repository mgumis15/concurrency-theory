package PC_3_Locks;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void   main(String[] args) throws InterruptedException {

        ArrayList<Double> finalAvgCpuTimes=new ArrayList<>();
        ArrayList<Double> finalTotalCpuTimes=new ArrayList<>();
        ArrayList<Double> finalProducersAccess=new ArrayList<>();
        ArrayList<Double> finalConsumersAccess=new ArrayList<>();

        for (int k = 1; k <= 10; k++) {

            int m=k;
            int n=k;
            int M=10;
            int w=20;

            ArrayList<Double> perQuantityAvgCpuTimes=new ArrayList<>();
            ArrayList<Double> perQuantityTotalCpuTimes=new ArrayList<>();
            ArrayList<Integer> perQuantityProducersAccess=new ArrayList<>();
            ArrayList<Integer> perQuantityConsumersAccess=new ArrayList<>();

            for (int j = 0; j < 10; j++) {
                Random rand=new Random(48);
                Buffer buffer = new Buffer(w);
                ArrayList<Thread> producersThreads = new ArrayList<>();
                ArrayList<Producer> producers = new ArrayList<>();
                ArrayList<Thread> consumersThreads = new ArrayList<>();
                ArrayList<Consumer> consumers = new ArrayList<>();

                for (int i = 0; i < m; i++) {
                    Producer producer = new Producer(buffer, i, rand.nextInt(M + 1));
                    producers.add(producer);
                    producersThreads.add(new Thread(producer));
                }

                for (int i = 0; i < n; i++) {
                    Consumer consumer = new Consumer(buffer, i, rand.nextInt(M + 1));
                    consumers.add(consumer);
                    consumersThreads.add(new Thread(consumer));
                }

                producersThreads.forEach(Thread::start);
                consumersThreads.forEach(Thread::start);


                Thread.sleep(1000);

                ArrayList<Long> cpuTimes = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    cpuTimes.add(cpuTime(producersThreads.get(i)));
                    producers.get(i).stop();

                }
                for (int i = 0; i < n; i++) {
                    cpuTimes.add(cpuTime(consumersThreads.get(i)));
                    consumers.get(i).stop();
                }

                OptionalDouble avgTimeCpu = cpuTimes.stream().mapToDouble(a -> a).average();
                int producersAccess = buffer.getProducersAccesses();
                int consumerAccesses = buffer.getConsumerAccesses();
                perQuantityAvgCpuTimes.add(avgTimeCpu.isPresent() ? avgTimeCpu.getAsDouble() : 0);
                perQuantityTotalCpuTimes.add(cpuTimes.stream().mapToDouble(a -> a).sum());
                perQuantityConsumersAccess.add(consumerAccesses);
                perQuantityProducersAccess.add(producersAccess);
            }
            OptionalDouble avgTimeCpu = perQuantityAvgCpuTimes.stream().mapToDouble(a -> a).average();
            OptionalDouble totalTimeCpu = perQuantityTotalCpuTimes.stream().mapToDouble(a -> a).average();
            OptionalDouble perQuantityProducersAccessAvg = perQuantityProducersAccess.stream().mapToDouble(a -> a).average();
            OptionalDouble perQuantityConsumersAccessAvg = perQuantityConsumersAccess.stream().mapToDouble(a -> a).average();

            finalAvgCpuTimes.add(avgTimeCpu.isPresent()?avgTimeCpu.getAsDouble()/1000000000:0.0);
            finalTotalCpuTimes.add(totalTimeCpu.isPresent()?totalTimeCpu.getAsDouble()/1000000000:0.0);
            finalConsumersAccess.add(perQuantityConsumersAccessAvg.isPresent()?perQuantityConsumersAccessAvg.getAsDouble():0.0);
            finalProducersAccess.add(perQuantityProducersAccessAvg.isPresent()?perQuantityProducersAccessAvg.getAsDouble():0.0);

            System.out.println("P/C = "+k);
            System.out.printf("Producer accesses: %.3f , Consumer accesses: %.3f \n",perQuantityProducersAccessAvg.isPresent()?perQuantityProducersAccessAvg.getAsDouble():0.0,perQuantityConsumersAccessAvg.isPresent()?perQuantityConsumersAccessAvg.getAsDouble():0.0);
            System.out.printf("Average cpu time: %.3f\n",avgTimeCpu.isPresent()?avgTimeCpu.getAsDouble()/1000000000:0.0);
            System.out.printf("Total cpu time: %.3f\n\n\n",totalTimeCpu.isPresent()?totalTimeCpu.getAsDouble()/1000000000:0.0);
        }
        System.out.println("Summary:");
        System.out.println("Avg time: "+finalAvgCpuTimes.toString());
        System.out.println("Total time: "+finalTotalCpuTimes.toString());
        System.out.println("Producers accesses: "+finalProducersAccess.toString());
        System.out.println("Consumers accesses: "+finalConsumersAccess.toString());

    }
    private static long cpuTime(Thread thread){
        ThreadMXBean mxBean= ManagementFactory.getThreadMXBean();
        if(mxBean.isThreadCpuTimeSupported()){
            try{
                return mxBean.getThreadCpuTime(thread.getId());
            }catch (UnsupportedOperationException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Not supported");
        }
        return 0;

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



