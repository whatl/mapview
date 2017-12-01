package com.jtv.view.mapview;

import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liugengsheng on 2017/11/4.
 * </p>
 * 设置mapview中的值,id从0开始算累加
 */
public class GetMapView {

    public static <T extends View> T get(View view, int id) {
        SparseArrayCompat<View> viewHolder = (SparseArrayCompat<View>) view.getTag();// 类似hashmap优化
        if (viewHolder == null) {
            viewHolder = new SparseArrayCompat<View>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static <T> T get(View view, int id, String text) {
        View tv = get(view, id);
        if (tv == null) return null;
        if (tv instanceof TextView) {
            TextView t = (TextView) tv;
            t.setText(text);
        }
        return (T) tv;
    }


    public static View getAndGone(View view, int id, String text) {
        if (view == null)
            return null;
        if (TextUtils.isEmpty(text)) {
            View mView = get(view, MapViewManager.getItemId(id));
            if (mView != null)
                mView.setVisibility(View.GONE);
        } else {
            View mView = get(view, MapViewManager.getItemId(id));
            if (mView != null)
                mView.setVisibility(View.VISIBLE);
        }
        return get(view, id, text);
    }

}
