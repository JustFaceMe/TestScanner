package com.google.zxing.client.android.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.zxing.Result;

/**
 * @user: dong.wang
 * @data: 2020/12/29 6:19 PM
 * @desc:
 */
public class MoreResultPointView extends SingleResultPointView{


    public MoreResultPointView(@NonNull Context context, Result result, OnResultClickListener listener) {
        super(context, result, listener);
    }

    @Override
    protected void initPreviewData() {
        super.initPreviewData();
    }
}
