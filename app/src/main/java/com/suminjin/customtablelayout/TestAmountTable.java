package com.suminjin.customtablelayout;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jspark on 2017-07-04.
 */
public class TestAmountTable extends BaseTable {
    private ArrayList<String> tableTagList = new ArrayList<>();

    public TestAmountTable(Context context) {
        this(context, null);
    }

    public TestAmountTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initReportAmountDataTable(context);
    }

    public TestAmountTable(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void initReportAmountDataTable(final Context context) {
        BaseTable table = this;

        table.initTable(new int[]{R.string.category, R.string.amount},
                new int[]{R.dimen.table_cell_width_1, R.dimen.table_cell_width_2},
                new int[]{Gravity.CENTER_VERTICAL, Gravity.CENTER_VERTICAL | Gravity.RIGHT});

        tableTagList.add(context.getString(R.string.sale));
        tableTagList.add(context.getString(R.string.cancel));
        tableTagList.add(context.getString(R.string.commission));

        for (int i = 0; i < tableTagList.size(); i++) {
            String s = tableTagList.get(i);
            table.addRow(s);
        }

        // set category name
        for (int i = 0; i < tableTagList.size(); i++) {
            String s = tableTagList.get(i);
            table.setData(s, 0, s, null);
        }

        // FIXME : test
        for (int i = 0; i < tableTagList.size(); i++) {
            final String s = tableTagList.get(i);
            table.setData(s, 1, getAmountString(0), new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public Spanned getAmountString(int amount) {
        StringBuilder sb = new StringBuilder();
        sb.append("<font color=#fcf67c>")
                .append(Integer.toString(amount)) // 금액
                .append("</font> <font color=#ffffff>&nbsp;")
                .append(context.getString(R.string.price_unit)); // 원
        return Html.fromHtml(sb.toString());
    }

}
