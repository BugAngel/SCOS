package es.source.code.adapter;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.FoodView;
import es.source.code.activity.R;
import es.source.code.model.FoodItem;
import es.source.code.utils.Global;


public class FoodAdapter extends BaseAdapter {

    private ArrayList fList;
    private Context context;
    private ViewHolder vh; // 全局的ViewHolder引用
    private FoodView activity;

    public FoodAdapter(Context context, ArrayList list) {
        super();
        this.context = context;
        this.fList=list;
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
            vh.food_name = convertView.findViewById(R.id.food_name);
            vh.food_price=convertView.findViewById(R.id.food_price);
            vh.food_store=convertView.findViewById(R.id.food_store);
            vh.btn = convertView.findViewById(R.id.food_flag);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        FoodItem foodItem = (FoodItem)fList.get(position);
        vh.food_name.setText(foodItem.getName());
        vh.food_price.setText(context.getString(R.string.price, foodItem.getPrice()));
        vh.food_store.setText(String.valueOf(foodItem.getStore()));

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
        TextView food_store;
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
            foodItem.setUnorderedNum(1);
            for(int i = 0;i < Global.foodInformation.size(); i ++){
                FoodItem temp=Global.foodInformation.get(i);
                if(temp.getName().equals(foodItem.getName())){
                    Global.foodInformation.set(i,foodItem);
                    break;
                }
            }

            activity.updateFragment();
        }
    }

}
