package PC_4_Cond;

public class Producer implements Runnable{

    private final Buffer buffer;
    private final int id;
    private final int value;
    private boolean running=true;



    public Producer(Buffer buffer, int id, int value){

        this.buffer=buffer;
        this.id=id;
        this.value=value;
    }

    @Override
    public void run() {
        while(running){
            try {
                buffer.produce(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop(){
        running=false;
    }
}
