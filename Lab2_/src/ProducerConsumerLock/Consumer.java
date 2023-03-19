package ProducerConsumerLock;

public class Consumer implements Runnable{

    private final Buffer buffer;
    private final int id;

    public Consumer(Buffer buffer, int id){
        this.buffer=buffer;
        this.id=id;
    }

    @Override
    public void run() {
      for (int i=0;i<1000;i++){
          try {

              buffer.consume();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }


      }
      System.out.println("Consumer finished");
    }

    public int getId() {
        return id;
    }
}
