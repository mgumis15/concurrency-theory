package ProdConsRand;

public class Consumer implements Runnable{

    private final Buffer buffer;
    private final int id;

    public Consumer(Buffer buffer, int id){
        this.buffer=buffer;
        this.id=id;
    }

    @Override
    public void run() {
      for (;;){
          try {

              buffer.consume();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }


      }
    }

    public int getId() {
        return id;
    }
}
