public class Main {
    public static void main(String[] args) throws InterruptedException {

        Crementer object=new Crementer();

        Thread threadIn=new Thread(()->{
            for (int i=0;i<10000;i++){
                object.increment();
            }
        });
        Thread threadDe=new Thread(()->{
            for (int i=0;i<10000;i++)
            {
                object.decrement();
            }
        });

        threadIn.start();
        threadDe.start();

        threadIn.join();
        threadDe.join();

        System.out.println("Finall: "+object.getI());

    }
}