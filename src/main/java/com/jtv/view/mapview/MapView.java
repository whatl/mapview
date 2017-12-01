package com.jtv.view.mapview;

import android.text.TextUtils;

/**
 * Created by liugengsheng on 2017/10/23.
 * </p>
 */
public class MapView {

    public MapView() {
    }

    public MapView(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public int id = -1;
    //显示标题
    public String title;
    //显示值
    public String value;

    public boolean merge;//是否显示到上行

    public int titleColor = -1;
    public int valueColor = -1;

    //字数太多不允许换行
    public boolean signLine;

    //最大行数
    public int maxLine = -1;

    //显示一行不全时处理方式
    private TextUtils.TruncateAt mEllipsize;
    //标题是否对齐
    private boolean titleAlign = true;
    public int titleTextSize = -1;
    public int valueTextSize = -1;
    public float weight = -1;//合并显示的权重
    public int mergeSpaceDp = 6;//当merge为true,合并两个控件之间的距离,单位dp

    //当merge为false时,设置与上行显示的距离,单位dp
    public int rowSpace = -1;

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getValueColor() {
        return valueColor;
    }

    public void setValueColor(int valueColor) {
        this.valueColor = valueColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isMerge() {
        return merge;
    }

    public void setMerge(boolean merge) {
        this.merge = merge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSignLine() {
        return signLine;
    }

    public void setSignLine(boolean signLine) {
        this.signLine = signLine;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getValueTextSize() {
        return valueTextSize;
    }

    public void setValueTextSize(int valueTextSize) {
        this.valueTextSize = valueTextSize;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getMergeSpaceDp() {
        return mergeSpaceDp;
    }

    public void setMergeSpaceDp(int mergeSpaceDp) {
        this.mergeSpaceDp = mergeSpaceDp;
    }

    public int getRowSpace() {
        return rowSpace;
    }

    public void setRowSpace(int rowSpace) {
        this.rowSpace = rowSpace;
    }

    public TextUtils.TruncateAt getEllipsize() {
        return mEllipsize;
    }

    public void setEllipsize(TextUtils.TruncateAt mEllipsize) {
        this.mEllipsize = mEllipsize;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public void setTitleAlign(boolean titleAlign) {
        this.titleAlign = titleAlign;
    }

    public boolean isTitleAlign() {
        return titleAlign;
    }
}
