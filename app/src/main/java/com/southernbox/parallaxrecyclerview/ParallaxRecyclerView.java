package com.southernbox.parallaxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
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

    public ParallaxRecyclerView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = -dp2px(context, 10);
            }
        });

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

                View firstView = recyclerView.getLayoutManager().findViewByPosition(firstVisible);
                if (firstView == null) {
                    return;
                }
                tempSpeed = firstView.getTop();
                int viewHeight = firstView.getHeight();
                int recyclerViewHeight = recyclerView.getHeight() / 2;
                int offset = recyclerViewHeight < viewHeight ? recyclerViewHeight - viewHeight : 0;

                if (tempSpeed < offset) {
                    lastTransitionY = -(tempSpeed - offset);
                    firstView.setTranslationY(lastTransitionY / 1.9f);
                    firstView.invalidate();
                } else if (lastTransitionY > 0) {
                    lastTransitionY = 0;
                    float currentY = firstView.getTranslationY();
                    if (currentY > 0) {
                        firstView.setTranslationY(0);
                    }
                }

                // 重置控件, 不然偏移可能出现偏差
                for (int i = firstVisible + 1; i <= (firstVisible + visibleCount); i++) {
                    firstView = recyclerView.getLayoutManager().findViewByPosition(i);
                    if (firstView != null) {
                        float currentY = firstView.getTranslationY();
                        if (currentY > 0) {
                            firstView.setTranslationY(0);
                            firstView.invalidate();
                        }
                    }
                }
            }
        });
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
