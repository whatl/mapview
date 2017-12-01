package com.jtv.view.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jtv.view.mapview.MapViewManager;

import java.util.HashMap;

//need extends RecyclerView.ViewHolder
public class MapHolder  {

    private final View itemView;
    HashMap<Integer, View> map = new HashMap<>();

    public MapHolder(View itemView) {
//        super(itemView);
        this.itemView=itemView;
        map.clear();
    }

    public View getId(int id) {
        if (map.containsKey(id))
            return map.get(id);
        View mView = itemView.findViewById(id);
        map.put(id, mView);
        return mView;
    }

    public TextView setText(int id, String text) {
        TextView mView = (TextView) getId(id);
        if (mView == null) return null;
        mView.setText(text);
        return mView;
    }

    public TextView setTextAndGone(int id, String text) {
        int rowId = MapViewManager.getItemId(id);
        View rowView = getId(rowId);
        if (rowView == null) return null;
        rowView.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        return setText(id, text);
    }


}
