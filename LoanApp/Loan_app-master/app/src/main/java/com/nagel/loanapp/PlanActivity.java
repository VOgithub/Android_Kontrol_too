package com.nagel.loanapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private List <items> list = new ArrayList<>();  //?
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        items.add(getResources().getString(R.string.per));  // issue 3.2
        items.add(getResources().getString(R.string.intr));  //issue 3.3
        items.add(getResources().getString(R.string.rep));  //issue 3.4
        items.add(getResources().getString(R.string.outs)); //issue 3.5
        for (int n = 1; n <= Loan.getInstance().getPeriods(); ++n)   // issue 3.1   not m!!  - must be n

        {
            items.add("" + n);
            items.add(String.format("%1.2f", Loan.getInstance().interest(n)));
            items.add(String.format("%1.2f", Loan.getInstance().repayment(n)));
            items.add(String.format("%1.2f", Math.abs(Loan.getInstance().outstanding(n))));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GridView grid = findViewById(R.id.grid);
        grid.show(adapter);
    }






}
