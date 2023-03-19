package PC_4_Cond;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff=0;
    private int producersAccesses=0;
    private int consumerAccesses=0;
    private final int buff_MAX;
    private final ReentrantLock lock=new ReentrantLock();
    private final Condition proProducer=lock.newCondition();
    private final Condition minorProducers=lock.newCondition();
    private final Condition proConsumer=lock.newCondition();
    private final Condition minorConsumers=lock.newCondition();
    private boolean proProducerIn=false;
    private boolean proConsumerIn=false;

    public Buffer(int buff_MAX){
        this.buff_MAX=buff_MAX;
    }

    public  void produce(int toProduce) throws InterruptedException {
        lock.lock();
        try{
            while(proProducerIn){
                minorProducers.await();
            }
            while(buff+toProduce>=buff_MAX){
                proProducerIn=true;
                proProducer.await();
            }

            buff+=toProduce;
            producersAccesses++;
            proProducerIn=false;
            proConsumer.signal();
            minorProducers.signal();
        }finally {
            lock.unlock();
        }
    }

    public void consume(int toConsume) throws InterruptedException {
        lock.lock();
        try{

            while(proConsumerIn){
                minorConsumers.await();
            }
            while(buff-toConsume<0){
                proConsumerIn=true;
                proConsumer.await();
            }

            buff-=toConsume;
            consumerAccesses++;
            proConsumerIn=false;
            proProducer.signal();
            minorConsumers.signal();
        }finally {
            lock.unlock();
        }
    }

    public int getConsumerAccesses() {
        return consumerAccesses;
    }

    public int getProducersAccesses() {
        return producersAccesses;
    }
}
