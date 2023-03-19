package JSCP.RANDROBIN;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

import java.util.Arrays;

public class Buffer implements CSProcess {
    private final One2OneChannelInt[] resIn;
    private final One2OneChannelInt[] reqIn;
    private final One2OneChannelInt[] resOut;
    private final One2OneChannelInt[] reqOut;
    private final int maxSize;
    private final int index;
    private int buff = 0;
    private int operations = 0;

    public Buffer(One2OneChannelInt[] resIn, One2OneChannelInt[] reqIn, One2OneChannelInt[] resOut, One2OneChannelInt[] reqOut, int size, int index) {
        this.resIn = resIn;
        this.reqIn = reqIn;
        this.resOut = resOut;
        this.reqOut = reqOut;
        this.maxSize = size;
        this.index = index;
    }

    @Override
    public void run() {

        final Guard[] guards = new Guard[reqIn.length + reqOut.length];
        for (int i = 0; i < reqIn.length; i++) {
            guards[i] = reqIn[i].in();
        }
        for (int i = 0; i < reqOut.length; i++) {
            guards[reqIn.length + i] = reqOut[i].in();
        }

        final Alternative alt = new Alternative(guards);

        while (true) {
            int selectedId = alt.fairSelect();
            if (selectedId >= 0 && selectedId < reqIn.length) {
                int producerRequest = reqIn[selectedId].in().read();
                if(producerRequest==0){
                    if (buff < maxSize) {
                        resIn[selectedId].out().write(1);

                        buff+=reqIn[selectedId].in().read();
                    }else{
                        resIn[selectedId].out().write(-1);
                    }
                }
            }
            else{
                int ind=selectedId- reqIn.length;
                int consumerRequest = reqOut[ind].in().read();
                if(consumerRequest==0){
                    if (buff > 0) {
                        resOut[ind].out().write(1);

                        reqOut[ind].in().read();
                        resOut[ind].out().write(1);
                        buff--;
                        operations++;
                    }else{
                        resOut[ind].out().write(-1);
                    }
                }
            }
        }
    }


}
