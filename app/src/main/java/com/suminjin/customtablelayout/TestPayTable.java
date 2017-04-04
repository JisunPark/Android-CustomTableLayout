package com.suminjin.customtablelayout;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jspark on 2017-07-04.
 */
public class TestPayTable extends BaseTable {
    private static final int COLUMN_COUNT = 3;
    private ArrayList<String> tableTagList = new ArrayList<>();
    private String[] shopIds = new String[]{"AAA", "bbb", "CCC", "ddd"};

    public TestPayTable(Context context) {
        this(context, null);
    }

    public TestPayTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initReportAmountDataTable(context);
    }

    public TestPayTable(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void initReportAmountDataTable(final Context context) {
        BaseTable table = this;

        table.initTable(new int[]{R.string.date, R.string.shop, R.string.amount},
                new int[]{R.dimen.table_cell_width_1, R.dimen.table_cell_width_1, R.dimen.table_cell_width_1},
                new int[]{Gravity.CENTER_VERTICAL, Gravity.CENTER, Gravity.CENTER_VERTICAL | Gravity.RIGHT});

        tableTagList.add("2017-4-1");
        tableTagList.add("2017-4-2");
        tableTagList.add("2017-4-3");
        tableTagList.add("2017-4-4");

        // 데이타 갯수만큼 row 추가.
        // 각 cell은 row text와 column index를 조합한 tag를 가진다.
        for (int i = 0; i < tableTagList.size(); i++) {
            String tag = tableTagList.get(i);
            table.addRow(tag);
        }

        // set 1st index data
        for (int i = 0; i < tableTagList.size(); i++) {
            String tag = tableTagList.get(i);
            table.setData(tag, 0, tag, null);
        }

        // set 2nd index data
        for (int i = 0; i < tableTagList.size(); i++) {
            final String s = tableTagList.get(i);
            table.setData(s, 1, shopIds[i], new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });
        }

        // set 3rd index data
        for (int i = 0; i < tableTagList.size(); i++) {
            final String s = tableTagList.get(i);
            table.setData(s, 2, getAmountString(10000), new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
