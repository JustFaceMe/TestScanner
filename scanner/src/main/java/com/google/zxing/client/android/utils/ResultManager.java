package com.google.zxing.client.android.utils;

import android.security.identity.EphemeralPublicKeyNotFoundException;
import android.util.Log;

import com.google.zxing.Result;

import java.util.ArrayList;

/**
 * @user: dong.wang
 * @data: 2020/12/25 1:47 PM
 * @desc:
 */
public class ResultManager {
    private static final String TAG = ResultManager.class.getSimpleName();

    public static void dealResults(Result[] array) {
        for (Result result : array) {
            Log.e(TAG, result.getResultPoints().length+"");
        }
    }

    public static void dealResult(Result result) {
        if(result == null) {
            return;
        }


    }

    public static void logE(String msg){
        Log.e(TAG, msg);
    }
}
