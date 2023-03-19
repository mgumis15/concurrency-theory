package JSCP.JSCP_with_chain;


import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;

public final class PCMain {
    public static void main(String[] args) {


        int producers = 10;
        int consumers = 10;
        int chainBuffPerCluster = 3;
        int p_c_bufferSize = 5;
        int clusters = 4;
        CSProcess[] procList = new CSProcess[producers + consumers + (clusters * (3+chainBuffPerCluster))];

        for (int i = 0; i < producers; i++) {

            One2OneChannelInt[] producerRes = new One2OneChannelInt[clusters];
            One2OneChannelInt[] producerReq = new One2OneChannelInt[clusters];
            for (int j = 0; j < clusters; j++) {
                producerRes[j] = Channel.one2oneInt();
                producerReq[j] = Channel.one2oneInt();
            }

            Producer producer = new Producer(producerRes, producerReq, i);
            procList[i] = producer;
        }

        for (int i = 0; i < consumers; i++) {
            One2OneChannelInt[] consumerRes = new One2OneChannelInt[clusters];
            One2OneChannelInt[] consumerReq = new One2OneChannelInt[clusters];
            for (int j = 0; j < clusters; j++) {
                consumerRes[j] = Channel.one2oneInt();
                consumerReq[j] = Channel.one2oneInt();
            }

            Consumer consumer = new Consumer(consumerRes, consumerReq, i);
            procList[producers + i] = consumer;
        }

        for (int i = 0; i < clusters; i++) {
            One2OneChannelInt[] producerRes = new One2OneChannelInt[producers];
            One2OneChannelInt[] producerReq = new One2OneChannelInt[producers];
            One2OneChannelInt[] consumerRes = new One2OneChannelInt[consumers];
            One2OneChannelInt[] consumerReq = new One2OneChannelInt[consumers];

            for (int j = 0; j < producers; j++) {
                producerRes[j] = ((Producer) procList[j]).getRes(i);
                producerReq[j] = ((Producer) procList[j]).getReq(i);
            }

            for (int j = 0; j < consumers; j++) {
                consumerRes[j] = ((Consumer) procList[producers + j]).getRes(i);
                consumerReq[j] = ((Consumer) procList[producers + j]).getReq(i);
            }

            One2OneChannelInt startingBufferRes=Channel.one2oneInt();
            One2OneChannelInt startingBufferReq=Channel.one2oneInt();
            One2OneChannelInt startingBufferOut=Channel.one2oneInt();
            int clusterId=producers + consumers + (i*(3+chainBuffPerCluster));

            procList[clusterId]=new ProducerBuffer(producerRes,producerReq,startingBufferRes,startingBufferReq,p_c_bufferSize,i);
            procList[clusterId+1]=new StartingChainBuffer(startingBufferRes,startingBufferReq,startingBufferOut,i);


            for (int j = 1; j <= chainBuffPerCluster; j++) {
                One2OneChannelInt chainRes=Channel.one2oneInt();
                int id=clusterId+1+j;
                if(j==1){
                    procList[id]=new ChainBuffer(startingBufferOut,chainRes,j,i);
                }else{
                    One2OneChannelInt previousRes=((ChainBuffer) procList[id-1]).getResOut();
                    procList[id]=new ChainBuffer(previousRes,chainRes,j,i);
                }
            }
            One2OneChannelInt previousRes=((ChainBuffer) procList[clusterId+2+chainBuffPerCluster-1]).getResOut();
            procList[clusterId+2+chainBuffPerCluster]=new ConsumerBuffer(previousRes,consumerRes,consumerReq,p_c_bufferSize,i);

        }

        Parallel par = new Parallel(procList);
        par.run();
    }
}
