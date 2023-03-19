package JSCP.SCP1;

import org.jcsp.lang.AltingChannelInputInt;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {

    private One2OneChannelInt channel;

    public Consumer(One2OneChannelInt channel) {
        this.channel = channel;
    }

    @Override
    public void run() {

        AltingChannelInputInt item=channel.in();
        System.out.println(item.read());
    }
}
