package com.nagel.loanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtCost, txtLoan, txtRate, txtPaym, txtYear, txtTerm;
    private Button btnAmortisation, btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAmortisation = findViewById(R.id.btnAmortisation);

        btnAmortisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Loan.getInstance().getPeriods() > 0) {            //issue 4.3
                    Intent intent = new Intent(this, PlanActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);
        disable(txtPaym);  // issue 4.14

    }

    public void onClear(View view) {
        txtCost.setText("");  //issue 4.4
        txtLoan.setText(""); //issue 4.5
        txtRate.setText(""); //issue 4.6
        txtPaym.setText(""); //issue 4.7
        txtYear.setText(""); //issue 4.8
        txtTerm.setText(""); //issue 4.9

        txtCost.requestFocus();

        Loan.getInstance().setPrincipal(1);
        Loan.getInstance().setInterestRate(1);
        Loan.getInstance().setPeriods(1);
    }

    public void onCalculate(View view) {
        double cost = 0;
        double loan;
        double rate;
        int yearInt;         // //issue 4.10
        int termInt;         // //issue 4.10
        String year = "0";  // issue 4.1
        String term = "0";  // issue 4.2
        try {
            String text = txtCost.getText().toString().trim();
            if (text.length() > 0) {
                cost = Double.parseDouble(text);
                if (cost < 0) throw new Exception();
            }
        } catch (Exception ex) {
            Toast.show();       //
            txtCost.requestFocus();
            return;
        }
        try {
            loan = Double.parseDouble(txtLoan.getText().toString().trim());
            if (loan < 0) throw new Exception();
        } catch (Exception ex) {
            Toast.show();
            txtLoan.requestFocus();
            return;
        }
        try {
            rate = Double.parseDouble(txtRate.getText().toString().trim());
            if (rate <= 0 && rate > 5) throw new Exception();
        } catch (Exception ex) {
            Toast.show();
            txtRate.requestFocus();
            return;
        }
        try {
            year = txtYear.getText().toString().trim();
            yearInt = Integer.parseInt(txtYear.getText().toString().trim()); ;
            if ( yearInt <= 0 && yearInt > 6) throw new Exception();  //issue 4.10
        } catch (Exception ex) {
            Toast.show();
            txtYear.requestFocus();
            return;
        }
        try {
            term = txtTerm.getText().toString().trim();
            termInt = Integer.parseInt(txtTerm.getText().toString().trim()); ;
            if (termInt <= 0 || termInt > 120) throw new Exception();   //issue 4.11
        } catch (Exception ex) {
            Toast.show();
            txtTerm.requestFocus();
            return;
        }
        Loan.getInstance().setPrincipal(loan + cost);
        Loan.getInstance().setInterestRate(rate / 100 / termInt);  //issue 4.12
        Loan.getInstance().setPeriods(yearInt * termInt);                // issue 4.13
        txtPaym.setText(String.format("%1.2f", Loan.getInstance().payment()));
    }

    public void onAmort(View view) {
        if (Loan.getInstance().getPeriods() > 0) {
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);
        }
    }

    private void disable(EditText view)  // issue 4.14
    {
        view.setKeyListener(null);
        view.setEnabled(false);
    }
}

