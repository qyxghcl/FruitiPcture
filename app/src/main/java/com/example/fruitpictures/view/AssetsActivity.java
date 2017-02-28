package com.example.fruitpictures.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.fruitpictures.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class AssetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("我的资产");

        PieChart pieChart = (PieChart) findViewById(R.id.pie);

        ArrayList<PieEntry> yVlas = new ArrayList<>();
        yVlas.add(new PieEntry(22, "理财"));
        yVlas.add(new PieEntry(55, "基金"));
        yVlas.add(new PieEntry(33, "投资"));
        PieDataSet dataSet = new PieDataSet(yVlas, "我的资产");
        dataSet.setColors(Color.RED, Color.GREEN, Color.BLUE);

        PieData pieData = new PieData();
        pieData.setDataSet(dataSet);
        pieChart.setHoleRadius(20);
        pieChart.setTransparentCircleAlpha(20);
        pieChart.setData(pieData);
    }
}
