package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import es.source.code.activity.FoodDetailed;
import es.source.code.activity.FoodView;
import es.source.code.adapter.FoodAdapter;
import es.source.code.model.FoodItem;
import es.source.code.utils.Global;

public class FoodViewFragment extends Fragment {

    private int position=0;
    private FoodView activity;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ListView listView;
        FoodAdapter foodAdapter=null;
        activity = (FoodView) getActivity();
        listView = new ListView(activity);

        if(activity!=null) {
            foodAdapter=new FoodAdapter(getContext(),getDishesData(position));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // position是当前item在listview中适配器里的位置
            // id是当前item在listview里的第几行的位置
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent intent = new Intent(getActivity(), FoodDetailed.class);

                FoodItem foodItem=(FoodItem)getDishesData(position).get(pos);
                for(int i = 0;i < Global.foodInformation.size(); i ++){
                    if(foodItem.getName().equals(Global.foodInformation.get(i).getName())){
                        Global.FOOD_DETAIL_CURRENT_ITEM=i;
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        listView.setAdapter(foodAdapter);
        return listView;
    }


    /**
     * 获得相应菜品信息
     */
    private ArrayList getDishesData(int position) {
        ArrayList<FoodItem> list = new ArrayList<FoodItem>();

        for (FoodItem foodItem : Global.foodInformation) {
            if(foodItem.getSort()==position){
                list.add(foodItem);
            }
        }

        return list;
    }

}
