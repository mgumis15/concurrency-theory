

public class MyInteger {
    private int value;

    MyInteger(int value){
        this.value = value;
    }

    public void increment(){
        value++;
    }
    public void decrement(){
        value--;
    }

    public String toString(){
        return Integer.toString(value);
    }

    public int value(){
        return this.value;
    }

}
