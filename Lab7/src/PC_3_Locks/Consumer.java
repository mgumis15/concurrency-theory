package PC_3_Locks;

import java.util.Random;

public class Consumer implements Runnable{

    private final Buffer buffer;
    private final int id;
    private final int maxValue;
    private boolean running=true;
    private final int computationNumber;

    public Consumer(Buffer buffer, int id,int computationNumber, int maxValue){
        this.buffer=buffer;
        this.id=id;
        this.computationNumber=computationNumber;
        this.maxValue=maxValue;
    }

    @Override
    public void run() {
        Random rand = new Random(48);
        while(running){
            try {
                double x=1;
                for (int i = 0; i < computationNumber; i++) {
                    x=Math.sin(i)*Math.cos(i);
                }
                buffer.consume(rand.nextInt(maxValue));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop(){
        running=false;
    }

}
