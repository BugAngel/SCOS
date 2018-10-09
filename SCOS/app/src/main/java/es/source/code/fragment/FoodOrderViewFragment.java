package es.source.code.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.source.code.activity.FoodOrderView;
import es.source.code.adapter.FoodOrderAdapter;
import es.source.code.model.DishesInformation;
import es.source.code.model.FoodItem;

public class FoodOrderViewFragment extends Fragment {
    private int position=0;
    private FoodOrderView activity;
    private ArrayList ordered_list = new ArrayList<>();
    DishesInformation dishesInformation=DishesInformation.getInstance();
    private String [][] dishes_name=dishesInformation.getDishes_name();
    private double[][] dishes_price=dishesInformation.getDishes_price();

    private ListView listView;

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FoodOrderAdapter foodOrderItems=null;
        activity = (FoodOrderView) getActivity();
        listView = new ListView(activity);

        if(activity!=null) {
            if(position==0){
                int[][] ordered_dishes_num =dishesInformation.getOrdered_dishes();
                int[][] unordered_dishes_num =dishesInformation.getUnordered_dishes();
                for (int i=0; i<dishesInformation.getDISH_SORT(); i++) {
                    for(int j=0;j<dishesInformation.getDISH_MAX_NUM();j++){
                        if(ordered_dishes_num[i][j]>0){
                            FoodItem item=new FoodItem(dishes_name[i][j],dishes_price[i][j],ordered_dishes_num[i][j],
                                    unordered_dishes_num[i][j],dishesInformation.getOrdered_dish_note(i,j),dishesInformation.getUnordered_dish_note(i,j));
                            ordered_list.add(item);
                        }
                    }
                }
                foodOrderItems=new FoodOrderAdapter(getContext(),ordered_list,0);
            }else{
                int[][] ordered_dishes_num =dishesInformation.getOrdered_dishes();
                int[][] unordered_dishes_num =dishesInformation.getUnordered_dishes();

                for (int i=0; i<dishesInformation.getDISH_SORT(); i++) {
                    for(int j=0;j<dishesInformation.getDISH_MAX_NUM();j++){
                        if(unordered_dishes_num[i][j]>0) {
                            FoodItem item = new FoodItem(dishes_name[i][j], dishes_price[i][j], ordered_dishes_num[i][j],
                                    unordered_dishes_num[i][j], dishesInformation.getOrdered_dish_note(i, j), dishesInformation.getUnordered_dish_note(i, j));
                            ordered_list.add(item);
                        }
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
