

public class DecrementThread extends Thread{
    private int n;
    private MyInteger a;

    public DecrementThread(int n, MyInteger a){
        this.n = n;
        this.a = a;
    }

    @Override
    public void run() {
        for(int i=0;i<this.n;i++){
            a.decrement();
        }
    }
}
