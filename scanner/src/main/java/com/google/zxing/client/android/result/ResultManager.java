package com.google.zxing.client.android.result;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;

import java.util.ArrayList;

/**
 * @user: dong.wang
 * @data: 2020/12/25 1:47 PM
 * @desc:
 */
public class ResultManager {
    private static final String TAG = ResultManager.class.getSimpleName();

    private static Result[] results;

    public static void initResults(Result[] array) {
        results = array;
    }

    public static Result[] dealResults(int w, float cameraScreenRatioX, float cameraScreenRatioY) {
        if(results == null || results.length <= 0){
            return results;
        }
        for (Result result : results) {
            dealResult(result, w, cameraScreenRatioX, cameraScreenRatioY);
        }
        return results;
    }

    private static Result dealResult(Result result, int w, float cameraScreenRatioX, float cameraScreenRatioY) {
        if(result == null) {
            return null;
        }

        ResultPoint[] points = result.getResultPoints();
        if(points == null || points.length <= 0) {
            return null;
        }

        ResultPoint center = getCenterPoint(points);

        float x = w - center.getY() / cameraScreenRatioX;
        float y = center.getX() / cameraScreenRatioY;
        ResultPoint point = new ResultPoint(x, y);
        result.addResultPoints(new ResultPoint[]{point});
        return result;
    }

    public static ResultPoint getCenterPoint(ResultPoint[] points) {
        if(points == null || points.length <= 0) {
            return null;
        }

        float dx = (points[0].getX() - points[2].getX()) / 2;
        float dy = (points[0].getY() - points[2].getY()) / 2;
        float cx = points[0].getX() - dx;
        float cy = points[0].getY() - dy;
        return new ResultPoint(cx, cy);
    }
}
