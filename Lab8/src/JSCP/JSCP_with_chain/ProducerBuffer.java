package JSCP.JSCP_with_chain;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class ProducerBuffer implements CSProcess {
    private final One2OneChannelInt[] resIn;
    private final One2OneChannelInt[] reqIn;
    private final One2OneChannelInt resOut;
    private final One2OneChannelInt reqOut;
    private final int maxSize;
    private final int clusterIndex;
    private int buff = 0;
    private int operations = 0;

    public ProducerBuffer(One2OneChannelInt[] resIn, One2OneChannelInt[] reqIn, One2OneChannelInt resOut, One2OneChannelInt reqOut, int size,int clusterIndex) {
        this.resIn = resIn;
        this.reqIn = reqIn;
        this.resOut = resOut;
        this.reqOut = reqOut;
        this.maxSize = size;
        this.clusterIndex = clusterIndex;
    }

    @Override
    public void run() {
        System.out.println("Producer Buffer "+clusterIndex+" is working");
        final Guard[] guards = new Guard[reqIn.length + 1];
        for (int i = 0; i < reqIn.length; i++) {
            guards[i] = reqIn[i].in();
        }
        guards[reqIn.length] = reqOut.in();

        final Alternative alt = new Alternative(guards);

        while (true) {
            int selectedId = alt.fairSelect();
            if (selectedId >= 0 && selectedId < reqIn.length) {
                int producerRequest = reqIn[selectedId].in().read();
                if (producerRequest == 0) {
                    if (buff < maxSize) {
                        resIn[selectedId].out().write(1);

                        buff += reqIn[selectedId].in().read();
                    } else {
                        resIn[selectedId].out().write(-1);
                    }
                }
            } else {
                int consumerRequest = reqOut.in().read();
                if (consumerRequest == 0) {
                    if (buff > 0) {
                        resOut.out().write(1);

                        reqOut.in().read();
                        resOut.out().write(1);

                        buff--;
                        operations++;
                    } else {
                        resOut.out().write(-1);
                    }
                }
            }
        }
    }
}
