package es.source.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import es.source.code.activity.R;
import es.source.code.model.FoodItem;

public class FoodOrderAdapter extends BaseAdapter {
    private ArrayList fList;
    private Context context;
    private ViewHolder vh; // 全局的ViewHolder引用
    private int fragment;

    public FoodOrderAdapter(Context context, ArrayList list,int fragment) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.food_order_item, null);
            vh.food_name = convertView.findViewById(R.id.food_name);
            vh.food_price=convertView.findViewById(R.id.food_price);
            vh.food_num=convertView.findViewById(R.id.food_num);
            vh.food_note=convertView.findViewById(R.id.food_note);
            vh.btn = convertView.findViewById(R.id.food_flag);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        FoodItem foodItem=(FoodItem)fList.get(position);
        if(fragment==0){ //已点菜
                vh.food_name.setText(foodItem.getName());
                vh.food_price.setText(context.getString(R.string.price, foodItem.getPrice()));
                vh.food_num.setText(String.valueOf(foodItem.getOrderedNum()));
                vh.food_note.setText(foodItem.getNote());
                vh.btn.setVisibility(View.INVISIBLE);
        }else
        {
                vh.food_name.setText(foodItem.getName());
                vh.food_price.setText(context.getString(R.string.price, foodItem.getPrice()));
                vh.food_num.setText(String.valueOf(foodItem.getUnorderedNum()));
                vh.food_note.setText(foodItem.getNote());
        }
        return convertView;
    }

    class ViewHolder {
        TextView food_name;//菜品
        TextView food_price;//价格
        TextView food_num;//数量
        TextView food_note;//备注
        Button btn;//退点按钮
    }
}
