package ProducerConsumerLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff;
    private final int buff_MAX;

    private final Lock lock=new ReentrantLock();
    private final Condition buffFull=lock.newCondition();
    private final Condition buffEmpty=lock.newCondition();



    public Buffer(int buff,int buff_MAX){
        this.buff=buff;
        this.buff_MAX=buff_MAX;
    }

    public  void produce() throws InterruptedException {
        lock.lock();
        try{
            while(buff>=buff_MAX){
                buffFull.await();
            }
            buff+=1;
            System.out.println("Product produced "+buff);
            buffEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try{
            while(buff==0){
                buffEmpty.await();
            }
            buff-=1;
            System.out.println("Product consumed "+buff);
            buffFull.signal();
        }finally {
            lock.unlock();
        }

    }
}
