package com.example.administrator.access_school_client.Fragments.Fragmentscharts.subs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.access_school_client.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Random;
/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/1/24 12:28.
 */

/**
 * A fragment containing a combo line/column chart view.
 */
public class FragmentMtUsed2 extends Fragment {

    private BarChart barChart;
    private BarData mBarData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_barchart, container, false);
        barChart = rootView.findViewById(R.id.barChart1);

        //getBarData(X轴坐标个数, X所对应的值也就是Y值)
        mBarData = getBarData(6, 20);
        showBarChart(barChart, mBarData);

        return rootView;
    }
    /**
     *用来处理视图的方法
     */
    private void showBarChart(BarChart barChart, BarData barData) {
        barChart.setData(barData); // 设置数据
    }

    /**
     *用来处理数据的方法
     */
    private BarData getBarData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xValues.add("会议室"+(i+1));
        }

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
//            float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;
//            float value = ( 20 - new Random().nextInt(30));//带负数
            float value =  new Random().nextInt(30);
            yValues.add(new BarEntry(value, i));
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "collection");

        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSet);

        return barData;
    }

}
