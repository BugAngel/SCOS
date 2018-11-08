package es.source.code.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import es.source.code.eventbus.MessageEvent;
import es.source.code.fragment.FoodViewFragment;
import es.source.code.model.FoodItem;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;
import es.source.code.utils.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FoodView extends AppCompatActivity {
    private String[] mTitle = {"冷菜","热菜","海鲜","酒水"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    User user=null;
    private ServiceConnection conn;
    private Messenger messenger;
//    将该 Handler 发送 Service
    private Messenger mOutMessenger = new Messenger(new OutgoingHandler());

    Menu menu;
    FragmentStatePagerAdapter fragmentPagerAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        initView();

        // 注册订阅者
//        EventBus.getDefault().register(this);

        user = (User)getIntent().getSerializableExtra("user");
        initBinder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentPagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()){
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
//                创建Fragment并返回
                FoodViewFragment fragment = new FoodViewFragment();
                fragment.setPosition(position % mTitle.length);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public int getItemPosition(Object object){
                return PagerAdapter.POSITION_NONE;
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);
        //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(conn!=null){
            unbindService(conn);
        }
        // 注销订阅者
//        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tl_tab);
        mViewPager = findViewById(R.id.vp_pager);

        fragmentPagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()){
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
//                创建Fragment并返回
                FoodViewFragment fragment = new FoodViewFragment();
                fragment.setPosition(position % mTitle.length);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public int getItemPosition(Object object){
                return PagerAdapter.POSITION_NONE;
            }
        };
        mViewPager.setAdapter(fragmentPagerAdapter);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);

        //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.foodview_actionbar, menu);
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=null;
        switch (item.getItemId()) {
            case R.id.action_bar_ordered_dish:
                Global.FOOD_ORDER_CURRENT_ITEM=1;
                intent=new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("user",user);
                startActivity(intent);
                return true;
            case R.id.action_bar_check_order:
                Global.FOOD_ORDER_CURRENT_ITEM=0;
                intent=new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("user",user);
                startActivity(intent);
                return true;
            case R.id.action_bar_call_service:
                intent=new Intent(FoodView.this, SCOSHelper.class);
                intent.putExtra("user",user);
                startActivity(intent);
                return true;
            case R.id.action_bar_real_update:
                handleRefreshClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //绑定服务
    private void initBinder() {
        Intent intent = new Intent(this, ServerObserverService.class);
        startService(intent);//启动服务
        conn = new MyServiceConnection();
        bindService(intent, conn, BIND_AUTO_CREATE);//绑定服务
        Log.d(Global.TAG.EVENT_BUS_TAG,"服务建立");
    }

    private void handleRefreshClick() {
        if (messenger == null) {
            Log.d(Global.TAG.INTENT_SERVICE_TAG, "服务不可用");
            return;
        }

        MenuItem item = menu.getItem(0);
        String title = item.getTitle().toString();
        int what;
        if (getString(R.string.start_real_update).equals(title)) {
            title = getString(R.string.stop_real_update);
            what = 1;
        } else {
            title = getString(R.string.start_real_update);
            what = 0;
        }

        //发送消息
        Message message = new Message();
        message.what =what;
        try{
            messenger.send(message);
        }catch (RemoteException e) {
            e.printStackTrace();
        }
//        EventBus.getDefault().post(new MessageEvent(what));
        Log.d(Global.TAG.INTENT_SERVICE_TAG, "实时更新消息发送成功" );
        item.setTitle(title);
    }

    class OutgoingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Log.d(Global.TAG.INTENT_SERVICE_TAG, "已经获取到 Service 发送的消息");
            switch (msg.what) {
                case 10:
                    Bundle bundle = msg.getData();
                    String foodName = bundle.getString("name"); //菜名称
                    int foodNum = bundle.getInt("num");  //库存量
                    for(int i = 0;i < Global.foodInformation.size(); i ++){
                        FoodItem foodItem=Global.foodInformation.get(i);
                        if(foodItem.getName().equals(foodName)){
                            foodItem.setStore(foodNum);  //更新库存信息
                            Global.foodInformation.set(i,foodItem);
                            break;
                        }
                    }
                    fragmentPagerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(Global.TAG.INTENT_SERVICE_TAG,"连接成功");
            messenger = new Messenger(service);
            Message message=Message.obtain();
            message.replyTo = mOutMessenger;
            message.what = 88;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Log.d(TAG,"连接已经断开!");
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
//        Log.d(Global.TAG.EVENT_BUS_TAG, "activity收到消息：" + event.getWhat());
//        if(event.getWhat()==10){
//            String foodName = event.getFoodName(); //菜名称
//            int foodNum = event.getFoodNum();  //库存量
//            for (int i = 0; i < Global.foodInformation.size(); i++) {
//                FoodItem foodItem = Global.foodInformation.get(i);
//                if (foodItem.getName().equals(foodName)) {
//                    foodItem.setStore(foodNum);  //更新库存信息
//                    Global.foodInformation.set(i, foodItem);
//                    break;
//                }
//            }
//        }
//    }
}
