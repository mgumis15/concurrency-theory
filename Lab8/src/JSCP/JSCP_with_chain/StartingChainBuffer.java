package JSCP.JSCP_with_chain;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class StartingChainBuffer implements CSProcess {

    private final One2OneChannelInt resIn;
    private final One2OneChannelInt reqIn;
    private final One2OneChannelInt resOut;

    private int buff = 0;

    private int clusterIndex;
    private int operations = 0;

    public StartingChainBuffer(One2OneChannelInt resIn, One2OneChannelInt reqIn, One2OneChannelInt resOut, int clusterIndex) {
        this.resIn = resIn;
        this.reqIn = reqIn;
        this.resOut = resOut;
        this.clusterIndex = clusterIndex;
    }

    @Override
    public void run() {
        System.out.println("Starting Chain Buffer "+clusterIndex+" is working");
        while (true) {
            if (buff == 0) {
                reqIn.out().write(0);
                int respond = resIn.in().read();
                if (respond == 1) {
                    reqIn.out().write(1);

                    buff += resIn.in().read();
                }
            } else {
                resOut.out().write(1);
                buff--;
                operations++;
            }
        }
    }
}
