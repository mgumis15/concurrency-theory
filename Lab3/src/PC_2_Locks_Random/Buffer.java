package PC_2_Locks_Random;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff=0;
    private final int buff_MAX;

    private final Lock lock=new ReentrantLock();
    private final Condition buffFull=lock.newCondition();
    private final Condition buffEmpty=lock.newCondition();

    public Buffer(int buff_MAX){
        this.buff_MAX=buff_MAX;
    }

    public  void produce(int toProduce) throws InterruptedException {
        lock.lock();
        try{
            while(buff+toProduce>=buff_MAX){
                buffFull.await();
            }
            buff+=toProduce;
//            System.out.println("Product produced "+buff);
            buffEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public void consume(int toConsume) throws InterruptedException {
        lock.lock();
        try{
            while(buff<toConsume){
                buffEmpty.await();
            }
            buff-=toConsume;
//            System.out.println("Product consumed "+buff);
            buffFull.signal();
        }finally {
            lock.unlock();
        }

    }
}
