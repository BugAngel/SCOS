package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.model.FoodItem;
import es.source.code.model.ResultJson;
import es.source.code.model.ResultXml;
import es.source.code.utils.FoodInformation;
import es.source.code.utils.Global;
import es.source.code.utils.URLUtil;

public class UpdateService extends IntentService {
    private static final int JSON_TYPE=1;
    private static final int XML_TYPE=2;

    // 删除通知flag
    private static final int FLAG_CLEAN = 101;
    private static final int NOTIFICATION_ID = 5445;
    //设置流格式
    private static int contectType=JSON_TYPE;
    private static boolean ready=false;

    private Context mContext;

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mContext=this;
            HttpUpdateOperate httpUpdateOperate=new HttpUpdateOperate();
            httpUpdateOperate.start();

            //等待网络操作完成
            while (!ready){};
            ready=false;

            // 发送状态栏通知
            sendServerNotification();
            // 播放提示音
            playNotification();

        }
    }

    private void sendServerNotification() {
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

        Notification notification = new Notification();
        notification.icon = R.mipmap.ic_launcher;
        notification.tickerText = "新菜到了";
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();

        Intent p_intent = new Intent(this, MainScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, p_intent, PendingIntent
                .FLAG_CANCEL_CURRENT);

        Intent serviceIntent = new Intent(Global.CLOSE_NOTIFICATION);
        serviceIntent.putExtra(Global.NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent closeIntent = PendingIntent.getBroadcast(this, FLAG_CLEAN, serviceIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tv,getString(R.string.notification, Global.URL_FOOD_AMOUNT));
        remoteViews.setOnClickPendingIntent(R.id.btn,closeIntent);
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }

    private void playNotification() {
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext, ringtone);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void sendNotification(){
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

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        Intent notificationIntent = new Intent(mContext, FoodDetailed.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
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
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    class HttpUpdateOperate extends Thread{
        public void run(){
            try {
                InputStream resultStream=null;

                URL url = new URL(Global.URL.BASE_URL+Global.URL.Update_URL);
//                URL url = new URL("http://www.baidu.com");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                //设置连接超时时间
                connection.setConnectTimeout(5000);
                //设置从主机读取数据超时时间
                connection.setReadTimeout(5000);
                //POST请求设置允许输出
                connection.setDoOutput(true);
                //REQUEST请求设置允许输出
                connection.setDoInput(true);
//                使用缓存
                connection.setUseCaches(true);
                //设置为GET请求
                connection.setRequestMethod("GET");
                //设置本次连接是否自动处理重定向
                connection.setInstanceFollowRedirects(true);
                //配置请求Content-Type
                if(contectType==JSON_TYPE) {
                    connection.setRequestProperty("Content-Type", "application/json");
                }else{
                    connection.setRequestProperty("Content-Type", "text/xml");
                }
                //设置客户端与服务器连接类型
//                connection.addRequestProperty("Connection","Keep-Alive");
                Log.d(Global.TAG.SIX_TAG, String.valueOf(connection.getResponseCode()));
                // 判断请求是否成功
                if (connection.getResponseCode() == 200) {
                    Log.d(Global.TAG.SIX_TAG, "请求成功");
                    resultStream = connection.getInputStream();//得到输入流
                    if (contectType == JSON_TYPE) {
                        String resultString = URLUtil.streamToString(resultStream);
                        // json
//                        Log.d(Global.TAG.SIX_TAG, "request = " + resultString);
                        ResultJson resultJson = new Gson().fromJson(resultString, ResultJson.class);
                        String foodString = resultJson.getDataString();
                        Type type = new TypeToken<ArrayList<FoodItem>>() {
                        }.getType();

                        // 解析列表统计时间
                        Date startDate = new Date(System.currentTimeMillis());
                        ArrayList<FoodItem> foodList = new Gson().fromJson(foodString, type);
                        Date endDate = new Date(System.currentTimeMillis());
                        long duration = endDate.getTime() - startDate.getTime();
                        Global.URL_FOOD_AMOUNT = foodList.size();
                        Log.d(Global.TAG.JSON_XML_TAG, "菜品数量为"+String.valueOf(Global.URL_FOOD_AMOUNT)+"时，解析JSON时间"+String.valueOf(duration)+"ms");
                    } else if (contectType == XML_TYPE) {
                        try {
                            URLUtil.resultFromStreamTOString(resultStream);
                        } catch (Exception e) {
                            Log.d(Global.TAG.SIX_TAG, "解析XML出错");
                        }
                    }
                } else {
                    Log.d(Global.TAG.SIX_TAG, "请求失败");
                }
                // 关闭连接
                connection.disconnect();

            }catch (IOException e){
                Log.d(Global.TAG.SIX_TAG,"GET请求失败");
                Toast.makeText(getApplicationContext(), "网络异常，无法登录", Toast.LENGTH_SHORT).show();
            }
            ready=true;
        }
    }

}
