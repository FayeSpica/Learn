package cn.tonlyshy.fmmusicx.base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lxx on 2017/8/10.
 */

public class ToastManager {
    public static void show(Context context, String format, Object... args) {
        try {
            Toast.makeText(context, String.format(format, args), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
