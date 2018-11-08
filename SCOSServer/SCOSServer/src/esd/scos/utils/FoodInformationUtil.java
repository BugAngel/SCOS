package esd.scos.utils;

import java.util.ArrayList;

import esd.scos.model.FoodItem;

public class FoodInformationUtil {
	public static ArrayList<FoodItem> getFoodArrayList(int amount){
		ArrayList<FoodItem> foodArrayList=new ArrayList<FoodItem>();
		for(int i=0;i<amount;i++) {
			foodArrayList.add(new FoodItem("老醋木耳",10.2,0,100));//名称 价格 种类 库存
		}
		return foodArrayList;
	}
}
