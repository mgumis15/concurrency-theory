package PC_4_Locks_Random;

public class Producer implements Runnable{

    private final Buffer buffer;
    private final int id;
    private final int value;
    private int timesProduce=0;


    public Producer(Buffer buffer, int id, int value){

        this.buffer=buffer;
        this.id=id;
        this.value=value;
    }

    @Override
    public void run() {
        for(;;){
            try {
                buffer.produce(value);
                timesProduce++;
                System.out.println("Producer "+id+" produced "+value+" times: "+timesProduce );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
