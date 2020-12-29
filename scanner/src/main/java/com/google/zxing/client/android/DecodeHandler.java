/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import android.graphics.Bitmap;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.Map;

final class DecodeHandler extends Handler {

  private final CaptureActivity activity;
  private final QRCodeMultiReader qrCodeMultiReader;
  private Map<DecodeHintType,?> hints;
  private boolean running = true;

  DecodeHandler(CaptureActivity activity, Map<DecodeHintType,Object> hints) {
    qrCodeMultiReader = new QRCodeMultiReader();
    this.hints = hints;
    this.activity = activity;
  }

  @Override
  public void handleMessage(Message message) {
    if (message == null || !running) {
      return;
    }
    if (message.what == R.id.decode) {
      decode((byte[]) message.obj, message.arg1, message.arg2);
    } else if (message.what == R.id.quit) {
      running = false;
      Looper.myLooper().quit();
    }
  }

  /**
   * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
   * reuse the same reader objects from one decode to the next.
   *
   * @param data   The YUV preview frame.
   * @param width  The width of the preview frame.
   * @param height The height of the preview frame.
   */
  private void decode(byte[] data, int width, int height) {
    Result[] results = null;
    PlanarYUVLuminanceSource source = activity.getCameraManager().buildLuminanceSource(data, width, height);
    if (source != null) {
      BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
      try {
        results = qrCodeMultiReader.decodeMultiple(bitmap, hints);
      } catch (ReaderException re) {
        // continue
      } finally {
        qrCodeMultiReader.reset();
      }
    }

    Handler handler = activity.getHandler();
    if (results != null && results.length > 0) {
      // Don't log the barcode contents for security.
      if (handler != null) {
        Message message = Message.obtain(handler, R.id.decode_succeeded, results);
        Bundle bundle = new Bundle();
//        bundleThumbnail(source, bundle);
        previewFrame(data, source, bundle);
        message.setData(bundle);
        message.sendToTarget();
      }
    } else {
      if (handler != null) {
        Message message = Message.obtain(handler, R.id.decode_failed);
        message.sendToTarget();
      }
    }
  }

  private static void bundleThumbnail(PlanarYUVLuminanceSource source, Bundle bundle) {
    int[] pixels = source.renderThumbnail();
    int width = source.getThumbnailWidth();
    int height = source.getThumbnailHeight();
    Bitmap bitmap = Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
    bundle.putByteArray(DecodeThread.BARCODE_BITMAP, out.toByteArray());
    bundle.putFloat(DecodeThread.BARCODE_SCALED_FACTOR, (float) width / source.getWidth());
  }

  public void previewFrame(byte[] data, PlanarYUVLuminanceSource source, Bundle bundle) {
    Camera camera = activity.getCameraManager().getCamera().getCamera();
    Camera.Size localSize = camera.getParameters().getPreviewSize();  //获得预览分辨率
    YuvImage localYuvImage = new YuvImage(data, 17, localSize.width, localSize.height, null);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    //把摄像头回调数据转成YUV，再按图像尺寸压缩成JPEG，从输出流中转成数组
    localYuvImage.compressToJpeg(new Rect(0, 0, localSize.width, localSize.height), 80, localByteArrayOutputStream);
    byte[] mParamArrayOfByte = localByteArrayOutputStream.toByteArray();
    //生成Bitmap
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPreferredConfig = Bitmap.Config.RGB_565;  //构造位图生成的参数，必须为565。类名+enum
    Bitmap mCurrentBitmap = BitmapFactory.decodeByteArray(mParamArrayOfByte, 0, mParamArrayOfByte.length, localOptions);
    Log.e("QrCode localSize", "w = "+localSize.width +" -- h = " +localSize.height);
    Log.e("QrCode bitmap", "w = "+mCurrentBitmap.getWidth() +" -- h = " +mCurrentBitmap.getHeight());
    Log.e("QrCode source", "w = "+source.getWidth() +" -- h = " +source.getHeight());
    bundle.putByteArray(DecodeThread.BARCODE_BITMAP, mParamArrayOfByte);
    bundle.putFloat(DecodeThread.BARCODE_SCALED_FACTOR, (float) mCurrentBitmap.getWidth() / source.getWidth());
  }
}
