package com.example.make_it_easy;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    BarChart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        chart = view.findViewById(R.id.lc_chart1);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        for (int i=0;i<8;i++){
            float value= (float) (i*10.0);
            BarEntry barEntry=new BarEntry(i,value);
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet =new BarDataSet(barEntries,"Emp");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawIcons(false);
        chart.setData(new BarData(barDataSet));
        chart.animateY(5000);
        chart.getDescription().setText("Emp Chart");
        chart.getDescription().setTextColor(Color.BLUE);

        return view;
    }

}