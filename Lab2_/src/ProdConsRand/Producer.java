package ProdConsRand;

public class Producer implements Runnable{

    private final Buffer buffer;
    private final int id;

    public Producer(Buffer buffer, int id){

        this.buffer=buffer;
        this.id=id;
    }

    @Override
    public void run() {
        for (;;){
            try {
                buffer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public int getId() {
        return id;
    }
}
