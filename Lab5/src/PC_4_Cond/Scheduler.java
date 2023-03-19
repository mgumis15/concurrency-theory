package PC_4_Cond;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler implements Runnable{


    private final ActivationQueue activationQueue;
    private ArrayDeque<Future> prioritizedQueue = new ArrayDeque<>();
    private final int maxBuffer;
    private int buffer=0;

    public Scheduler(int maxBuffer, ActivationQueue activationQueue) {
        this.maxBuffer = maxBuffer;
        this.activationQueue=activationQueue;
    }

    private boolean canModifyBuffer(Future future){
        return buffer + future.getFunction().getBufferChange()<=maxBuffer && buffer+future.getFunction().getBufferChange()>=0;
    }

    @Override
    public void run() {
        while(true){
            Future future;
            Task function;

            if(prioritizedQueue.isEmpty()||!canModifyBuffer(prioritizedQueue.getFirst())){
                System.out.println("Scheduler: Check Pro Que");
                future = activationQueue.dequeue();
            }else{
                System.out.println("Scheduler: Take from Pro Que");

                future = prioritizedQueue.poll();
            }
            System.out.println("Scheduler: Got sth to do");

            if(!canModifyBuffer(future)){
                prioritizedQueue.add(future);
                System.out.println("Scheduler: Add task to Pro Que");
                continue;
            }

            function = future.getFunction();
            function.run();
            System.out.println("Scheduler did sth: "+function.getBufferChange());
            buffer+=function.getBufferChange();
            future.makeReady();
        }
    }
}
