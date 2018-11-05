package es.source.code.utils;

import java.util.ArrayList;
import java.util.HashMap;

import es.source.code.model.FoodItem;

public class Global {
    //菜品列表
    public static ArrayList<FoodItem> foodInformation=new ArrayList<FoodItem>();

    //菜品种类
    public static class FoodSort {
        public static final int COLD_FOOD = 0;
        public static final int HOT_FOOD = 1;
        public static final int SEA_FOOD = 2;
        public static final int DRINKS = 3;
    }

    //订单界面当前显示的fragment
    public static int FOOD_ORDER_CURRENT_ITEM=0;
    public static int FOOD_DETAIL_CURRENT_ITEM=0;

    public static class TAG {
        public static final String INTENT_SERVICE_TAG = "IntentService";
        public static final String EVENT_BUS_TAG = "EventBus";
    }
}
