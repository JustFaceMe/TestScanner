package com.google.zxing.client.android.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.zxing.client.android.R;

/**
 * @user: dong.wang
 * @data: 2020/12/30 2:02 PM
 * @desc:
 */
public class LaserView extends FrameLayout {

    private static final int LASER_DURATION = 1000;
    private ImageView laserImage;

    public LaserView(@NonNull Context context) {
        this(context, null, -1);
    }

    public LaserView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LaserView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(R.color.viewfinder_mask));
        laserImage = new ImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        laserImage.setLayoutParams(layoutParams);
        laserImage.setImageResource(R.drawable.scanner_laser);
        addView(laserImage);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE) {
            startLaser();
        } else {
            endLaser();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        endLaser();
    }

    ObjectAnimator laserAnim;
    private void startLaser() {
        post(new Runnable() {
            @Override
            public void run() {
                int height = getHeight();
                laserAnim = ObjectAnimator.ofFloat(laserImage, TRANSLATION_Y, height / 6, height / 5 * 4);
                laserAnim.setDuration(LASER_DURATION * 2);
                laserAnim.setRepeatCount(ValueAnimator.INFINITE);
                laserAnim.start();
                laserAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float ratio = (float)animation.getAnimatedValue() / LASER_DURATION;
                        if(ratio >= 1.0) {
                            ratio = 2.0f - ratio;
                        }
                        laserImage.setAlpha(ratio);
                    }
                });
            }
        });

    }

    private void endLaser() {
        if(laserAnim != null) {
            laserAnim.cancel();
            laserAnim.setTarget(null);
            laserAnim.removeAllListeners();
            laserAnim = null;
        }
    }
}
