package es.source.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import es.source.code.activity.R;
import es.source.code.model.GridItem;
import es.source.code.utils.ScreenUtils;

public class SCOSHelperAdapter extends BaseAdapter {
    private ArrayList fList;
    private Context context;
    private SCOSHelperAdapter.ViewHolder vh; // 全局的ViewHolder引用

    public SCOSHelperAdapter(Context context, ArrayList list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItem gridItem = (GridItem) fList.get(position);
        int screenWidth = ScreenUtils.getScreenWidth(context);
        int screenHeight = ScreenUtils.getScreenHeight(context);
        int viewWidth = screenWidth / 2;
        int viewHeight = screenHeight / 3;

        if (convertView == null) {
            vh = new SCOSHelperAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_view_item, null);
            vh.name = convertView.findViewById(R.id.item_text);
            vh.image = convertView.findViewById(R.id.item_image);
            vh.relativeLayout = convertView.findViewById(R.id.grid_item);

            vh.name.setText(gridItem.getName());
            vh.image.setImageResource(gridItem.getImage());
            if (position == 0) {
                vh.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            } else if (position == 1) {
                vh.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.orange));
            } else if (position == 2) {
                vh.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.lightblue));
            } else if (position == 3) {
                vh.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.greenyellow));
            } else {
                vh.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.ivory));
            }
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, 50));
            lp.width = viewWidth;
            lp.height = viewHeight;
            convertView.setLayoutParams(lp);
            convertView.setTag(vh);

        } else {
            vh = (SCOSHelperAdapter.ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        ImageView image;//图片
        TextView name;//选项名
        RelativeLayout relativeLayout;
    }
}
