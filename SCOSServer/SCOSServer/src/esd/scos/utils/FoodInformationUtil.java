package esd.scos.utils;

import java.util.ArrayList;

import esd.scos.model.FoodItem;

public class FoodInformationUtil {
	public static ArrayList<FoodItem> getFoodArrayList(int amount){
		ArrayList<FoodItem> foodArrayList=new ArrayList<FoodItem>();
		for(int i=0;i<amount;i++) {
			foodArrayList.add(new FoodItem("�ϴ�ľ��",10.2,0,100));//���� �۸� ���� ���
		}
		return foodArrayList;
	}
}
