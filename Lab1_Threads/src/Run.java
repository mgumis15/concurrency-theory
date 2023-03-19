
import java.util.List;
import java.util.LinkedList;
import static java.lang.System.out;


public class Run{
    public static void main(String[] args) throws InterruptedException {

        MyInteger a = new MyInteger(0);

        int cycles = 1000000;


        IncrementThread increment = new IncrementThread(cycles,a);
        DecrementThread decrement = new DecrementThread(cycles,a);

        increment.start();
        decrement.start();

        increment.join();
        decrement.join();



        out.println(a);
    }
}


