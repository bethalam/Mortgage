package com.example.dineshvarma.mortgage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , BottomSheetListener {
    TextView dateView, mpTxt, repaymentTxt;
    Calendar cal;
    BottomSheet.Builder bottomSheet;
    String repaymentMethod, myPreferences = "MyPrefs";
    NumberPickerFragment fragment;
    EditText editText2, interestTxt;
    ImageView warning;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int n=30, year;
    boolean MActivity =true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateView = (TextView) findViewById(R.id.textView14);
        cal = new java.util.GregorianCalendar();

        bottomSheet = new BottomSheet.Builder(this);
        mpTxt = (TextView) findViewById(R.id.textView12);
        editText2 = (EditText) findViewById(R.id.editText2);
        warning = (ImageView) findViewById(R.id.warning);
        repaymentTxt = (TextView) findViewById(R.id.textView10);
        interestTxt = (EditText) findViewById(R.id.editText3);

        preferences = getSharedPreferences(myPreferences, MODE_PRIVATE);
        editor = preferences.edit();

        String total = preferences.getString("total", null),
                rm = preferences.getString("repaymentMethod", null),
                mp = preferences.getString("mortgagePeriod", null),
                sd = preferences.getString("startDate", null),
                in = preferences.getString("interest", null);
        n = preferences.getInt("n",30);
        year = preferences.getInt("year", 2017);
        if (total != null) {
            editText2.setText(total);
            repaymentTxt.setText(rm);
            mpTxt.setText(mp);
            dateView.setText(sd);
        }
        if (in != null) {
            interestTxt.setText(in);
        }

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                warning.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editText2.getText().length() > 0) {
                    warning.setVisibility(View.INVISIBLE);
                } else {
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void EqualInstallment(View view) {
        bottomSheet.setSheet(R.menu.list_sheet)
                .setTitle("Repayment method")
                .setListener(this)
                .show();
    }

    public void mortgagePeriod(View view) {
        FragmentManager fm = getSupportFragmentManager();
        fragment = NumberPickerFragment.newInstance("Mortgage period");
        fragment.show(fm, "number_picker_layout");
    }

    public void startDate(View view) {
        DialogFragment dialog = createDateDialog();
        dialog.show(getSupportFragmentManager(), "MainActivity");
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        dateView.setText(DateFormat.getDateFormat(this).format(cal.getTime()));
    }

    private DialogFragment createDateDialog() {
        BottomSheetPickerDialog.Builder builder = null;
        Calendar now = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        builder = new DatePickerDialog.Builder(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        DatePickerDialog.Builder dateDialogBuilder = (DatePickerDialog.Builder) builder;
        dateDialogBuilder.setMaxDate(max)
                .setYearRange(1970, 2052);
        return builder.build();
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {
    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        String myVar = menuItem.getTitle().toString();
        repaymentTxt.setText(myVar);
        repaymentMethod = myVar;
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {
    }

    public void okButtonPressed(View view) {
        n = fragment.n;
        String text = n + "years(" + n * 12 + " months)";
        mpTxt.setText(text);
        fragment.dismiss();
    }

    public void Calculate(View view) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(50);
        if (editText2.getText().length() > 0) {
            editor.putString("total", editText2.getText().toString());
            editor.putString("repaymentMethod", repaymentTxt.getText().toString());
            editor.putString("mortgagePeriod", mpTxt.getText().toString());
            editor.putString("startDate", dateView.getText().toString());
            editor.putString("interest", interestTxt.getText().toString());
            editor.putInt("n",n);
            editor.putInt("year",year);
            editor.commit();
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("total", editText2.getText().toString());
            intent.putExtra("repaymentMethod", repaymentTxt.getText().toString());
            intent.putExtra("mortgageYears", String.valueOf(n));
            intent.putExtra("interest", interestTxt.getText().toString());
            intent.putExtra("startDate", dateView.getText().toString());
            intent.putExtra("year",String.valueOf(year));
            intent.putExtra("MActivity",MActivity);
            startActivity(intent);
        } else {
            warning.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}

