package es.source.code.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.source.code.activity.FoodOrderView;
import es.source.code.adapter.FoodOrderAdapter;
import es.source.code.model.FoodItem;
import es.source.code.utils.Global;

public class FoodOrderViewFragment extends Fragment {
    private int position=0;
    private FoodOrderView activity;
    private ArrayList<FoodItem> ordered_list = new ArrayList<FoodItem>();

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ListView listView;
        FoodOrderAdapter foodOrderItems=null;
        activity = (FoodOrderView) getActivity();
        listView = new ListView(activity);

        if(activity!=null) {
            if(position==0){
                for(FoodItem foodItem: Global.foodInformation){
                    if(foodItem.getOrderedNum()>0){
                        ordered_list.add(foodItem);
                    }
                }
                foodOrderItems=new FoodOrderAdapter(getContext(),ordered_list,0);
            }else{
                for(FoodItem foodItem: Global.foodInformation){
                    if(foodItem.getUnorderedNum()>0){
                        ordered_list.add(foodItem);
                    }
                }
                foodOrderItems=new FoodOrderAdapter(getContext(),ordered_list,1);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "这是第"+i+"行", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mainActivity.this, fristActivity.class);
//                intent.putExtra("data", "mainActivity");
//                startActivity(intent);
            }
        });

        listView.setAdapter(foodOrderItems);
        return listView;
    }
}
