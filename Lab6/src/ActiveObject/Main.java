package ActiveObject;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;

public class Main {

    public static void   main(String[] args) {
        System.out.println("Active Object");
//        ArrayList<Integer> testingClientsNumber=new ArrayList<>(Arrays.asList(1,2,3,5,8,50,1000));
        ArrayList<Integer> testingClientsNumber=new ArrayList<>(Arrays.asList(1));
        for(Integer k :testingClientsNumber){
            int producersQuantity=k;
            int consumersQuantity=k;
            int maxBuff=20;
            int maxValue=10;
            int schedulerSinToCompute=5000;
            int clientSinToCompute=500000;
            ActivationQueue activationQueue = new ActivationQueue();
            Scheduler scheduler = new Scheduler(maxBuff, activationQueue);
            Thread schedulerThread = new Thread(scheduler);
            schedulerThread.start();

            ArrayList<Client> clients = new ArrayList<>();
            ArrayList<Thread> clientsThread = new ArrayList<>();

            for (int i = 0; i < producersQuantity; i++) {
                clients.add(new Client("Producer", activationQueue, i, maxValue,clientSinToCompute,schedulerSinToCompute,1));

            }
            for (int i = 0; i < consumersQuantity; i++) {
                clients.add(new Client("Consumer", activationQueue, i, maxValue,clientSinToCompute,schedulerSinToCompute,-1));
            }

            clients.forEach(client -> clientsThread.add(new Thread(client)));
            clientsThread.forEach(Thread::start);
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int buffTasks=0;
            int clientsTasks=0;
            ArrayList<Long> cpuTimes = new ArrayList<>();
            for (int i = 0; i < 2*k; i++) {
                cpuTimes.add(cpuTime(clientsThread.get(i)));
                ArrayList<Integer> clientresults=clients.get(i).stopAndReturnCounter();
                buffTasks+=clientresults.get(0);
                clientsTasks+=clientresults.get(1);
            }
            scheduler.stop();

            OptionalDouble avgTimeCpu = cpuTimes.stream().mapToDouble(a -> a).average();
            System.out.println("P/C = " + 2*k);
            System.out.printf("All clients accesses: %d\n", buffTasks);
            System.out.printf("Avg client accesses: %d\n", buffTasks/(2*k));
            System.out.printf("Client own tasks: %d\n", clientsTasks);
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

