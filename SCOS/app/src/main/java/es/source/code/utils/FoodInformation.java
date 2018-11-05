package es.source.code.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import es.source.code.activity.R;
import es.source.code.model.FoodItem;

import static es.source.code.utils.Global.FoodSort;

public class FoodInformation {

    //初始化菜品信息   只在没菜的时候执行
    public void foodItemInit(){

        if(Global.foodInformation.size()>0){
            return;
        }

        FoodItem foodItem;

        foodItem=new FoodItem("酱牛肉",15.3,R.drawable.jiangniurou,FoodSort.COLD_FOOD,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("酸辣凤爪",20.5,R.drawable.suanlafengzhao,FoodSort.COLD_FOOD,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("老醋木耳",10.2,R.drawable.laocumuer,FoodSort.COLD_FOOD,0,0,5,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("鱼香肉丝",25,R.drawable.yuxiangrousi,FoodSort.HOT_FOOD,0,1,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("冒菜",28,R.drawable.maocai,FoodSort.HOT_FOOD,0,0,0,"多放辣");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("牛肉干锅",60,R.drawable.niurouganguo,FoodSort.HOT_FOOD,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("螃蟹",40,R.drawable.pangxie,FoodSort.SEA_FOOD,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("鲍鱼",200,R.drawable.baoyu,FoodSort.SEA_FOOD,0,0,0,"鲜");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("龙虾",300,R.drawable.longxia,FoodSort.SEA_FOOD,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("茅台",500,R.drawable.maotai,FoodSort.DRINKS,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("五粮液",400,R.drawable.wuliangye,FoodSort.DRINKS,0,0,0,"");
        Global.foodInformation.add(foodItem);

        foodItem=new FoodItem("天之蓝",300,R.drawable.tianzhilan,FoodSort.DRINKS,0,0,0,"");
        Global.foodInformation.add(foodItem);

        //排序
        foodSort();
    }

    //计算已提交订单菜品总数
    public int getSumOrderedDishes(){
        int sum=0;

        for (FoodItem foodItem : Global.foodInformation){
            sum+=foodItem.getOrderedNum();
        }
        return  sum;
    }

    //计算未提交订单菜品总数
    public int getSumUnorderedDishes(){
        int sum=0;

        for (FoodItem foodItem : Global.foodInformation){
            sum+=foodItem.getUnorderedNum();
        }
        return  sum;
    }

    //计算已提交订单菜品总价
    public double getSumOrderedPrice(){
        double sum=0.0;

        for (FoodItem foodItem : Global.foodInformation){
            sum+=foodItem.getOrderedNum()*foodItem.getPrice();
        }
        return  sum;
    }

    //计算未提交订单菜品总价
    public double getSumUnorderedPrice(){
        double sum=0.0;

        for (FoodItem foodItem : Global.foodInformation){
            sum+=foodItem.getUnorderedNum()*foodItem.getPrice();
        }
        return  sum;
    }

    //提交订单，将已提交订单菜品总数记为0
    public void submitOrders(){
        for(int i=0;i<Global.foodInformation.size();i++){
            FoodItem foodItem=Global.foodInformation.get(i);
            if(foodItem.getOrderedNum()!=0){
                foodItem.setOrderedNum(0);
                Global.foodInformation.set(i, foodItem);
            }
        }
    }

    //添加菜品
    public void addFood(FoodItem foodItem){
        if(Global.foodInformation.size()==0){
            foodItemInit();
        }
        Global.foodInformation.add(foodItem);
        foodSort();
    }

    //对总菜品进行排序
    private void foodSort(){
        Collections.sort(Global.foodInformation, new Comparator<FoodItem>() {
            @Override
            public int compare(FoodItem foodItem1, FoodItem foodItem2) {
                if (foodItem1.getSort() > foodItem2.getSort()) {
                    return 1;
                } else if (foodItem1.getSort() < foodItem2.getSort()) {
                    return -1;
                } else {
                    int i=foodItem1.getName().compareTo(foodItem2.getName());
                    return Integer.compare(i,0);
                }
            }
        });
    }
}
