package com.example.fruitpictures.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fruitpictures.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        TextView title = (TextView) findViewById(R.id.title);
        // title.setText("我的收益");

        BarChart barChart = (BarChart) findViewById(R.id.Bar_Chart);

        BarData data = new BarData();
        List<BarEntry> yVlas = new ArrayList<>();
        Random random = new Random();
        for (int i = 1998; i < 2017; i++) {
            yVlas.add(new BarEntry(i, random.nextInt(300)));
        }
        BarDataSet dataSet = new BarDataSet(yVlas, "我的收益");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        data.addDataSet(dataSet);
        barChart.animateXY(2000, 1600);
        barChart.setData(data);

    }
}
