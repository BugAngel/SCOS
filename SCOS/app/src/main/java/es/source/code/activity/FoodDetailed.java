package es.source.code.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.source.code.fragment.FoodDetailedFragment;
import es.source.code.fragment.FoodOrderViewFragment;
import es.source.code.model.DishesInformation;

public class FoodDetailed extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    DishesInformation dishesInformation=DishesInformation.getInstance();
    String mTitle[]=dishesInformation.getDishNameTo1D();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);

        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.t3_tab);
        mViewPager = (ViewPager) findViewById(R.id.t3_pager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //此方法用来显示tab上的名字
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position % mTitle.length];
            }

            @Override
            public Fragment getItem(int position) {
//                创建Fragment并返回
                int pos=position % mTitle.length;
//                dishesInformation.setDetail_position(pos);
                FoodDetailedFragment fragment = new FoodDetailedFragment();
                fragment.setPosition(pos);
                return fragment;
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }
        });

        mViewPager.setCurrentItem(dishesInformation.getDetail_position());
        //将ViewPager关联到TabLayout上
        mTabLayout.setupWithViewPager(mViewPager);

        //  那我们如果真的需要监听tab的点击或者ViewPager的切换,则需要手动配置ViewPager的切换,例如:
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                dishesInformation.setDetail_position(tab.getPosition());
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
}
