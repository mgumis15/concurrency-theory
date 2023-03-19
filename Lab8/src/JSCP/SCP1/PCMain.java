package JSCP.SCP1;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public class PCMain {
    public static void main(String[] args){
        new PCMain();
    }

    public PCMain(){
        final One2OneChannelInt channel=Channel.one2oneInt();
        CSProcess[] procList={new Producer(channel),new Consumer(channel)};
        Parallel parallel=new Parallel(procList);
        parallel.run();
    }
}
