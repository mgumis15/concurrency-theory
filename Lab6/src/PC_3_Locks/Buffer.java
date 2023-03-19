package PC_3_Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buff=0;
    private int clientAccesses=0;
    private final int buff_MAX;
    private final ReentrantLock buffLock=new ReentrantLock();
    private final ReentrantLock producerLock=new ReentrantLock();
    private final ReentrantLock consumerock=new ReentrantLock();
    private final Condition buffCond=buffLock.newCondition();
    private int computationNumber;

    public Buffer(int buff_MAX, int computationNumber){
        this.buff_MAX=buff_MAX;
        this.computationNumber=computationNumber;
    }

    public  void produce(int toProduce) throws InterruptedException {
        producerLock.lock();
        try{
            buffLock.lock();
            while(buff+toProduce>=buff_MAX){
                buffCond.await();
            }

            buff+=toProduce;
            clientAccesses++;
            double x=1;
            for (int i = 0; i < computationNumber; i++) {
                x*=Math.sin(i)*Math.cos(i)/Math.cos(i)+2;
            }
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
            clientAccesses++;
            double x=1;
            for (int i = 0; i < computationNumber; i++) {
                x*=Math.sin(i)*Math.cos(i)/Math.cos(i)+2;
            }
            buffCond.signal();
            buffLock.unlock();
        }finally {
            consumerock.unlock();
        }
    }


    public int getClientAccesses() {
        return clientAccesses;
    }

}
