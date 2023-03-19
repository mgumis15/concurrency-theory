package ProducerConsumer;

import javax.management.monitor.Monitor;

public class Buffer {
    private int buff;
    private final int buff_MAX;


    public Buffer(int buff,int buff_MAX){
        this.buff=buff;
        this.buff_MAX=buff_MAX;
    }
    public synchronized void produce() throws InterruptedException {
        while(buff>=buff_MAX){
            wait();
        }
        buff+=1;
        System.out.println("Product produced "+buff);
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        while(buff==0){
            wait();
        }
        buff-=1;
        System.out.println("Product consumed "+buff);
        notifyAll();

    }
}
