package es.source.code.utils;

import java.util.ArrayList;
import java.util.HashMap;

import es.source.code.model.FoodItem;

public class Global {
    //菜品列表
    public static ArrayList<FoodItem> foodInformation=new ArrayList<FoodItem>();
    //订单界面当前显示的fragment
    public static int FOOD_ORDER_CURRENT_ITEM=0;
    public static int FOOD_DETAIL_CURRENT_ITEM=0;
    public static int URL_FOOD_AMOUNT=0;

    public static final String NOTIFICATION_ID = "notification_id";

    public static final String CLOSE_NOTIFICATION = "scos.intent.action.CLOSE_NOTIFICATION";
    //菜品种类
    public static class FoodSort {
        public static final int COLD_FOOD = 0;
        public static final int HOT_FOOD = 1;
        public static final int SEA_FOOD = 2;
        public static final int DRINKS = 3;
    }

    public static class TAG {
        public static final String INTENT_SERVICE_TAG = "IntentService";
        public static final String EVENT_BUS_TAG = "EventBus";
        public static final String SIX_TAG = "作业六";
        public static final String JSON_XML_TAG = "JSON_XML";
    }

    public static class URL {
        public static final String BASE_URL = "http://192.168.191.1:8080/SCOSServer/";
        public static final String Login_URL = "Login";
        public static final String Update_URL = "Update";
    }

    public static class ELEMENT_ID{
        public static final String FOOD = "food";
        public static final String ROOT = "root";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String SORT = "sort";
        public static final String STORE = "store";
    }
}
