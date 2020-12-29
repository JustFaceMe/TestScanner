package com.google.zxing.client.android.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;


/**
 * @user: dong.wang
 * @data: 2020/12/29 6:19 PM
 * @desc:
 */
public class SingleResultPointView extends AppCompatImageView implements View.OnClickListener {

    private static final int VIEW_WIDTH = 100;
    private static final int VIEW_HEIGHT = 100;

    protected Result mResult;

    public SingleResultPointView(@NonNull Context context, Result result) {
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
    }


    protected void initPreviewData() {
        setBackgroundColor(Color.RED);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), mResult.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始动画
     */
    protected void startAnim() {
    }
}
