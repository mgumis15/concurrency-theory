package ActiveObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Client implements Runnable{

    private final ActivationQueue activationQueue;
    private final int id;
    private final int maxValue;
    private final String type;
    private boolean running=true;
    private final int flag;
    private int buffTaskCounter=0;
    private int ownTaskCounter=0;
    private final int ownComputationNumber;
    private final int schedulerComputationNumber;



    public Client(String type,ActivationQueue activationQueue, int id, int maxValue,int ownComputationNumber,int schedulerComputationNumber, int flag){
        this.type=type;
        this.activationQueue=activationQueue;
        this.id=id;
        this.maxValue=maxValue;
        this.flag=flag;
        this.ownComputationNumber=ownComputationNumber;
        this.schedulerComputationNumber=schedulerComputationNumber;
    }

    @Override
    public void run() {
        Random rand=new Random(48);
        Task task;
        int newInt=rand.nextInt(maxValue+1);
        // System.out.println(type + " "+ id + ": starts working");
        task=new Task(newInt*flag,schedulerComputationNumber);
        Future awaitingFuture = activationQueue.enqueue(task);

        while(running){
            if(awaitingFuture.isReady()){
                buffTaskCounter++;
//                 System.out.println(type+" "+id+": get Future done");
                newInt=rand.nextInt(maxValue+1);
//                 System.out.println(newInt);
                task=new Task(newInt*flag,schedulerComputationNumber);
                awaitingFuture =activationQueue.enqueue(task);
            }else{
                doOwnJob();
            }
        }
    }
    public void doOwnJob(){
//         System.out.println(type+" "+id+": is doing own job");

        double x=1;
        for (int i = 0; i < ownComputationNumber; i++) {
            x+=Math.sin(i)*Math.cos(i)/Math.cos(i)+2;
        }
        ownTaskCounter++;
    }

    public ArrayList<Integer> stopAndReturnCounter(){
        running=false;
        return  new ArrayList<>(Arrays.asList(buffTaskCounter,ownTaskCounter));
    }
}
