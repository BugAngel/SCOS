package es.source.code.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.model.FoodItem;
import es.source.code.model.User;
import es.source.code.utils.FoodInformation;
import es.source.code.utils.Global;

public class FoodOrderView extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView dishes_num;
    private TextView dishes_sum_price;
    private Button dishes_btn;
    private ProgressDialog progressDialog=null;
    User user=null;
    private String[] mTitle = {"已下单菜","未下单菜"};
    FoodInformation foodInformation=new FoodInformation();
    FragmentStatePagerAdapter fragmentStatePagerAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        user = (User)getIntent().getSerializableExtra("user");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentStatePagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
//                创建Fragment并返回
                FoodOrderViewFragment fragment = new FoodOrderViewFragment();
                //进来肯定是已点
                dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                dishes_btn.setText("结账");
                fragment.setPosition(position);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        };
        mViewPager.setAdapter(fragmentStatePagerAdapter);
        mViewPager.setCurrentItem(Global.FOOD_ORDER_CURRENT_ITEM);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);

        //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
                int pos=tab.getPosition() % mTitle.length;
                if(pos==0){
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                    dishes_btn.setText("结账");
                }else{
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumUnorderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumUnorderedPrice()));
                    dishes_btn.setText("提交订单");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        mTabLayout = findViewById(R.id.t2_tab);
        mViewPager = findViewById(R.id.t2_pager);
        dishes_num= findViewById(R.id.dishes_num);
        dishes_sum_price= findViewById(R.id.dishes_sum_price);
        dishes_btn= findViewById(R.id.dishes_btn);

        fragmentStatePagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
//                创建Fragment并返回
                FoodOrderViewFragment fragment = new FoodOrderViewFragment();
                //进来肯定是已点
                dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                dishes_btn.setText("结账");
                fragment.setPosition(position);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        };
        mViewPager.setAdapter(fragmentStatePagerAdapter);
        mViewPager.setCurrentItem(Global.FOOD_ORDER_CURRENT_ITEM);
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);

        //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换ViewPager
                mViewPager.setCurrentItem(tab.getPosition());
                int pos=tab.getPosition() % mTitle.length;
                if(pos==0){
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                    dishes_btn.setText("结账");
                }else{
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumUnorderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumUnorderedPrice()));
                    dishes_btn.setText("提交订单");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onInvoicing(View v){
        if(dishes_btn.getText().equals("结账")){

            //    弹出要给ProgressDialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("提示信息");
            progressDialog.setMessage("正在结账中，请稍后......");
            //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
            progressDialog.setCancelable(false);
            //    设置ProgressDialog样式为水平的样式
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            // 在UI Thread当中实例化AsyncTask对象，并调用execute方法
            new OrderDishesAsyncTask().execute();

        }else if(dishes_btn.getText().equals("提交订单")){
            foodInformation.submitOrders(); //提交订单，已点菜品清零

            fragmentStatePagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                //此方法用来显示tab上的名字
                @Override
                public CharSequence getPageTitle(int position) {
                    return mTitle[position % mTitle.length];
                }

                @Override
                public Fragment getItem(int position) {
//                创建Fragment并返回
                    FoodOrderViewFragment fragment = new FoodOrderViewFragment();
                    //进来肯定是已点
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                    dishes_btn.setText("结账");
                    fragment.setPosition(position);
                    return fragment;
                }

                @Override
                public int getCount() {
                    return mTitle.length;
                }
            };
            mViewPager.setAdapter(fragmentStatePagerAdapter);
            mViewPager.setCurrentItem(Global.FOOD_ORDER_CURRENT_ITEM);
            //将ViewPager关联到TabLayout上
            mTabLayout.setupWithViewPager(mViewPager);

            //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //切换ViewPager
                    mViewPager.setCurrentItem(tab.getPosition());
                    int pos=tab.getPosition() % mTitle.length;
                    if(pos==0){
                        dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                        dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                        dishes_btn.setText("结账");
                    }else{
                        dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumUnorderedDishes()));
                        dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumUnorderedPrice()));
                        dishes_btn.setText("提交订单");
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }

    /**
     * 定义一个类，让其继承AsyncTask这个类
     * Params: Void类型，表示传递给异步任务的参数类型是Void
     * Progress: Integer类型，进度条的单位通常都是Integer类型
     * Result：Integer类型，随便返回一个数
     *
     */
    public class OrderDishesAsyncTask extends AsyncTask<Void, Integer, Integer> {
        private Integer progress = 0;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            for(int i=0;i<100;i++){
                SystemClock.sleep(60);
                progress++;
                //    时刻将当前进度更新给onProgressUpdate方法
                publishProgress(progress);
            }
            return progress;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            //    更新ProgressDialog的进度条
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            //    使ProgressDialog框消失
            progressDialog.dismiss();
            double sum_price=foodInformation.getSumOrderedPrice();
            if(user!=null){
                if(user.getOldUser()){
                    Toast.makeText(FoodOrderView.this, "您好，老顾客，本次你可享受 7 折优惠", Toast.LENGTH_SHORT).show();
                    sum_price*=0.7;
                }
            }
            Toast.makeText(FoodOrderView.this, "本次结账金额"+String.valueOf(sum_price)+"元，增加10积分", Toast.LENGTH_SHORT).show();
            dishes_btn.setClickable(false);
            dishes_btn.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.gray));
            foodInformation.checkOut(); //结账

            fragmentStatePagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                //此方法用来显示tab上的名字
                @Override
                public CharSequence getPageTitle(int position) {
                    return mTitle[position % mTitle.length];
                }

                @Override
                public Fragment getItem(int position) {
//                创建Fragment并返回
                    FoodOrderViewFragment fragment = new FoodOrderViewFragment();
                    //进来肯定是已点
                    dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                    dishes_btn.setText("结账");
                    fragment.setPosition(position);
                    return fragment;
                }

                @Override
                public int getCount() {
                    return mTitle.length;
                }
            };
            mViewPager.setAdapter(fragmentStatePagerAdapter);
            mViewPager.setCurrentItem(Global.FOOD_ORDER_CURRENT_ITEM);
            //将ViewPager关联到TabLayout上
            mTabLayout.setupWithViewPager(mViewPager);

            //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
            mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //切换ViewPager
                    mViewPager.setCurrentItem(tab.getPosition());
                    int pos=tab.getPosition() % mTitle.length;
                    if(pos==0){
                        dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumOrderedDishes()));
                        dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumOrderedPrice()));
                        dishes_btn.setText("结账");
                    }else{
                        dishes_num.setText(getString(R.string.dishes_num, foodInformation.getSumUnorderedDishes()));
                        dishes_sum_price.setText(getString(R.string.dishes_sum_price, foodInformation.getSumUnorderedPrice()));
                        dishes_btn.setText("提交订单");
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

    }
}
