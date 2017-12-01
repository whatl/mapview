package com.jtv.view.mapview;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by liugengsheng on 2017/10/23.
 * </p>
 * 为了快速开发,不需要创建xml直接生成标题和值
 * 1 可以合并行,并且合并行之间可以配置行间隙,或者列间隙
 * 2 实现了标题对齐
 * 3 可以配置列之间权重
 */
public class MapViewManager {
    // 一个条目中的标题id,相对于内容
    public static final int MAPVIEW_TITLE = 1000;
    // 一个条目的id,相对于内容
    public static final int MAPVIEW_ITEM = 2000;

    private final Context context;

    public MapViewManager(Context context) {
        this.context = context;
    }


    // 生产一个条目
    public View createMapView(View item, MapView mConfig) {
        TextView tv = (TextView) item.findViewById(R.id.tv_title);
        TextView tv_value = (TextView) item.findViewById(R.id.tv_value);
        tv_value.setId(mConfig.getId());
        if (mConfig.getEllipsize() != null)
            tv_value.setEllipsize(mConfig.getEllipsize());
        tv_value.setSingleLine(mConfig.isSignLine());
        if (mConfig.getMaxLine() != -1)
            tv_value.setMaxLines(mConfig.getMaxLine());

        tv.setId(getTitleId(mConfig.getId()));
        item.setId(getItemId(mConfig.getId()));

        if (!TextUtils.isEmpty(mConfig.getValue()))
            tv_value.setText(mConfig.getValue());
        if (mConfig.getTitleColor() != -1)
            tv.setTextColor(mConfig.getTitleColor());
        if (mConfig.getValueColor() != -1)
            tv_value.setTextColor(mConfig.getValueColor());
        tv.setText(mConfig.getTitle());
        return item;
    }


    public View createMapContent(MapViewTheme theme, List<MapView> map) {
        ViewGroup root = (ViewGroup) View.inflate(context, R.layout.mapview_item_group_root, null);
        LinearLayout rootContent = (LinearLayout) root.findViewById(R.id.mapview_root);
        if (theme != null) {
            if (theme.isTitleAlign()) textHeadAlign(map);
            if (theme.leftMargin != -1 && theme.rightMargin != -1 && theme.rightMargin != -1 && theme.bottomMargin != -1) {
                RelativeLayout.LayoutParams mLayoutPar = (RelativeLayout.LayoutParams) rootContent.getLayoutParams();
                int leftMargin = theme.leftMargin == -1 ? mLayoutPar.leftMargin : theme.leftMargin;
                int topMargin = theme.topMargin == -1 ? mLayoutPar.topMargin : theme.topMargin;
                int rightMargin = theme.rightMargin == -1 ? mLayoutPar.rightMargin : theme.rightMargin;
                int bottomMargin = theme.bottomMargin == -1 ? mLayoutPar.bottomMargin : theme.bottomMargin;
                mLayoutPar.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            }

        } else
            textHeadAlign(map);
        LinearLayout upLineItemRoot = null;
        int id = 0;
        for (int i = 0; i < map.size(); i++) {
            MapView mapView = map.get(i);
            if (mapView.getId() == -1)
                mapView.setId(id++);
            if (i == 0) mapView.setMerge(false);
            boolean merge = mapView.isMerge();
            LinearLayout itemRoot = null;
            if (!merge)
                itemRoot = (LinearLayout) View.inflate(context, R.layout.mapview_item_group_content, null);
            ViewGroup itemContent = (ViewGroup) View.inflate(context, R.layout.mapview_item_group, null);
            View item = View.inflate(context, R.layout.mapview_item, null);
            item = createMapView(item, mapView);
            LinearLayout itemContentIn = (LinearLayout) itemContent.findViewById(R.id.ll_item_in);

            itemContentIn.addView(item);
            if (itemRoot != null) {
                itemRoot.addView(itemContent);
                rootContent.addView(itemRoot);
            } else
                upLineItemRoot.addView(itemContent);

//            if(mapView.getWeight()>0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) itemContent.getLayoutParams();
            layoutParams.weight = mapView.getWeight() < 0 ? 1 : mapView.getWeight();
            itemContent.setLayoutParams(layoutParams);
//            }

            //合并之间的间隙
            if (merge && mapView.getMergeSpaceDp() > 0) {
                int childCount = upLineItemRoot.getChildCount();
                if (childCount > 1) {
                    int halfSpace = mapView.getMergeSpaceDp() / 2;
                    halfSpace = dpToPx(halfSpace);
                    for (int j = 0; j < childCount * 2; j++) {
                        View childAt = upLineItemRoot.getChildAt(j % childCount);
                        if (j % 2 == 0)
                            childAt.setPadding(childAt.getPaddingLeft(), childAt.getPaddingTop(), (int) halfSpace, childAt.getPaddingBottom());
                        else if (j % 2 == 1)
                            childAt.setPadding((int) halfSpace, childAt.getPaddingTop(), childAt.getPaddingRight(), childAt.getPaddingBottom());
                    }
                }
            }

            //合并之间的间隙
            if (!merge && mapView.getRowSpace() > -1) {
                LinearLayout.LayoutParams mLayoutParmter = (LinearLayout.LayoutParams) itemRoot.getLayoutParams();
                mLayoutParmter.setMargins(mLayoutParmter.leftMargin, dpToPx(mapView.getRowSpace()), mLayoutParmter.rightMargin, mLayoutParmter.bottomMargin);
                itemRoot.setLayoutParams(mLayoutParmter);
            }


            if (itemRoot != null)
                upLineItemRoot = itemRoot;
        }
        return root;
    }

