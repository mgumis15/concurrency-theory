package PC_4_Cond;

public class Client implements Runnable{

    private ActivationQueue activationQueue;
    private final int id;
    private final int value;
    private final String type;
    private boolean running=true;
    private Future awaitingFuture;



    public Client(String type,ActivationQueue activationQueue, int id, int value){
        this.type=type;
        this.activationQueue=activationQueue;
        this.id=id;
        this.value=value;
    }

    @Override
    public void run() {
        Task task;
        System.out.println(type + " "+ id + ": starts working");
        task=new Task(value);
        awaitingFuture=activationQueue.enqueue(task);
        while(running){
            if(awaitingFuture.isReady()){
                System.out.println(type+" "+id+": get Future done");
                task=new Task(value);
                awaitingFuture=activationQueue.enqueue(task);
            }else{
                doOwnJob();
            }

        }
    }
    public void doOwnJob(){
        System.out.println(type+" "+id+": is doing own job");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running=false;
    }
}
