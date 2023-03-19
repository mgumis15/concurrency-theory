package PC_4_Cond;

public class Future {
    private boolean ready=false;
    private Task function;

    public Future(Task function) {
        this.function = function;
    }

    public boolean isReady() {
        return ready;
    }

    public void makeReady() {
        ready = true;
    }

    public Task getFunction() {
        return function;
    }
}
