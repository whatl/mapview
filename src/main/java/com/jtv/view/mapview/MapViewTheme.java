package com.helloworld.view.mapview;

/**
 * Created by liugengsheng on 2017/10/23.
 * </p>
 */

public class MapViewTheme {
    //是否对齐
    public boolean titleAlign=true;

    //px
    public int leftMargin=-1;
    public int topMargin=-1;
    public int rightMargin=-1;
    public int bottomMargin=-1;

    public static final int LEFT=1;
    public static final int TOP=1;
    public static final int RIGHT=1;
    public static final int CENTER=1;

    public boolean isTitleAlign() {
        return titleAlign;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public void setTitleAlign(boolean titleAlign) {
        this.titleAlign = titleAlign;
    }

    public void setMargin(int margin){
        setLeftMargin(margin);
        setTopMargin(margin);
        setRightMargin(margin);
        setBottomMargin(margin);
    }
}
