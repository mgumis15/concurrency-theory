package PC_4_Cond;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.Random;

public class Main {

    public static void   main(String[] args) throws InterruptedException {
//        int producersQuantity=3;
//        int consumersQuantity=3;
//        int maxBuff=20;
//        int maxOperation=10;
//
//        Random random=new Random(48);
//
//        ActivationQueue activationQueue=new ActivationQueue();
//        Scheduler scheduler=new Scheduler(maxBuff,activationQueue);
//        Thread schedulerThread=new Thread(scheduler);
//        schedulerThread.start();
//
//        ArrayList<Client> clients=new ArrayList<>();
//        ArrayList<Thread> clientsThread=new ArrayList<>();
//
//        for (int i = 0; i < producersQuantity; i++) {
//            clients.add(new Client("Producer",activationQueue,i,random.nextInt(maxOperation+1)));
//
//        }
//        for (int i = 0; i < consumersQuantity; i++) {
//            clients.add(new Client("Consumer",activationQueue,i,-1*random.nextInt(maxOperation+1)));
//        }
//
//        clients.forEach(client -> clientsThread.add(new Thread(client)));
//        clientsThread.forEach(Thread::start);
        double sinx=0;
        double new_sinx=2;
        double x=0.1833*Math.PI;
        double power_x;
        double fact;
        int s=1;
        int i=1;
        while(Math.abs(sinx-new_sinx)>0.000000000001) {
            new_sinx=sinx;
            power_x=1;
            fact=1;
            for (int j = 1; j <= i; j++) {
                power_x*=x;
                fact*=j;
            }

            sinx+=s*power_x/fact;
            System.out.println(Math.abs(sinx));
            s*=-1;
            i+=2;
        }
        System.out.println(sinx);


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

