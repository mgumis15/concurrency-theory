package JSCP.JSCP3;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private One2OneChannelInt channel;
    private int start;

    public Producer(One2OneChannelInt channel, int start) {
        this.channel = channel;
        this.start = start;
    }

    @Override
    public void run() {
        int item=1;
        for (int i = 0; i < 100; i++) {
            channel.out().write(item);
        }
        channel.out().write(-1);
        System.out.println("Producer"+start+" ended.");
    }
}
