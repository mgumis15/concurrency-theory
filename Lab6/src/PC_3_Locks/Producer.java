package PC_3_Locks;

import java.util.Random;

public class Producer implements Runnable{

    private final Buffer buffer;
    private final int id;
    private final int maxValue;
    private boolean running=true;
    private final int computationNumber;



    public Producer(Buffer buffer, int id,int computationNumber, int maxValue){

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
                    x*=Math.sin(i)*Math.cos(i)/Math.cos(i)+2;
                }
                buffer.produce(rand.nextInt(maxValue));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop(){
        running=false;
    }
}
