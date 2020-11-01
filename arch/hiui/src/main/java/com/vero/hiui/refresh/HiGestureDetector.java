package com.vero.hiui.refresh;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class HiGestureDetector implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("HiGestureDetector","onDown");
//        return false;//用在线性布局等不会触发onScroll
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

        Log.e("HiGestureDetector","onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e("HiGestureDetector","onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e("HiGestureDetector","onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.e("HiGestureDetector","onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("HiGestureDetector","onFling");
        return false;
    }
}
