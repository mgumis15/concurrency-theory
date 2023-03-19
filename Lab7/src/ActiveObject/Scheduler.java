package ActiveObject;

import java.util.ArrayDeque;

public class Scheduler implements Runnable{


    private final ActivationQueue activationQueue;
    private ArrayDeque<Future> prioritizedQueue = new ArrayDeque<>();
    private final int maxBuffer;
    private int buffer=0;
    private boolean running=true;


    public Scheduler(int maxBuffer, ActivationQueue activationQueue) {
        this.maxBuffer = maxBuffer;
        this.activationQueue=activationQueue;
    }
    //sprawdzenie warunków modyfikacji bufferu
    private boolean canModifyBuffer(Future future){
        return buffer + future.getFunction().getBufferChange()<=maxBuffer && buffer+future.getFunction().getBufferChange()>=0;
    }


    @Override
    public void run() {
        while(running){
            Future future;
            Task function;
//          Sprawdzenie, czy w kolejce priorytetowej ktoś się znajduje i czy może zmodyfikować buffor
            if(prioritizedQueue.isEmpty()||!canModifyBuffer(prioritizedQueue.getFirst())){
//                 System.out.println("Scheduler: Take from Activation Que");
                future = activationQueue.dequeue();

                if(!prioritizedQueue.isEmpty()&&prioritizedQueue.getFirst().getFunction().getBufferChange()*future.getFunction().getBufferChange()>0){
                    // System.out.println("Scheduler: Add task to Pro Que");
                    prioritizedQueue.add(future);
                    continue;
                }
            }else{
//                 System.out.println("Scheduler: Take from Pro Que");
                future = prioritizedQueue.poll();
            }
//          Jeżeli pobrany z ActivationQueue nie może wykonać zadania to dodajemy do kolejki priorytetowej
            if(!canModifyBuffer(future)){
                prioritizedQueue.add(future);
//                 System.out.println("Scheduler: Add task to Pro Que");
                continue;
            }


//          Wykonanie zadania i modyfikacja buforu
            function = future.getFunction();
            function.run();
//             System.out.println("Scheduler did sth: "+function.getBufferChange());
            buffer+=function.getBufferChange();
            future.makeReady();
        }
    }

    public void stop(){
        running=false;
    }
}
