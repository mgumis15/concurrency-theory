package ActiveObject;

public class Task implements Runnable{
    private final int bufferChange;
    private final int computationNumber;
    public Task(int bufferChange,int computationNumber) {
        this.bufferChange = bufferChange;
        this.computationNumber=computationNumber;
    }

    public int getBufferChange() {
        return bufferChange;
    }

    @Override
    public void run() {
//        System.out.println("Task is working");
        double x=1;
        for (int i = 0; i < computationNumber; i++) {
            x=Math.sin(i)*Math.cos(i);
        }
    }
}
