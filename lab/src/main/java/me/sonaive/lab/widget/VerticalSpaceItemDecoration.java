package me.sonaive.lab.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int firstTopGapHeight;
    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int firstTopGapHeight, int verticalSpaceHeight) {
        this.firstTopGapHeight = firstTopGapHeight;
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = firstTopGapHeight;
        }
        outRect.bottom = verticalSpaceHeight;
    }
}