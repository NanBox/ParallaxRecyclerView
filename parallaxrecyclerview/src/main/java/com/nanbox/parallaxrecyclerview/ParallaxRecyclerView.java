package com.nanbox.parallaxrecyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 带视差折叠效果的列表控件
 * Created by NanBox on 2017/2/4.
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

        setLayoutManager(new LinearLayoutManager(context));

        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect,
                                       @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull State state) {
                super.getItemOffsets(outRect, view, parent, state);
                //获取当前项的下标
                final int currentPosition = parent.getChildLayoutPosition(view);
                //获取最后一项的下标
                final int lastPosition = state.getItemCount() - 1;
                if (currentPosition != lastPosition) {
                    outRect.bottom = -dp2px(context, 10);
                }
            }
        });

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int visibleCount = lastPosition - firstPosition;
                //重置控件的位置及高度
                int elevation = 1;
                for (int i = firstPosition - 1; i <= (firstPosition + visibleCount) + 1; i++) {
                    View view = layoutManager.findViewByPosition(i);
                    if (view != null) {
                        if (view instanceof CardView) {
                            ((CardView) view).setCardElevation(dp2px(context, elevation));
                            elevation += 5;
                        }
                        float translationY = view.getTranslationY();
                        if (i > firstPosition && translationY != 0) {
                            view.setTranslationY(0);
                        }
                    }
                }

                View firstView = layoutManager.findViewByPosition(firstPosition);
                if (firstView != null) {
                    float firstViewTop = firstView.getTop();
                    firstView.setTranslationY(-firstViewTop / 2.0f);
                }
            }
        });
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
