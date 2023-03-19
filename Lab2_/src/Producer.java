public class Producer implements Runnable{

    private final Buffer buffer;
    private final int id;

    public Producer(Buffer buffer, int id){

        this.buffer=buffer;
        this.id=id;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++){
            try {
                buffer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        System.out.println("Producer finished");
    }

    public int getId() {
        return id;
    }
}
