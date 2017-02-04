package com.southernbox.parallaxrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by SouthernBox on 2017/2/4.
 * 带视差折叠效果的列表控件
 */

public class ParallaxRecyclerView extends RecyclerView {

    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        addItemDecoration(new OverlapDecoration(context));

        if (isInEditMode()) {
            return;
        }

        addOnScrollListener(new OnScrollListener() {

            private float lastTransitionY;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int firstVisible = layoutManager.findFirstVisibleItemPosition();
                int visibleCount = Math.abs(firstVisible - layoutManager.findLastVisibleItemPosition());

                float tempSpeed;

                // 第一个控件固定在顶部(超过屏幕一半时固定一半在顶部, 后续控件层叠上来
                View view1 = recyclerView.getLayoutManager().findViewByPosition(firstVisible);
                if (view1 == null) {
                    return;
                }
                tempSpeed = view1.getTop();
                int viewHeight = view1.getHeight();
                int recyclerViewHeight = recyclerView.getHeight() / 2;
                int offset = recyclerViewHeight < viewHeight ? recyclerViewHeight - viewHeight : 0;

                if (tempSpeed < offset) {
                    lastTransitionY = -(tempSpeed - offset);
                    view1.setTranslationY(lastTransitionY / 1.9f);
                    view1.invalidate();
                } else if (lastTransitionY > 0) {
                    lastTransitionY = 0;
                    float currentY = view1.getTranslationY();
                    if (currentY > 0) {
                        view1.setTranslationY(0);
                    }
                }

                // 重置控件, 不然偏移可能出现偏差
                for (int i = firstVisible + 1; i <= (firstVisible + visibleCount); i++) {
                    view1 = recyclerView.getLayoutManager().findViewByPosition(i);
                    if (view1 != null) {
                        float currentY = view1.getTranslationY();
                        if (currentY > 0) {
                            view1.setTranslationY(0);
                            view1.invalidate();
                        }
                    }
                }
            }
        });
    }

}
