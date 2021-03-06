package es.source.code.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import es.source.code.adapter.SCOSHelperAdapter;
import es.source.code.mail.SendMailUtil;
import es.source.code.model.GridItem;
import es.source.code.service.UpdateService;
import es.source.code.utils.Global;

public class SCOSHelper extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private String[] scos_helper_name= new String[]{
            "电话人工帮助","短信帮助","邮件帮助","用户使用协议","关于系统"
    };//选项名

    private int[] scos_helper_image=new int[]{
            R.drawable.scos_help_call,R.drawable.scos_help_message,R.drawable.scos_help_mail,
            R.drawable.scos_help_protocol, R.drawable.scos_help_about
    };//图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);

        GridView mGridView;
        mGridView = findViewById(R.id.grid_view);
        SCOSHelperAdapter scosHelperAdapter=new SCOSHelperAdapter(this,getGridViewData());
        mGridView.setAdapter(scosHelperAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 获取GridView的数据
     */
    private ArrayList getGridViewData() {
        ArrayList<GridItem> list = new ArrayList<GridItem>();

        for (int i=0; i<scos_helper_name.length; i++) {
            GridItem item=new GridItem(scos_helper_image[i],scos_helper_name[i]);
            list.add(item);
        }

        return list;
    }

    // 创建Handler对象，匿名类的方式实现handleMessage方法，这里是子线程
    Handler handler = new Handler() {

        /**
         * 接收Message信息, 只要Handler对象执行了SendMessage方法， 这个方法就会触发
         */
        @Override
        public void handleMessage(Message msg) {
            // 获取Message的what数值
            int index = msg.what;
            if(index==1){
                Toast.makeText(getApplicationContext(), "求助邮件已发送成功", Toast.LENGTH_SHORT).show();
            }
        }

    };

    /**
     * GridView的点击回调函数
     *
     * @param adapter  -- GridView对应的dapterView
     * @param view     -- AdapterView中被点击的视图(它是由adapter提供的一个视图)。
     * @param position -- 视图在adapter中的位置。
     * @param rowid    -- 被点击元素的行id。
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long rowid) {
        // 根据元素位置获取对应的值
        GridItem item = new GridItem(scos_helper_image[position],scos_helper_name[position]);

//        Toast.makeText(this.getApplicationContext(), "You Select "+item.getName(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        String phoneNumber="5554";
        Uri uri=null;
        switch (position) {
            case 0:  //电话人工帮助
                uri = Uri.parse("tel:"+phoneNumber);
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case 1:  //短信帮助
                String message="test scos helper";
                Intent SENT_SMS_ACTION = new Intent("SENT_SMS_ACTION");
                PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, SENT_SMS_ACTION, 0);

                SmsManager smsManager = SmsManager.getDefault();
                List<String> divideContents = smsManager.divideMessage(message);
                for (String text : divideContents) {
                    smsManager.sendTextMessage(phoneNumber, null, text, sendIntent, null);
                }
                // register the Broadcast Receivers
                this.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context _context, Intent _intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(_context, "求助短信发送成功", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(_context, "求助短信发送失败", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SENT_SMS_ACTION"));
                break;
            case 2: //邮件帮助
                SendMailUtil.send("3506932092@qq.com");
                handler.sendEmptyMessage(1);
                break;
            case 3: //用户使用协议
                Toast.makeText(this, "此功能暂未开放，不如看看有没有什么新菜", Toast.LENGTH_SHORT).show();
                Intent intentService = new Intent(this, UpdateService.class);
                intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //避免重复打开
                this.startService(intentService);
                break;
                case 4: //关于系统
                    Toast.makeText(this, "此功能暂未开放，不如看看有没有什么新菜", Toast.LENGTH_SHORT).show();
                    int FLAG_CLEAN = 101;
                     int NOTIFICATION_ID = 5445;
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

                    Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    MediaPlayer mediaPlayer = MediaPlayer.create(this, ringtone);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }
                    });
                break;
                default:
                    break;

        }
    }
}
