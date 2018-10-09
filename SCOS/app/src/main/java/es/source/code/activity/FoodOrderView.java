package es.source.code.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.fragment.FoodViewFragment;
import es.source.code.model.DishesInformation;
import es.source.code.model.User;

public class FoodOrderView extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView dishes_num;
    private TextView dishes_sum_price;
    private Button dishes_btn;
    User user=null;

    private String[] mTitle = {"已下单菜","未下单菜"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);

        user = (User)getIntent().getSerializableExtra("user");
        initView();
    }

    private void initView() {
        final DishesInformation dishesInformation=DishesInformation.getInstance();
        mTabLayout = (TabLayout) findViewById(R.id.t2_tab);
        mViewPager = (ViewPager) findViewById(R.id.t2_pager);
        dishes_num=(TextView) findViewById(R.id.dishes_num);
        dishes_sum_price=(TextView) findViewById(R.id.dishes_sum_price);
        dishes_btn=(Button) findViewById(R.id.dishes_btn);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                dishes_num.setText(getString(R.string.dishes_num, dishesInformation.getOrderedSumDishes()));
                dishes_sum_price.setText(getString(R.string.dishes_sum_price, dishesInformation.getOrderedSumPrice()));
                dishes_btn.setText("结账");
                fragment.setPosition(position);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });
        mViewPager.setCurrentItem(dishesInformation.getOrder_position());
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
                    dishes_num.setText(getString(R.string.dishes_num, dishesInformation.getOrderedSumDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, dishesInformation.getOrderedSumPrice()));
                    dishes_btn.setText("结账");
                }else{
                    dishes_num.setText(getString(R.string.dishes_num, dishesInformation.getUnorderedSumDishes()));
                    dishes_sum_price.setText(getString(R.string.dishes_sum_price, dishesInformation.getUnorderedSumPrice()));
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
            if(user!=null){
                if(user.getOldUser()){
                    Toast.makeText(this, "您好，老顾客，本次你可享受 7 折优惠", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
