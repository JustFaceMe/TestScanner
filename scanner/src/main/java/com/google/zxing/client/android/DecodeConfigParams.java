package com.google.zxing.client.android;

import com.google.zxing.client.android.camera.FrontLightMode;

/**
 * @user: dong.wang
 * @data: 2020/12/29 3:01 PM
 * @desc: 扫码时的配置参数
 */
public class DecodeConfigParams {
    // 一维码：商品
    public static boolean KEY_DECODE_1D_PRODUCT = false;
    // 一维码：工业
    public static boolean KEY_DECODE_1D_INDUSTRIAL = false;
    // 二维码
    public static boolean KEY_DECODE_QR = true;
    // Data_Matrix
    public static boolean KEY_DECODE_DATA_MATRIX = true;
    // Aztec
    public static boolean KEY_DECODE_AZTEC = false;
    // PDF417
    public static boolean KEY_DECODE_PDF417 = false;
    //CUSTOM_PRODUCT_SEARCH
    public static String KEY_CUSTOM_PRODUCT_SEARCH = null;

    // beep 声音
    public static boolean KEY_PLAY_BEEP = false;
    // vibrate 震动
    public static boolean KEY_VIBRATE = false;
    // 复制到剪切板
    public static boolean KEY_COPY_TO_CLIPBOARD = false;
    // 闪光灯状态
    public static FrontLightMode KEY_FRONT_LIGHT_MODE = FrontLightMode.AUTO;
    // 开启批量扫描模式
    public static boolean KEY_BULK_MODE = false;
    // 是否保存重复的记录
    public static boolean KEY_REMEMBER_DUPLICATES = false;
    // 是否开启历史记录
    public static boolean KEY_ENABLE_HISTORY = false;
    // 检索更多信息
    public static boolean KEY_SUPPLEMENTAL = false;
    // 开启自动对焦
    public static boolean KEY_AUTO_FOCUS = true;
    // 开启反色模式
    public static boolean KEY_INVERT_SCAN = false;
    // 搜索引擎国别
    public static String KEY_SEARCH_COUNTRY = "-";
    // 禁用自动定向，不自动旋转
    public static boolean KEY_DISABLE_AUTO_ORIENTATION = true;

    // 不持续对焦
    public static boolean KEY_DISABLE_CONTINUOUS_FOCUS = false;
    // 不曝光
    public static boolean KEY_DISABLE_EXPOSURE = true;
    // 不实用距离测量
    public static boolean KEY_DISABLE_METERING = true;
    // 不进行条形码场景匹配
    public static boolean KEY_DISABLE_BARCODE_SCENE_MODE = true;
    // 自动打开网址
    public static boolean KEY_AUTO_OPEN_WEB = false;
}
