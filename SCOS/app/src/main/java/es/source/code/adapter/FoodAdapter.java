package es.source.code.adapter;

import android.content.Intent;
import android.widget.BaseAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.source.code.activity.LoginOrRegister;
import es.source.code.activity.MainScreen;
import es.source.code.activity.R;
import es.source.code.model.DishesInformation;
import es.source.code.model.FoodItem;


public class FoodAdapter extends BaseAdapter {
    DishesInformation dishesInformation=DishesInformation.getInstance();
    private String [][] dishes_name=dishesInformation.getDishes_name();
    private double[][] dishes_price=dishesInformation.getDishes_price();

    private ArrayList fList;
    private Context context;
    private ViewHolder vh; // 全局的ViewHolder引用
    private int fragment;

    public FoodAdapter(Context context, ArrayList list,int fragment) {
        super();
        this.context = context;
        this.fList=list;
        this.fragment=fragment;
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView ==null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.food_item, null);
            vh.food_name = (TextView) convertView.findViewById(R.id.food_name);
            vh.food_price=(TextView) convertView.findViewById(R.id.food_price);
            vh.btn = (Button) convertView.findViewById(R.id.food_flag);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.food_name.setText(dishes_name[fragment][position]);
        vh.food_price.setText(String.valueOf(dishes_price[fragment][position]));

        /*
         * 此处是重点，ListVeiw的Item里有Button,我在BaseAdapter里写了Button的监听，
         * 但是我每次点击Button,都是最后一个Item的Button响应事件，网上查说是因为传进去
         * 的arg0是最后一个所以响应的是最后一个item，然后我在getview()外面写了一个监听接口， 并把position传进去
         */
        vh.btn.setOnClickListener(new MyListener(position));

        return convertView;
    }

    class ViewHolder {
        TextView food_name;
        TextView food_price;
        Button btn;
    }

    /*
     * 外部监听接口
     */
    class MyListener implements OnClickListener {
        int pos;

        public MyListener(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            FoodItem foodItem = (FoodItem)fList.get(pos);
            Toast.makeText(context, foodItem.getName(), Toast.LENGTH_SHORT).show();
            // 此处可以由View强转来取得Button按钮
            Button btn = (Button) v;

            // 置为退点
            btn.setText("退点");
            btn.setTextColor(context.getResources().getColor(R.color.white));
            btn.setBackgroundColor(context.getResources().getColor(R.color.red));
            btn.setVisibility(View.VISIBLE);
            dishesInformation.setUnordered_dishes_num(1,fragment,pos);
        }
    }

}
