package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.FoodView;
import es.source.code.adapter.FoodAdapter;
import es.source.code.model.DishesInformation;
import es.source.code.model.FoodItem;

public class FoodViewFragment extends Fragment {
    DishesInformation dishesInformation=DishesInformation.getInstance();
    private String [][] dishes_name=dishesInformation.getDishes_name();
    private double[][] dishes_price=dishesInformation.getDishes_price();

    private int position=0;
    private FoodView activity;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ListView listView;
        FoodAdapter foodItems=null;
        activity = (FoodView) getActivity();
        listView = new ListView(activity);

        if(activity!=null) {
            foodItems=new FoodAdapter(getContext(),getDishesData(position),position);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=0;
                Intent intent = new Intent(getActivity(), FoodDetailed.class);
                for (int temp=0;temp<position;temp++) {
                    pos += dishes_name[temp].length;
                }
                dishesInformation.setDetail_position(pos+i);
                startActivity(intent);
            }
        });

        listView.setAdapter(foodItems);
        return listView;
    }


    /**
     * 获得相应菜品信息
     */
    private ArrayList getDishesData(int position) {
        ArrayList list = new ArrayList<>();

        for (int i=0; i<dishesInformation.getDISH_MAX_NUM(); i++) {
            FoodItem item=new FoodItem(dishes_name[position][i],dishes_price[position][i]);
            list.add(item);
        }

        return list;
    }

}
