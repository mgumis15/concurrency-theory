package PC_3_Locks;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
        System.out.println("Three locks");
        ArrayList<Integer> testingClientsNumber=new ArrayList<>(Arrays.asList(3));
//        ArrayList<Integer> testingClientsNumber=new ArrayList<>(Arrays.asList(1,2,3,5,10,50,1000));
        for (Integer k:testingClientsNumber) {
            int m = k;
            int n = k;
            int M = 10;
            int w = 1000;
            int bufferSinToCompute=100000;
            int clientSinToCompute=100000;



            Buffer buffer = new Buffer(w,bufferSinToCompute);
            ArrayList<Thread> producersThreads = new ArrayList<>();
            ArrayList<Producer> producers = new ArrayList<>();
            ArrayList<Thread> consumersThreads = new ArrayList<>();
            ArrayList<Consumer> consumers = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                Producer producer = new Producer(buffer, i,clientSinToCompute, M+1);
                producers.add(producer);
                producersThreads.add(new Thread(producer));
            }

            for (int i = 0; i < n; i++) {
                Consumer consumer = new Consumer(buffer, i,clientSinToCompute, M+1);
                consumers.add(consumer);
                consumersThreads.add(new Thread(consumer));
            }

            producersThreads.forEach(Thread::start);
            consumersThreads.forEach(Thread::start);


            Thread.sleep(120000);

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

            System.out.println("P/C = " + 2*k);
            System.out.printf("All clients accesses: %d\n", buffer.getClientAccesses());
            System.out.printf("Avg client accesses: %d\n", buffer.getClientAccesses()/(2*k));
            System.out.printf("Average cpu time: %.3f\n", avgTimeCpu.isPresent() ? avgTimeCpu.getAsDouble() / 1000000000 : 0.0);
        }


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

