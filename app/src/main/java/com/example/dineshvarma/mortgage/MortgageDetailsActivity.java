package com.example.dineshvarma.mortgage;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class MortgageDetailsActivity extends AppCompatActivity {
    int n;
    double total, percent;
    String yy, repaymentMethod, year;
    boolean MActivity = false;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_details);
         v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        Intent i = getIntent();
        n = Integer.parseInt(i.getStringExtra("n"));
        total = Double.parseDouble(i.getStringExtra("total"));
        percent = Double.parseDouble(i.getStringExtra("percent"));
        yy = i.getStringExtra("yy");
        repaymentMethod = i.getStringExtra("repaymentMethod");
        year = i.getStringExtra("year");

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.lvExp);
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, n, total, percent, yy, repaymentMethod, year);

        listView.setAdapter(adapter);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                v.vibrate(50);
                return false;
            }
        });
    }

    public void goBack(View view) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
        Intent intent = new Intent(this,ResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("MActivity", MActivity);
        startActivity(intent);
    }
}
