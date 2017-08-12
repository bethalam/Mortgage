package com.example.dineshvarma.mortgage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    double total, interest, percent;
    String repaymentMethod, startDate, year, myPreferences = "Prefs";
    int n;
    TextView mrTxt,iaTxt, raTxt, maTxt, mpTxt;
    double monthlyPayment, rpa, ia;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean MActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mrTxt = (TextView) findViewById(R.id.textView22);
        iaTxt = (TextView) findViewById(R.id.textView19);
        raTxt = (TextView) findViewById(R.id.textView17);
        maTxt = (TextView) findViewById(R.id.textView6);
        mpTxt = (TextView) findViewById(R.id.textView4);

        preferences = getSharedPreferences(myPreferences, MODE_PRIVATE);
        editor = preferences.edit();



            Intent i = getIntent();
            MActivity = i.getBooleanExtra("MActivity",false);
        if (MActivity==true){
            n = Integer.parseInt(i.getStringExtra("mortgageYears"));
            repaymentMethod = i.getStringExtra("repaymentMethod");
            startDate = i.getStringExtra("startDate");
            String ir = i.getStringExtra("interest");
            if (ir.length()<1){
                ir = "1";
            }
            total = Double.parseDouble(i.getStringExtra("total"));
            interest = Double.parseDouble(ir);
            year = i.getStringExtra("year");
        }else {
                n = preferences.getInt("n",30);
                repaymentMethod = preferences.getString("repaymentMethod",null);
                startDate = preferences.getString("startDate",null);
                total = Double.valueOf(preferences.getString("total", null));
                interest = Double.valueOf(preferences.getString("interest", null));
                year = preferences.getString("year", null);
            }
                percent = interest;
                interest /= 100;
                double monthlyRate = interest / 12;
                int termInMonths = n * 12;
                if (repaymentMethod.equals("Equal installments")) {
                    monthlyPayment =
                            (total * monthlyRate) /
                                    (1 - Math.pow(1 + monthlyRate, -termInMonths));
                    rpa = monthlyPayment * termInMonths;
                    ia = rpa - total;

                } else {
                    double principal = total / termInMonths;
                    double iAmount = total * monthlyRate;
                    monthlyPayment = principal + iAmount;
                    ia = (iAmount * (termInMonths + 1)) / 2;
                    rpa = total + ia;
                }
                raTxt.setText(new DecimalFormat("##.##").format(rpa));
                iaTxt.setText(new DecimalFormat("##.##").format(ia));
                mrTxt.setText(new DecimalFormat("##.##").format(monthlyPayment));
                maTxt.setText(new DecimalFormat("##.##").format(total));
                mpTxt.setText(n + " Years (" + n * 12 + " months) ");


        }

    public void back(View view) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void showDetails(View view) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
       editor.putInt("n", n);
        editor.putString("repaymentMethod", repaymentMethod);
        editor.putString("startDate", startDate);
        editor.putString("total",String.valueOf(total));
        editor.putString("interest", String.valueOf(percent));
        editor.putString("year",year);
        editor.commit();

        Intent intent = new Intent(this,MortgageDetailsActivity.class);
        intent.putExtra("n", String.valueOf(n));
        intent.putExtra("total",String.valueOf(total));
        intent.putExtra("percent", String.valueOf(percent));
        intent.putExtra("yy",startDate);
        intent.putExtra("repaymentMethod", repaymentMethod);
        intent.putExtra("year", year);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
         super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
