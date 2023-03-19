package PC_4_Cond;

public class Task implements Runnable{
    private int bufferChange;

    public Task(int bufferChange) {
        this.bufferChange = bufferChange;
    }

    public int getBufferChange() {
        return bufferChange;
    }

    @Override
    public void run() {
        System.out.println("Task is working");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
