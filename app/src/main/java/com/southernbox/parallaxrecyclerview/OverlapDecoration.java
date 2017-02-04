package com.southernbox.parallaxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by SouthernBox on 2017/2/4.
 * 实现列表的折叠效果
 */

class OverlapDecoration extends RecyclerView.ItemDecoration {

    private int offset;

    OverlapDecoration(Context context) {
        offset = -dp2px(context, 10);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = offset;
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
