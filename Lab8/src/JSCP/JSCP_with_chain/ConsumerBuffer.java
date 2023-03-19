package JSCP.JSCP_with_chain;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class ConsumerBuffer implements CSProcess {
    private final One2OneChannelInt resIn;
    private final One2OneChannelInt[] resOut;
    private final One2OneChannelInt[] reqOut;
    private final int maxSize;
    private final int clusterIndex;
    private int buff = 0;
    private int operations = 0;

    public ConsumerBuffer(One2OneChannelInt resIn, One2OneChannelInt[] resOut, One2OneChannelInt[] reqOut, int size,int clusterIndex) {
        this.resIn = resIn;

        this.resOut = resOut;
        this.reqOut = reqOut;
        this.maxSize = size;
        this.clusterIndex = clusterIndex;
    }

    @Override
    public void run() {
        System.out.println("Consumer Buffer "+clusterIndex+" is working");
        final Guard[] guards = new Guard[1 + reqOut.length];
        guards[0] = resIn.in();
        for (int i = 1; i < reqOut.length + 1; i++) {
            guards[i] = reqOut[i-1].in();
        }
        final Alternative alt = new Alternative(guards);

        while (true) {
            int selectedId = alt.fairSelect();
            if (selectedId == 0) {
                if (buff < maxSize) {
                    buff += resIn.in().read();
                }
            } else {
                int ind = selectedId - 1;
                int consumerRequest = reqOut[ind].in().read();
                if (consumerRequest == 0) {
                    if (buff > 0) {
                        resOut[ind].out().write(1);
                        reqOut[ind].in().read();
                        resOut[ind].out().write(1);
                        buff--;
                        operations++;
                    } else {
                        resOut[ind].out().write(-1);
                    }
                }
            }
        }
    }
}
