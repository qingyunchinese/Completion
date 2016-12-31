package com.qingyun.completion.ui;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 作者： qingyun on 16/12/5.
 * 邮箱：419254872@qq.com
 * 版本：v1.0
 * <p>
 * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
 * MeasureSpec.AT_MOST。
 * <p>
 * <p>
 * MeasureSpec.EXACTLY是精确尺寸，
 * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
 * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
 * <p>
 * <p>
 * MeasureSpec.AT_MOST是最大尺寸，
 * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
 * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
 * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
 * <p>
 * <p>
 * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
 * 通过measure方法传入的模式。
 */
public class CEditViewGroup extends FrameLayout {

    public CEditViewGroup(Context context) {
        super(context);
    }

    public CEditViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CEditViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int measureWidth(int pWidthMeasureSpec) {
        int result = 0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// 得到模式
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// 得到尺寸
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
    }

    private int measureHeight(int pHeightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            RectF rectF = (RectF) childView.getTag();
            childView.layout(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.left+rectF.width()), Math.round(rectF.top+rectF.height()));
        }
    }
}

