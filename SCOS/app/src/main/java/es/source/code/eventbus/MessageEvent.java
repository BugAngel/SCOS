package es.source.code.eventbus;

public class MessageEvent {
    private int what;
    private int foodNum;
    private String foodName;

    public MessageEvent(int what) {
        this.what = what;
    }

    public MessageEvent(int what,int foodNum,String foodName) {
        this.what = what;
        this.foodNum=foodNum;
        this.foodName=foodName;
    }

    public int getWhat() {
        return what;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public String getFoodName() {
        return foodName;
    }
}
