package cn.tonlyshy.fmmusicx.base;

import android.util.Log;

/**
 * Created by lxx on 2017/8/10.
 */

public class LogManager {
    public static void d(String format, Object... args) {
        try {
            Log.d("LogManager", String.format(format, args));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
