package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddData extends AppCompatActivity {
    TextView tvTitle;
    EditText edAmount,edReason;
    Button button;
    DatabaseHelper dbHelper;

    public static  boolean EXPENSE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        tvTitle = findViewById(R.id.tvTitle);
        edAmount = findViewById(R.id.edAmount);
        edReason = findViewById(R.id.edReason);
        button = findViewById(R.id.button);
        dbHelper =new DatabaseHelper(this);



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


            }
        });


    }

    //===================

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}