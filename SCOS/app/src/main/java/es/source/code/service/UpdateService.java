package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.model.FoodItem;
import es.source.code.utils.FoodInformation;
import es.source.code.utils.Global;

public class UpdateService extends IntentService {
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            //添加菜品
            FoodItem foodItem = new FoodItem("龙江家园", 50, R.drawable.longjiangjiayuan, Global.FoodSort.DRINKS, 10, 0, 0, "");
            FoodInformation foodInformation = new FoodInformation();
            foodInformation.addFood(foodItem);
            for (int i = 0; i < Global.foodInformation.size(); i++) {
                if (Global.foodInformation.get(i).getName().equals(foodItem.getName())) {
                    Global.FOOD_DETAIL_CURRENT_ITEM = i;
                    break;
                }
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), PUSH_CHANNEL_ID);
            Intent notificationIntent = new Intent(getApplicationContext(), FoodDetailed.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
            builder.setContentTitle("新品上架")//设置通知栏标题
                    .setContentIntent(pendingIntent) //设置通知栏点击意图
                    .setContentText("新品上架：龙江家园，50元, 酒水")
                    .setTicker("新品上架") //通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setSmallIcon(R.drawable.icon)//设置通知小ICON
                    .setDefaults(Notification.DEFAULT_ALL);
            Log.d(Global.TAG.INTENT_SERVICE_TAG, "已设置通知");
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            if (notificationManager != null) {
                notificationManager.notify(PUSH_NOTIFICATION_ID, notification);
            }
        }

    }
}
