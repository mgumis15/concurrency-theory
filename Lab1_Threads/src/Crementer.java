public class Crementer {
    private int i = 0;

    public Crementer() {
        this.i = 0;
    }

    public void add(int value){
        this.i+=value;
    }
    public void increment() {
        synchronized (this){
            this.i += 1;
        }

    }

    public void decrement() {
        synchronized (this){
            this.i -= 1;
        }
    }

    public int getI() {
        return i;
    }
}