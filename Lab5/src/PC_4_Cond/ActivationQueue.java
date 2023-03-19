package PC_4_Cond;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {
    private ArrayDeque<Future> activationQueue = new ArrayDeque<>();
    private final ReentrantLock queueLock=new ReentrantLock();
    private final Condition isQueueEmpty=queueLock.newCondition();

    public Future enqueue(Task function){
        queueLock.lock();
        Future future=new Future(function);
        System.out.println("Sth is added to ActiationQueue");
        activationQueue.add(future);
        isQueueEmpty.signal();

        queueLock.unlock();
        return future;

    }
    public Future dequeue(){
        queueLock.lock();
        try {
            System.out.println("Scheduler: want to get sth from ActivationQueue");
            while(activationQueue.isEmpty()){
                System.out.println("ActivationQueue is empty :(");
                isQueueEmpty.await();
            }
            System.out.println("Scheduler: got sth from Activation Queue");
            return activationQueue.poll();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  null;
        }finally {
            queueLock.unlock();

        }
    }
}
