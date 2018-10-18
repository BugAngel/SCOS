package es.source.code.model;

import es.source.code.activity.R;

public class DishesInformation {
    //定义自身对象，单例模式可保持所有的OrderedDishes都是同一个对象
    private static DishesInformation dishesInformation;

    private final int DISH_SORT=4;//四个种类菜品

    private final int DISH_MAX_NUM=3;//每个种类菜品最多的菜品数

    private int detail_position=0;//记录进入菜品详情界面时要显示哪个菜

    private int order_position=0;//记录进入查看订单时的默认显示

    private String [][] dishes_name={
            {"酱牛肉","酸辣凤爪","老醋木耳"},
            {"鱼香肉丝", "冒菜", "牛肉干锅"},
            {"螃蟹", "鲍鱼", "龙虾"},
            {"茅台", "五粮液", "天之蓝"}
    };

    private int[][] dishes_img={
            {R.drawable.jiangniurou,R.drawable.suanlafengzhao,R.drawable.laocumuer},
            {R.drawable.yuxiangrousi,R.drawable.maocai,R.drawable.niurouganguo},
            {R.drawable.pangxie,R.drawable.baoyu,R.drawable.longxia},
            {R.drawable.maotai,R.drawable.wuliangye,R.drawable.tianzhilan}
    };

    private double[][] dishes_price={{15.39,20.59,10.29},{25,28,60},{250,500,999},{588,909,480}};
    private int[][] ordered_dishes_num = {{0,0,0},{25,0,0},{0,0,0},{0,0,0}};//已下单菜
    private int[][] unordered_dishes_num = {{0,0,0},{0,0,0},{0,5,0},{0,0,0}};//未下单菜
    private String[][] ordered_dishes_note={{"","",""},{"少放辣","",""},{"","",""},{"","",""}};//已下单菜备注
    private String[][] unordered_dishes_note={{"","",""},{"","",""},{"","要鲜",""},{"","",""}};//未下单菜备注


    public static DishesInformation getInstance(){ //获取本类对象的方法
        if(dishesInformation==null){ //如果dishesInformation是null
            dishesInformation=new DishesInformation();//则创建新对象
        }
        return dishesInformation;
    }

    private DishesInformation(){
        dishesInformation=this;
    }

    public void orderDishes(){
        for(int i=0;i<DISH_SORT;i++){
            for(int j=0;j<DISH_MAX_NUM;j++){
                ordered_dishes_num[i][j]=0;
            }
        }
    }
    public void setOrder_position(int order_position) {
        this.order_position = order_position;
    }

    public int getOrder_position() {
        return order_position;
    }

    public int[] getDishesImgTo1D(){
        int[] int1d;
        int len = 0;
        for (int[] element : dishes_img) {
            len += element.length;
        }
        int1d = new int[len];
        int index = 0;
        for (int[] array : dishes_img) {
            for (int element : array) {
                int1d[index++] = element;
            }
        }
        return int1d;
    }

    /**
     * 二维数组转一维数组
     * @return 菜名的一维数组
     */
    public String[] getDishNameTo1D(){
        String[] string1d;
        int len = 0;
        for (String[] element : dishes_name) {
            len += element.length;
        }
        string1d = new String[len];
        int index = 0;
        for (String[] array : dishes_name) {
            for (String element : array) {
                string1d[index++] = element;
            }
        }
        return string1d;
    }

    public double[] getDishPriceTo1D(){
        double[] double1d;
        int len = 0;
        for (double[] element : dishes_price) {
            len += element.length;
        }
        double1d = new double[len];
        int index = 0;
        for (double[] array : dishes_price) {
            for (double element : array) {
                double1d[index++] = element;
            }
        }
        return double1d;
    }

    public int[] getUnOrderedDishesNumTo1D(){
        int[] int1d;
        int len = 0;
        for (int[] element : unordered_dishes_num) {
            len += element.length;
        }
        int1d = new int[len];
        int index = 0;
        for (int[] array : unordered_dishes_num) {
            for (int element : array) {
                int1d[index++] = element;
            }
        }
        return int1d;
    }

    public void setDetail_position(int detail_position) {
        this.detail_position = detail_position;
    }

    public int getDetail_position() {
        return detail_position;
    }

    public void setOrdered_dishes_num(int value,int x,int y){
        ordered_dishes_num[x][y]+=value;
    }

    public void setUnordered_dishes_num(int value,int x,int y){
        unordered_dishes_num[x][y]+=value;
    }

    public int getDISH_SORT() {
        return DISH_SORT;
    }

    public int getDISH_MAX_NUM() {
        return DISH_MAX_NUM;
    }

    public String[][] getDishes_name() {
        return dishes_name;
    }

    public double[][] getDishes_price() {
        return dishes_price;
    }

    public int[][] getOrdered_dishes() {
        return ordered_dishes_num;
    }

    public int[][] getUnordered_dishes() {
        return unordered_dishes_num;
    }

    public double getOrderedSumPrice(){
        double sum=0.0;
        for(int i=0;i<DISH_SORT;i++){
            for(int j=0;j<DISH_MAX_NUM;j++){
                sum+=ordered_dishes_num[i][j]*dishes_price[i][j];
            }
        }
        return sum;
    }

    public double getUnorderedSumPrice(){
        double sum=0.0;
        for(int i=0;i<DISH_SORT;i++){
            for(int j=0;j<DISH_MAX_NUM;j++){
                sum+=unordered_dishes_num[i][j]*dishes_price[i][j];
            }
        }
        return sum;
    }

    public int getOrderedSumDishes(){
        int sum=0;
        for(int i=0;i<DISH_SORT;i++){
            for(int j=0;j<DISH_MAX_NUM;j++){
                sum+=ordered_dishes_num[i][j];
            }
        }
        return sum;
    }

    public int getUnorderedSumDishes(){
        int sum=0;
        for(int i=0;i<DISH_SORT;i++){
            for(int j=0;j<DISH_MAX_NUM;j++){
                sum+=unordered_dishes_num[i][j];
            }
        }
        return sum;
    }

    public String getOrdered_dish_note(int x,int y) {
        return ordered_dishes_note[x][y];
    }

    public String getUnordered_dish_note(int x,int y) {
        return unordered_dishes_note[x][y];
    }

    public int getOrderedNum(int x,int y){
        return ordered_dishes_num[x][y];
    }

    public int getUnorderedNum(int x,int y){
        return unordered_dishes_num[x][y];
    }
}
