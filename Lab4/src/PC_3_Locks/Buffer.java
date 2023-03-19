package PC_3_Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff=0;
    private int producersAccesses=0;
    private int consumerAccesses=0;
    private final int buff_MAX;
    private final ReentrantLock buffLock=new ReentrantLock();
    private final ReentrantLock producerLock=new ReentrantLock();
    private final ReentrantLock consumerock=new ReentrantLock();
    private final Condition buffCond=buffLock.newCondition();

    public Buffer(int buff_MAX){
        this.buff_MAX=buff_MAX;
    }

    public  void produce(int toProduce) throws InterruptedException {
        producerLock.lock();
        try{
            buffLock.lock();
            while(buff+toProduce>=buff_MAX){
                buffCond.await();
            }

            buff+=toProduce;
            producersAccesses++;
            buffCond.signal();
            buffLock.unlock();
        }finally {
            producerLock.unlock();
        }
    }

    public void consume(int toConsume) throws InterruptedException {
        consumerock.lock();
        try{

            buffLock.lock();
            while(buff-toConsume<0){
                buffCond.await();
            }

            buff-=toConsume;
            consumerAccesses++;
            buffCond.signal();
            buffLock.unlock();
        }finally {
            consumerock.unlock();
        }
    }

    public int getConsumerAccesses() {
        return consumerAccesses;
    }

    public int getProducersAccesses() {
        return producersAccesses;
    }
}
