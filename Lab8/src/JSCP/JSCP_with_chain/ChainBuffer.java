package JSCP.JSCP_with_chain;

import org.jcsp.lang.*;

public class ChainBuffer implements CSProcess {

    private final One2OneChannelInt resIn;
    private final One2OneChannelInt resOut;

    private int buff = 0;
    private int index;
    private int clusterIndex;
    private int operations = 0;

    public ChainBuffer(One2OneChannelInt resIn, One2OneChannelInt resOut, int index, int clusterIndex) {
        this.resIn = resIn;
        this.resOut = resOut;
        this.index = index;
        this.clusterIndex = clusterIndex;
    }

    @Override
    public void run() {
        System.out.println("Chain Buffer "+index+" from "+clusterIndex+" is working");
        while (true) {
            if (buff == 0) {
                buff += resIn.in().read();
            } else {
                resOut.out().write(1);
                buff--;
                operations++;
            }

        }

    }

    public One2OneChannelInt getResOut() {
        return resOut;
    }
}
