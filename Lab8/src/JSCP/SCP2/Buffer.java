package JSCP.SCP2;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

import java.util.Arrays;

public class Buffer implements CSProcess {
    private final One2OneChannelInt[] in;
    private final One2OneChannelInt[] req;
    private final One2OneChannelInt[] out;
    private final int[] buffer=new int[10];
    int hd=-1;
    int tl=-1;

    public Buffer(One2OneChannelInt[] in, One2OneChannelInt[] req, One2OneChannelInt[] out) {
        this.in = in;
        this.req = req;
        this.out = out;
    }

    @Override
    public void run() {

        final Guard[] guards={in[0].in(),in[1].in(),req[0].in(),req[1].in()};
        final Alternative alt=new Alternative(guards);
        int countdown=4;
        while(countdown>0){
            int index=alt.select();
            switch (index){
                case 0:
                case 1:

                    if(hd<tl+11){
                        int item=in[index].in().read();

                        if(item<0)
                            countdown--;
                        else{
                            hd++;
                            buffer[hd%buffer.length]=item;

                        }
                    }
                    break;
                case 2:
                case 3:

                    if(tl<hd){
                        req[index-2].in().read();
                        tl++;
                        int item=buffer[tl% buffer.length];
                        out[index-2].out().write(item);

                    }
                    else if(countdown<=2){
                        req[index-2].in().read();
                        out[index-2].out().write(-1);
                        countdown--;
                    }
                    break;
            }
        }
        System.out.println("Buffer ended");
    }
}
