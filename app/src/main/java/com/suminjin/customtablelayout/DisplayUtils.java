package com.suminjin.customtablelayout;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by jspark on 2016-03-17.
 */
public class DisplayUtils {
    public static int dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}
