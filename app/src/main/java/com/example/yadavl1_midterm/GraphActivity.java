package com.example.yadavl1_midterm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Get the grade distribution percentages from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double[] ratios = extras.getDoubleArray("ratios");

            if (ratios != null) { // Check for null
                // Convert the percentages to the required format for BarEntry
                ArrayList<BarEntry> entries = new ArrayList<>();
                for (int i = 0; i < ratios.length; i++) {
                    entries.add(new BarEntry(i, (float) ratios[i])); // Add grade distribution percentage
                }

                // Get reference to BarChart view
                barChart = findViewById(R.id.barChart);

                // Create dataset for the bar graph
                BarDataSet barDataSet = new BarDataSet(entries, "Grade Distribution");

                // Define colors for each bar
                int[] colors = new int[]{ContextCompat.getColor(this, R.color.red),
                        ContextCompat.getColor(this, R.color.teal_700),
                        ContextCompat.getColor(this, R.color.pink),
                        ContextCompat.getColor(this, R.color.purple_500),
                        ContextCompat.getColor(this, R.color.yellow)};
                barDataSet.setColors(colors); // Set bar colors

                // Create BarData object and set dataset to it
                BarData barData = new BarData(barDataSet);
                barData.setBarWidth(0.5f); // Set custom bar width

                // Configure X-axis labels
                final String[] labels = new String[]{"A", "B", "C", "D", "F"};
                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setGranularity(1f);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);

                // Configure Y-axis
                YAxis yAxisRight = barChart.getAxisRight();
                yAxisRight.setEnabled(false);

                // Set data to the bar chart
                barChart.setData(barData);
                barChart.getDescription().setEnabled(false);
                barChart.setFitBars(true);
                barChart.invalidate();
            }
        }
    }
}
