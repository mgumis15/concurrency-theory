package JSCP.SCP2;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt req;
    private final One2OneChannelInt in;


    public Consumer(One2OneChannelInt req, One2OneChannelInt in) {
        this.req = req;
        this.in = in;

    }

    @Override
    public void run() {


        int item;
        while (true){
            req.out().write(0);
            item=in.in().read();
            if(item<0)
                break;
            System.out.println(item);
        }
        System.out.println("Consumer ended.");
    }
}
