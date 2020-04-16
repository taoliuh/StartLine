package com.huxq17.handygridview.listener;

import android.view.View;

public interface OnItemCapturedListener {
    /**
     * Called when user selected a view to drag.
     *
     */
    void onItemCaptured(View v, int position);

    /**
     * Called when user released the drag view.
     *
     */
    void onItemReleased(View draggedView, View targetView, int currentPosition, int targetPosition,
                        float lastTouchX, float lastTouchY);
}
