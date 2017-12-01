package com.jtv.view.mapview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liugengsheng on 2017/10/25.
 * </p>
 * 提供标准的通用样式
 */
public class MapViewLayout {

    public static View loadTheme(Context context, ViewGroup root, List<MapView> list) {
        MapViewManager mapViewManager = new MapViewManager(context);
        MapViewTheme mapViewTheme = new MapViewTheme();
        mapViewTheme.setMargin(MapViewManager.dpToPx(5));
        View mapContent = mapViewManager.createMapContent(mapViewTheme, list);
        root.addView(mapContent);
        return root;
    }

}
