package JSCP.RANDROBIN;


import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public final class PCMain {
    public static void main(String[] args){

        int producers=10;
        int consumers=10;
        int buffers=4;
        int bufferSize=5;
        CSProcess[] procList=new CSProcess[producers+consumers+buffers];

        for (int i = 0; i < producers; i++) {

            One2OneChannelInt[] producerData=new One2OneChannelInt[buffers];
            One2OneChannelInt[] producerReq=new One2OneChannelInt[buffers];
            for (int j = 0; j <buffers ; j++) {
                producerData[j]=Channel.one2oneInt();
                producerReq[j]=Channel.one2oneInt();
            }

            Producer producer=new Producer(producerData,producerReq,i);
            procList[i]=producer;
        }

        for (int i = 0; i < consumers; i++) {
            One2OneChannelInt[] consumerData=new One2OneChannelInt[buffers];
            One2OneChannelInt[] consumerReq=new One2OneChannelInt[buffers];
            for (int j = 0; j <buffers ; j++) {
                consumerData[j]=Channel.one2oneInt();
                consumerReq[j]=Channel.one2oneInt();
            }

            Consumer consumer=new Consumer(consumerData,consumerReq,i);
            procList[producers+i]=consumer;
        }
        for (int i = 0; i < buffers; i++) {
            One2OneChannelInt[] producerRes=new One2OneChannelInt[producers];
            One2OneChannelInt[] producerReq=new One2OneChannelInt[producers];
            One2OneChannelInt[] consumerRes=new One2OneChannelInt[consumers];
            One2OneChannelInt[] consumerReq=new One2OneChannelInt[consumers];
            for (int j = 0; j < producers; j++) {
                producerRes[j] = ((Producer) procList[j]).getRes(i);
                producerReq[j] = ((Producer) procList[j]).getReq(i);
            }
            for (int j = 0; j < consumers; j++) {
                consumerRes[j]=((Consumer) procList[producers+j]).getRes(i);
                consumerReq[j]=((Consumer) procList[producers+j]).getReq(i);
            }
            procList[producers+consumers+i]=new Buffer(producerRes,producerReq,consumerRes,consumerReq,bufferSize,i);
        }

        Parallel par=new Parallel(procList);
        par.run();

    }
}
