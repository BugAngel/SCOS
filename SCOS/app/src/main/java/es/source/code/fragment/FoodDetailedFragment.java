package es.source.code.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.source.code.activity.R;
import es.source.code.model.DishesInformation;

public class FoodDetailedFragment extends Fragment {
    private int position=0;
    private FoodDetailedFragment.ViewHolder vh; // 全局的ViewHolder引用

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        DishesInformation dishesInformation=DishesInformation.getInstance();
        int dishes_img[]=dishesInformation.getDishesImgTo1D();
        String dishes_name[]=dishesInformation.getDishNameTo1D();
        double dishes_price[]=dishesInformation.getDishPriceTo1D();
        int unordered_dishes_num[]=dishesInformation.getUnOrderedDishesNumTo1D();
//        position=dishesInformation.getDetail_position();

        vh = new FoodDetailedFragment.ViewHolder();
        View convertView=LayoutInflater.from(getContext()).inflate(R.layout.food_detailed_item, null);
        vh.food_image=convertView.findViewById(R.id.dish_img);
        vh.food_name = convertView.findViewById(R.id.dish_name);
        vh.food_price=convertView.findViewById(R.id.dish_price);
        vh.food_note=convertView.findViewById(R.id.dish_note);
        vh.btn = convertView.findViewById(R.id.dish_btn);

        vh.food_image.setImageResource(dishes_img[position]);
        vh.food_name.setText(dishes_name[position]);
        vh.food_price.setText(String.valueOf(dishes_price[position]));
        if(unordered_dishes_num[position]>0){
            vh.btn.setText("退点");//已点菜
            vh.btn.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        }else{
            vh.btn.setText("点菜");//未点菜
            vh.btn.setBackgroundColor(getContext().getResources().getColor(R.color.darkgreen));
        }

        convertView.setTag(vh);

        return convertView;
    }

    class ViewHolder {
        ImageView food_image;//菜品图片
        TextView food_name;//菜名
        TextView food_price;//菜价
        EditText food_note;//备注
        Button btn;//按钮
    }
}
