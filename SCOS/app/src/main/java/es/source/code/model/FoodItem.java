package es.source.code.model;

public class FoodItem {
    private String name;
    private double price;
    private int ordered_num=0;//已点数量
    private int unordered_num=0;//未点数量
    private String ordered_note=null;//已点备注
    private String unordered_note=null;//未点备注

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public FoodItem(String name, double price,int ordered_num,int unordered_num,String note,String unordered_note) {
        this.name = name;
        this.price = price;
        this.ordered_num = ordered_num;
        this.unordered_num = unordered_num;
        this.ordered_note = note;
        this.unordered_note = unordered_note;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setOrderedNum(int ordered_num) {
        this.ordered_num = ordered_num;
    }

    public void setUnorderedNum(int unordered_num) {
        this.unordered_num = unordered_num;
    }

    public void setOrderedNote(String note) {
        this.ordered_note = note;
    }

    public void setUnorderedNote(String note) {
        this.unordered_note = note;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getOrderedNum() {
        return ordered_num;
    }

    public int getUnorderedNum() {
        return unordered_num;
    }

    public String getOrderedNote() {
        return ordered_note;
    }

    public String getUnorderedNote() {
        return unordered_note;
    }
}
