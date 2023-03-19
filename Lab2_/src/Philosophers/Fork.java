package Philosophers;

public class Fork {
    private boolean available=true;
    private final int id;

    public Fork(int id){
        this.id=id;
    }

    public boolean isAvailable() {
        return available;
    }

    public synchronized void takeFork() throws InterruptedException {
        while(!this.available){
            wait();
        }
        this.available = false;
        notify();
    }
    public synchronized void leaveFork() throws InterruptedException {
        while(this.available){
            wait();
        }
        this.available = true;
        notify();
    }

    public int getId() {
        return id;
    }
}
