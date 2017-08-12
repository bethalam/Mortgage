package com.example.dineshvarma.mortgage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.NumberPicker;


public class NumberPickerFragment extends DialogFragment {
    NumberPicker picker;
    int n=1;
    public NumberPickerFragment() {

    }

    public static NumberPickerFragment newInstance(String title) {
        NumberPickerFragment frag = new NumberPickerFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

  @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.number_picker_layout, null);
      picker = view.findViewById(R.id.numberPicker);
      picker.setMinValue(1);
      picker.setMaxValue(30);
      picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
          @Override
          public void onValueChange(NumberPicker numberPicker, int i, int i1) {
              n=i1;
          }
      });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
     }

}