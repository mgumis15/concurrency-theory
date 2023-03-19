package PC_4_Locks_Random;

public class Consumer implements Runnable{

    private final Buffer buffer;
    private final int id;
    private final int value;
    private int timesConsume=0;

    public Consumer(Buffer buffer, int id, int value){
        this.buffer=buffer;
        this.id=id;
        this.value=value;
    }

    @Override
    public void run() {

        for(;;){
            try {

                buffer.consume(value);
                timesConsume++;
                System.out.println("Consumer "+id+" consumed "+value+" times: "+timesConsume );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
