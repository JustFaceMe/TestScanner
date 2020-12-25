package com.google.zxing.client.android.utils;

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

    private static final int MAX_COUNT = 30;
    private static ArrayList<Result> results = new ArrayList<>();
    private static int scanCount;

    public static boolean addResult(Result result){
        boolean added = false;
        if(result == null) {
            added = false;
        } else if(results.size() == 0){
            added =  true;
        } else {
            int index = -1;
            for(int i = 0; i < results.size(); i++) {
                Result temp = results.get(i);
                if(isSameResult(temp, result)) {
                    index = i;
                    break;
                }
            }
            if(index == -1) {
                added = true;
            }
        }
        if(added) {
            results.add(result);
            Log.e(TAG, scanCount + " -- " + results.toString());
        }
        return added;
    }

    public static boolean addCount() {
        if(scanCount < MAX_COUNT){
            if(results.size() > 0) {
                scanCount++;
                Log.e(TAG, scanCount+"");
            }
            return true;
        }
        return false;
    }

    public static void clear() {
        scanCount = 0;
        results.clear();
    }

    private static boolean isSameResult(Result one, Result other){
        if(one != null && other != null) {
            return one.getText().equals(other.getText());
        }
        return false;
    }

    public static void logE(String msg){
        Log.e(TAG, msg);
    }
}
