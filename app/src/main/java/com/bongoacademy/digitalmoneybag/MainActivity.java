package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    public static  boolean EXPENSE = true;


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
                EXPENSE = true;
                //startActivity(new Intent(MainActivity.this, AddData.class));
                showDialogBox();

            }
        });

        tvAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               EXPENSE = false;
               // startActivity(new Intent(MainActivity.this, AddData.class));
                showDialogBox();

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


    private void showDialogBox(){

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.activity_add_data, null);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);
        dbHelper =new DatabaseHelper(this);

        TextView tvTitle = mView.findViewById(R.id.tvTitle);
        EditText edAmount = mView.findViewById(R.id.edAmount);
        EditText edReason = mView.findViewById(R.id.edReason);
        Button button = mView.findViewById(R.id.button);



        if (EXPENSE==true) tvTitle.setText("Add Expense");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAmount = edAmount.getText().toString();
                String reason = edReason.getText().toString();

                String Name = edReason.getText().toString();
                if (Name.isEmpty()) {
                    edReason.setError("Enter Your Reason");

                }else {

                    double amount = Double.parseDouble(sAmount);

                    if (EXPENSE==true){

                        dbHelper.addexpense(amount,reason);
                        tvTitle.setText("Expense Added!!");

                    }else {
                        dbHelper.addIncome(amount,reason);
                        tvTitle.setText("Income Added!!");
                    }
                }
                upDateUI();


            }
        });




        mView.findViewById(R.id.dismiss).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }


    public void upDateUI(){
        tvTotalExpense.setText(""+dbHelper.calculatedTotalExpense());
        tvTotalIncome.setText(""+dbHelper.calculatedTotalIncome());
        double balance = dbHelper.calculatedTotalIncome()- dbHelper.calculatedTotalExpense();
        tvFinalBlance.setText(" "+balance);

        PieChart pieChart =findViewById(R.id.chart);
        String tv = tvTotalIncome.getText().toString();
        String tv2 = tvTotalExpense.getText().toString();
        String tv3 = tvFinalBlance.getText().toString();


        int color = 0xFFFFFFFF;
        int piecenterbackcolor = 0xFFFFA500;



        ArrayList<PieEntry> pai =new ArrayList<>();
        pieChart.setEntryLabelTextSize(16f);
      //  pieChart.setCenterTextColor();
        pieChart.setCenterText(tv3);
        pieChart.setCenterTextColor(color);
        pieChart.setCenterTextSize(27);
        pieChart.setDrawCenterText(true);
        pieChart.setHoleColor(piecenterbackcolor);


        /// pai.add(new PieEntry(80f,"Math"));
        /// pai.add(new PieEntry(50f,"Eng"));
        pai.add(new PieEntry(Float.parseFloat(tv),"Income"));
        pai.add(new PieEntry(Float.parseFloat(tv3),"Balance"));
        pai.add(new PieEntry(Float.parseFloat(tv2),"Expense"));



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