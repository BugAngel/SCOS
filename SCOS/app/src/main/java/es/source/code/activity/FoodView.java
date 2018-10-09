package es.source.code.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import es.source.code.fragment.FoodViewFragment;
import es.source.code.model.DishesInformation;
import es.source.code.model.User;

public class FoodView extends AppCompatActivity {
    private String[] mTitle = {"冷菜","热菜","海鲜","酒水"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    DishesInformation dishesInformation=DishesInformation.getInstance();
    User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        user = (User)getIntent().getSerializableExtra("user");
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.foodview_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tl_tab);
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=null;
        switch (item.getItemId()) {
            case R.id.action_bar_ordered_dish:
//                Toast.makeText(this, "你点击了“已点菜品”按键！", Toast.LENGTH_SHORT).show();
                dishesInformation.setOrder_position(1);
                intent=new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("user",user);
                startActivity(intent);
                return true;
            case R.id.action_bar_check_order:
//                Toast.makeText(this, "你点击了“查看订单”按键！", Toast.LENGTH_SHORT).show();
                dishesInformation.setOrder_position(0);
                intent=new Intent(FoodView.this, FoodOrderView.class);
                intent.putExtra("user",user);
                startActivity(intent);
                return true;
            case R.id.action_bar_call_service:
//                Toast.makeText(this, "你点击了“呼叫服务”按键！", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
