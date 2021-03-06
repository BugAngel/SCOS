package es.source.code.model;

public class FoodItem {
    private String name;//菜名
    private double price;//价格
    private int image;//图片
    private int sort;//菜品种类 0 冷菜 1 热菜 2 海鲜 3 酒水
    private int store=0;//库存数量
    private int ordered_num=0;//已点数量
    private int unordered_num=0;//未点数量
    private String note=null;//备注

    public FoodItem(){

    }

	public FoodItem(String name, double price, int image, int sort, int store) {
		this.name = name;
		this.price = price;
		this.image = image;
		this.sort = sort;
		this.store = store;
	}
	
    public FoodItem(String name, double price, int image, int sort, int store, int ordered_num, int unordered_num, String note) {
        this.name = name;
        this.price = price;
        this.image=image;
        this.sort=sort;
        this.store=store;
        this.ordered_num = ordered_num;
        this.unordered_num = unordered_num;
        this.note = note;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public void setOrderedNum(int ordered_num) {
        this.ordered_num = ordered_num;
    }

    public void setUnorderedNum(int unordered_num) {
        this.unordered_num = unordered_num;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public int getSort() {
        return sort;
    }

    public int getStore() {
        return store;
    }

    public int getOrderedNum() {
        return ordered_num;
    }

    public int getUnorderedNum() {
        return unordered_num;
    }

    public String getNote() {
        return note;
    }

}
