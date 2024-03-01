package com.bongoacademy.digitalmoneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class ShowData extends AppCompatActivity {

    ListView listView;
    TextView tvTitle;

    DatabaseHelper dbHelper;

    ArrayList<HashMap<String,String>> arrayList ;
    HashMap<String,String> hashMap;

    public static boolean EXPENSE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        listView = findViewById(R.id.listview);
        tvTitle = findViewById(R.id.tvTitle);
        dbHelper = new DatabaseHelper(this);

        if (EXPENSE==true) tvTitle.setText("Showing All Expenses");

        else tvTitle.setText("Showing All Income");

        loadData();


    }

    public void loadData(){
        Cursor cursor =null;

        if (EXPENSE==true)cursor=dbHelper.getAllExpenses();

        else cursor=dbHelper.getAllIncome();


        if (cursor!=null && cursor.getCount()>0){

            arrayList = new ArrayList<>();

            while (cursor.moveToNext()){

                int id = cursor.getInt(0);
                double amount = cursor.getDouble(1);
                String reason = cursor.getString(2);
                String time = cursor.getString(3);

                hashMap = new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("amount",""+amount);
                hashMap.put("reason",""+reason);
                hashMap.put("time",""+time);
                arrayList.add(hashMap);

            }
            listView.setAdapter(new MyAdapter());





        }else {
            tvTitle.append("NO Data Found");
        }
    }

    public class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
           View myView = inflater.inflate(R.layout.design,parent,false);

           TextView tvReason = myView.findViewById(R.id.tvReason);
            TextView tvAmount = myView.findViewById(R.id.tvAmount);
            TextView tvDelete= myView.findViewById(R.id.tvDelete);
            TextView tvtime= myView.findViewById(R.id.tvtime);
            TextView tvupdate= myView.findViewById(R.id.tvUpdate);

            ImageView image = myView.findViewById(R.id.logo);

            hashMap= arrayList.get(position);

            String id = hashMap.get("id");
            String amount = hashMap.get("amount");
            String reason = hashMap.get("reason");
            String time = hashMap.get("time");

            tvReason.setText(reason);
            tvAmount.setText(amount);
            tvtime.setText(time);


            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (EXPENSE==true) dbHelper.deleteExpense(id);
                    else dbHelper.deleteIncome(id);
                    loadData();


                }
            });

            tvupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (EXPENSE==true) dbHelper.updateEx(id, Double.valueOf(amount));
                    else dbHelper.updateIncome(id, Double.valueOf(amount));



                }
            });

            if (EXPENSE==true)image.setImageResource(R.drawable.expense);

            else image.setImageResource(R.drawable.income);





            return myView;
        }
    }
}