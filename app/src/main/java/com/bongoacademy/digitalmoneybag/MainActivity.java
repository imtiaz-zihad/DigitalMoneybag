package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView  tvFinalBlance,tvAddExpense,tvShowAllDataExpense,tvTotalExpense,
            tvAddIncome,tvShowAllDataIncome,tvTotalIncome;

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFinalBlance =findViewById(R.id.tvFinalBlance);
        tvAddExpense =findViewById(R.id.tvAddExpense);
        tvShowAllDataExpense =findViewById(R.id.tvShowAllDataExpense);
        tvTotalExpense =findViewById(R.id.tvTotalExpense);
        tvAddIncome =findViewById(R.id.tvAddIncome);
        tvShowAllDataIncome =findViewById(R.id.tvShowAllDataIncome);
        tvTotalIncome =findViewById(R.id.tvTotalIncome);
        dbHelper= new DatabaseHelper(this);



        tvAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.EXPENSE = true;
                startActivity(new Intent(MainActivity.this, AddData.class));

            }
        });

        tvAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData.EXPENSE = false;
                startActivity(new Intent(MainActivity.this, AddData.class));

            }
        });

        tvShowAllDataExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSE=true;
                startActivity(new Intent(MainActivity.this, ShowData.class));

            }
        });

        tvShowAllDataIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowData.EXPENSE=false;
                startActivity(new Intent(MainActivity.this, ShowData.class));

            }
        });



        upDateUI();





    } //Oncrette Finish ===================================================================

    public void upDateUI(){
        tvTotalExpense.setText(""+dbHelper.calculatedTotalExpense());
        tvTotalIncome.setText(""+dbHelper.calculatedTotalIncome());
        double balance = dbHelper.calculatedTotalIncome()- dbHelper.calculatedTotalExpense();
        tvFinalBlance.setText(" "+balance);

        PieChart pieChart =findViewById(R.id.chart);
        String tv = tvTotalIncome.getText().toString();
        String tv2 = tvTotalExpense.getText().toString();
        String tv3 = tvFinalBlance.getText().toString();


        ArrayList<PieEntry> pai =new ArrayList<>();
        /// pai.add(new PieEntry(80f,"Math"));
        /// pai.add(new PieEntry(50f,"Eng"));
        pai.add(new PieEntry(Float.parseFloat(tv),"Income"));
        pai.add(new PieEntry(Float.parseFloat(tv2),"Expense"));
        pai.add(new PieEntry(Float.parseFloat(tv3),"Balance"));



        PieDataSet pieDataSet = new PieDataSet(pai,"Sub");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        upDateUI();

    }

}