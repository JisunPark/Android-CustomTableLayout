package com.suminjin.customtablelayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * 상단에 제목, 왼쪽에 분류, 오른쪽에 내용이 들어가는 표 타입.
 * <p/>
 * Created by jspark on 2016-03-30.
 */
abstract public class BaseTable extends GridLayout {
    protected Context context;

    private int columnCount = -1;
    private int realColumnCount;
    private int[] width;
    private int[] textAlignment;

    private GridLayout gridLayout;
    private HashMap<String, TextView> contentViewMap = new HashMap<>();
    private Resources res;
    private int totalWidth = 0;

    public BaseTable(Context context) {
        this(context, null);
    }

    public BaseTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public BaseTable(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        this.context = context;
        res = context.getResources();

        // root view인 gridLayout에 대한 설정
        gridLayout = this;
        LayoutParams p = new LayoutParams();
        p.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        gridLayout.setLayoutParams(p);
        gridLayout.setPadding(1, 1, 1, 1);
        gridLayout.setBackgroundColor(Color.rgb(0x33, 0x33, 0x33));

    }

    public void initTable(int[] titleRedId, int[] width, int[] textAlignment) {
        initTable(titleRedId, width, textAlignment, -1);
    }

    public void initTable(int[] titleRedId, int[] width, int[] textAlignment, int tHeight) {
        columnCount = titleRedId.length;
        if (columnCount == width.length && columnCount == textAlignment.length) { // check column and data count
            this.width = width;
            this.textAlignment = textAlignment;

            realColumnCount = columnCount + (columnCount - 1); // add divider count
            gridLayout.setColumnCount(realColumnCount);

            // calculate total width
            int titleHeight = tHeight > 0 ? tHeight : res.getDimensionPixelSize(R.dimen.table_title_row_height);

            // add title
            for (int i = 0; i < columnCount; i++) {
                if (i > 0) {
                    // occupied view at vertical divider position
                    View divider = new View(context);
                    GridLayout.LayoutParams dividerP = new GridLayout.LayoutParams();
                    dividerP.width = 1;
                    dividerP.height = titleHeight;
                    divider.setLayoutParams(dividerP);
                    divider.setBackgroundColor(Color.rgb(0x88, 0x88, 0x88));
                    gridLayout.addView(divider);

                    totalWidth += 1;
                }

                TextView txtView = new TextView(context);
                GridLayout.LayoutParams p = new GridLayout.LayoutParams();
                int w = res.getDimensionPixelSize(width[i]);
                p.width = w;
                p.height = titleHeight;
                txtView.setLayoutParams(p);
                txtView.setText(res.getString(titleRedId[i]));
                txtView.setBackgroundResource(R.drawable.table_title_bg);
                txtView.setTextColor(Color.rgb(0x00, 0x00, 0x00));
                txtView.setGravity(Gravity.CENTER);
                txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                txtView.setTypeface(null, Typeface.BOLD);
                txtView.setMaxLines(1);
                gridLayout.addView(txtView);

                totalWidth += w;
            }

            // divider under title layout
            View dividerUnderTitle = new View(context);
            GridLayout.LayoutParams p2 = new GridLayout.LayoutParams();
            p2.width = totalWidth; // LayoutParams.MATCH_PARENT;
            p2.height = 3; // 3px
            p2.columnSpec = GridLayout.spec(0, realColumnCount);
            dividerUnderTitle.setLayoutParams(p2);
            dividerUnderTitle.setBackgroundColor(Color.rgb(0x00, 0x00, 0x00));
            gridLayout.addView(dividerUnderTitle);
        } else {
            Log.e(AppConfig.TAG, "NOT match column and data count!!!");
        }
    }

    public void addRow(String tag) {
        addRow(tag, -1);
    }

    public void addRow(String tag, int rowHeight) {
        int height = rowHeight > 0 ? rowHeight : res.getDimensionPixelSize(R.dimen.table_contents_row_height);

        // horizontal divider
        if (!contentViewMap.isEmpty()) {
            View divider = new View(context);
            LayoutParams dividerP = new LayoutParams();
            dividerP.width = totalWidth;
            dividerP.height = 1;
            dividerP.columnSpec = GridLayout.spec(0, realColumnCount);
            divider.setLayoutParams(dividerP);
            divider.setBackgroundResource(R.color.table_divider);
            addView(divider);
        }


        for (int i = 0; i < columnCount; i++) {
            if (i > 0) {
                // vertical divider
                View divider = new View(context);
                LayoutParams dividerP = new LayoutParams();
                dividerP.width = 1;
                dividerP.height = height;
                divider.setLayoutParams(dividerP);
                divider.setBackgroundColor(Color.rgb(0x88, 0x88, 0x88));
                gridLayout.addView(divider);
            }

            TextView txtView = new TextView(context);
            LayoutParams p = new LayoutParams();
            p.width = res.getDimensionPixelSize(width[i]);
            p.height = height;
            txtView.setLayoutParams(p);
            txtView.setBackgroundResource(R.color.table_row_bg);
            txtView.setTextColor(Color.rgb(0xff, 0xff, 0xff));
            txtView.setGravity(textAlignment[i]);
            txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            txtView.setTypeface(null, Typeface.BOLD);
            int padding = res.getDimensionPixelOffset(R.dimen.table_text_padding);
            txtView.setPadding(padding, 0, padding, 0);
            txtView.setMaxLines(1);
            gridLayout.addView(txtView);
            contentViewMap.put(tag + Integer.toString(i), txtView);
        }
    }

    /**
     * set data at specific cell
     *
     * @param tag
     * @param index
     * @param data
     */
    public void setData(String tag, int index, String data, OnClickListener onClickListener) {
        String key = tag + Integer.toString(index);
        if (contentViewMap.containsKey(key)) {
            TextView v = contentViewMap.get(key);
            v.setText(data);
            v.setOnClickListener(onClickListener);
        }
    }

    public void setData(String tag, int index, Spanned spanned, OnClickListener onClickListener) {
        String key = tag + Integer.toString(index);
        if (contentViewMap.containsKey(key)) {
            TextView v = contentViewMap.get(key);
            v.setText(spanned);
            v.setOnClickListener(onClickListener);
        }
    }

    /**
     * get text has multiple color
     *
     * @param amount
     * @return
     */
    protected Spanned getAmountString(int amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<font color=#fcf67c>")
                .append(setComma(Integer.toString(amount))) // 금액
                .append("</font> <font color=#ffffff>&nbsp;")
                .append(context.getString(R.string.price_unit)); // 원
        return Html.fromHtml(sb.toString());
    }


    private boolean isValid(String response) {
        boolean result = true;
        if (response == null || response.isEmpty()) {
            result = false;
        }
        return result;
    }

    private String setComma(String str) {
        String returnValue = "";
        if (isValid(str)) {
            try {
                int value = Integer.parseInt(str);
                returnValue = new DecimalFormat("#,###").format(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }
}
