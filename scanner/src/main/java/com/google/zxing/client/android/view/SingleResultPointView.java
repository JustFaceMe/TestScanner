package com.google.zxing.client.android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;


/**
 * @user: dong.wang
 * @data: 2020/12/29 6:19 PM
 * @desc:
 */
public class SingleResultPointView extends AppCompatImageView implements View.OnClickListener {

    private static final int VIEW_WIDTH = 100;
    private static final int VIEW_HEIGHT = 100;

    // 动画
    protected ObjectAnimator animatorX;
    protected ObjectAnimator animatorY;
    protected ValueAnimator valueAnimator;

    protected Result mResult;

    private OnResultClickListener onResultClickListener;

    public SingleResultPointView(@NonNull Context context, Result result, OnResultClickListener listener) {
        super(context);
        mResult = result;

        ResultPoint[] points = mResult.getResultPoints();
        ResultPoint point = points[points.length - 1];
        initPreviewData();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(VIEW_WIDTH, VIEW_HEIGHT);
        params.leftMargin = (int) (point.getX() - VIEW_WIDTH / 2);
        params.topMargin = (int) (point.getY() - VIEW_HEIGHT / 2);
        setLayoutParams(params);
        setOnClickListener(this);
        onResultClickListener = listener;
    }


    protected void initPreviewData() {
        setBackgroundResource(R.drawable.result_view_bg);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endAnim();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility != VISIBLE) {
            endAnim();
        }
    }

    @Override
    public void onClick(View v) {
        if(onResultClickListener != null) {
            onResultClickListener.onResultClick(mResult);
        }
    }

    /**
     * 开始动画
     */
    protected void startAnim() {
        animatorX = ObjectAnimator.ofFloat(this, SCALE_X, 1.0f, 0.8f, 1.0f);
        animatorY = ObjectAnimator.ofFloat(this, SCALE_Y, 1.0f, 0.8f, 1.0f);
        animatorX.setDuration(1000);
        animatorY.setDuration(1000);

        valueAnimator = ValueAnimator.ofFloat(0, 2000);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                if(null != animatorX && null != animatorY && !animatorX.isRunning()) {
                    animatorY.start();
                    animatorX.start();
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 结束动画
     */
    protected void endAnim() {
        if(null != valueAnimator) {
            valueAnimator.removeAllListeners();
            valueAnimator.cancel();
            valueAnimator = null;
        }

        if(null != animatorX) {
            animatorX.cancel();
            animatorX = null;
        }

        if(null != animatorY) {
            animatorY.cancel();
            animatorY = null;
        }
    }
}
