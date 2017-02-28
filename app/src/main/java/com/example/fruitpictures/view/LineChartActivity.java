package com.example.fruitpictures.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fruitpictures.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LineChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        TextView title = (TextView) findViewById(R.id.title);
       // title.setText("收益");
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);

        LineData lineData = new LineData();
        List<Entry> yVals = new ArrayList<>();
        Random random = new Random();
        for (int i = 1998; i < 2017; i++) {
            yVals.add(new Entry(i, random.nextInt(300)));
        }

        LineDataSet lineDataSet = new LineDataSet(yVals, "收益率");
        lineData.addDataSet(lineDataSet);
        lineDataSet.setDrawCircles(false);

        //设置值格式化器
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return String.format("%.1f", value);
            }
        });
        //设置值格式化器
/*        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return null;
            }
        });*/

        lineChart.setData(lineData);
        lineChart.getAxisLeft().setAxisMinimum(0);
    }
}
