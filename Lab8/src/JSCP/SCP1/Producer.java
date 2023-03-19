package JSCP.SCP1;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt channel;

    public Producer(One2OneChannelInt out) {
        this.channel = out;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int item=1;
        channel.out().write(item);
        System.out.println("XD");
    }
}