    public static MapView build(String title, String content) {
        MapView mapView = new MapView();
        mapView.setTitle(title);
        mapView.setValue(content);
        return mapView;
    }

    public static List<MapView> builds(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        ArrayList<MapView> maps = new ArrayList<MapView>();
        for (String key :
                keySet) {
            String value = map.get(key);
            MapView build = build(key, value);
            maps.add(build);
        }
        return maps;
    }

    public static int getTitleId(int id) {
        return MAPVIEW_TITLE + id;
    }

    public static int getItemId(int id) {
        return MAPVIEW_ITEM + id;
    }



    //字体对齐
    public static void textHeadAlign(List<MapView> map) {
        int max = 0;
        for (MapView integer : map) {
            if(!integer.isTitleAlign())continue;
            String value = integer.getTitle();
            if (value == null)
                continue;
            int len = value.length();
            if (len > max)
                max = len;
        }

        if (max > 7)
            // "大于7个字还没提供解决方案...";
            return;
        for (MapView integer : map) {
            if(!integer.isTitleAlign())continue;
            String value = integer.getTitle();
            if (value == null)
                continue;
            value = typeSetting(value, max);
            integer.setTitle(value);
        }
    }


    // 返回文字排版后的内容,max 最长的标题个数
    public static String typeSetting(String value, int max) {
        int len = value.length();
        int cha = 0;
        if (len < max)
            cha = max - len;
        else
            return value;

        String fullWidthSpace = "　";// 全角空格
        String halfWidthSpace = "  ";// 两个半角空格

        String temp = "";
        if (max == 7 || max == 6) {
            if (len <= 2) {
                for (int i = 0; i < cha; i++)
                    temp += fullWidthSpace;
                return temp + value;
            } else if (len == 3) {
                for (int i = 0; i < cha; i++)
                    temp += fullWidthSpace;
                String start = value.substring(0, 1);
                String end = value.substring(1);
                return start + temp + end;
            } else if (len == 4) {// 四个字符
                boolean isBaseNumber = cha % 2 == 1;
                temp += fullWidthSpace;// 全角空格
                if (isBaseNumber) // 基数
                    temp += halfWidthSpace;// 两个半角空格,一个不行，两个稍微有丁点多
                String start = value.substring(0, 1);
                String middlen = value.substring(1, 2);
                String end = value.substring(2);
                return start + temp + middlen + temp + end;
            } else if (len == 5) {// 5个字符
                for (int i = 0; i < cha; i++)
                    temp += fullWidthSpace;// 全角空格
                String start = value.substring(0, 2);
                String end = value.substring(2);
                return start + temp + end;
            } else if (len == 6) {// 6个字符
                temp = halfWidthSpace;
                String start = value.substring(0, 2);
                String middlen = value.substring(2, 4);
                String end = value.substring(4);
                return start + temp + middlen + temp + end;
            }
            return value;
        }
        // 五个字以内的对齐
        if (len <= 2) {
            for (int i = 0; i < cha; i++)
                temp += fullWidthSpace;
            return temp + value;
        } else if (cha % 2 == 0) {
            for (int i = 0; i < cha; i++)
                temp = fullWidthSpace + temp;// 这个空格不是普通空格，是全角空格也可以用xml中四个&#160;表示全角空格
            String start = value.substring(0, 1);
            String end = value.substring(1);
            return start + temp + end;
        } else if (max % len == 1 && len == 4) {
            String srart = value.substring(0, 1);
            String middlen = value.substring(1, 2);
            String end = value.substring(2);
            return srart + halfWidthSpace + middlen + halfWidthSpace + end;
        } else if (max % len == 1 && len == 3) {// 3个字的
            String srart = value.substring(0, 1);
            String end = value.substring(1);
            return srart + fullWidthSpace + end;// 一个字的空格
        }
        return value;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
