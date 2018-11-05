package es.source.code.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.source.code.activity.FoodView;
import es.source.code.eventbus.MessageEvent;
import es.source.code.utils.AliveUtil;
import es.source.code.utils.Global;

public class ServerObserverService extends Service {
    private volatile boolean runFlag = false;
//    private CMessageHandler cMessageHandler = new CMessageHandler();
////    private Messenger Cmessenger = new Messenger(cMessageHandler);
////    private Messenger mActivityMessenger ;
    ReceiveFoodStoreThread receiveFoodStoreThread=new ReceiveFoodStoreThread();
    private AliveUtil aliveUtil=new AliveUtil();

    @Override
    public IBinder onBind(Intent intent) {
//        IBinder binder = Cmessenger.getBinder();
//        return binder;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册订阅者
        EventBus.getDefault().register(this);
        receiveFoodStoreThread.start();
        Log.d(Global.TAG.EVENT_BUS_TAG,"服务成功创建");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    class ReceiveFoodStoreThread extends Thread {

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                if (runFlag) {
//                    Log.d(Global.TAG.EVENT_BUS_TAG,"进入线程");
//                    if (aliveUtil.isProcessRunning(getApplicationContext(), "es.source.code.activity") && (mActivityMessenger != null)) {
                    if (aliveUtil.isProcessRunning(getApplicationContext(), "es.source.code") ) {
//                        try {
//                            int foodNum = 10;
//                            String foodName = "鱼香肉丝";
//                            Bundle b = new Bundle();
//                            b.putInt("num", foodNum);
//                            b.putString("name", foodName);
//
//                            Message message = new Message();
//                            message.what = 10;
//                            message.setData(b);
//                            mActivityMessenger.send(message);
//                            sleep(300);//休眠300ms
//                        } catch (InterruptedException|RemoteException e) {
//                            e.printStackTrace();
//                        }
                        int what=10;
                        int foodNum = 10;
                        String foodName = "鱼香肉丝";
                        EventBus.getDefault().post(new MessageEvent(what,foodNum,foodName));
                        try {
                            sleep(300);//休眠300ms
                        }catch (InterruptedException e) {
                           e.printStackTrace();
                        }
                        Log.d(Global.TAG.EVENT_BUS_TAG, "Service消息发送成功" );
                    }
                }
            }
        }
    }

//    class CMessageHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    runFlag=true;
//                    Log.d(Global.TAG.EVENT_BUS_TAG, "启动线程");
//                    break;
//                case 0:
//                    runFlag=false;
//                    break;
//                case 88:
//                    mActivityMessenger = msg.replyTo;
//                    Log.d(Global.TAG.EVENT_BUS_TAG, "已经获取到 Activity 发送的消息");
//                    break;
//                default:
//                    break;
//            }
//        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN , sticky = true)
    public void onMessageEvent(MessageEvent event) {
        Log.d(Global.TAG.EVENT_BUS_TAG, "Service收到消息：" + event.getWhat());
        switch(event.getWhat()){
            case 1:
                runFlag = true;
                break;
            case 0:
                runFlag = false;
                break;
        }
        // 移除粘性事件
//        EventBus.getDefault().removeStickyEvent(event);
    }
}
