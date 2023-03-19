package ProdConsRand;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff;
    private final int buff_MAX;
    private final int M;
    private final Lock lock=new ReentrantLock();
    private final Condition proProducer=lock.newCondition();
    private final Condition minorProducers=lock.newCondition();
    private final Condition proConsumer=lock.newCondition();
    private final Condition minorConsumers=lock.newCondition();
    private Thread proProducerThread;
    private Thread proConsumerThread;

    public Buffer(int buff,int buff_MAX, int M){
        this.buff=buff;
        this.buff_MAX=buff_MAX;
        this.M=M;
    }

    public  void produce() throws InterruptedException {
        Random rand=new Random();
        int prod=rand.nextInt(M)+1;
        lock.lock();
        try{
//            while(){
                minorProducers.await();
                while(buff+prod>=buff_MAX){
                    proProducer.await();
                }
//            }

            buff+=prod;
            System.out.println("Product produced "+buff);
            proConsumer.signal();
            minorProducers.signal();
        }finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        Random rand=new Random();
        int take=rand.nextInt(M)+1;
        lock.lock();
        try{
            while(buff-take<0){
                proConsumer.await();
            }
            buff-=take;
            System.out.println("Product consumed "+buff);
            proProducer.signal();
            minorConsumers.signal();
        }finally {
            lock.unlock();
        }

    }
}
