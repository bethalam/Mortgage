package com.example.dineshvarma.mortgage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    int n;
    double total,  percent;
    String yy, paymentType, year;
    Details details = new Details();
    private Context context;
    String[] monthText= {"month","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep","Oct", "Nov","Dec"};
    public ExpandableListAdapter(Context context, int n, double total, double percent, String yy, String paymentType, String year) {
        this.n = n;
        this.total = total;
        this.percent = percent;
        this.yy = yy;
        this.paymentType = paymentType;
        this.year = year;
        this.context = context;
        details.details(n, total, percent, yy, paymentType, year);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        int position;
        if (groupPosition==0){
            position= childPosititon;
        }else {
            position = 12*(groupPosition-1)+13-details.month+childPosititon;
        }
        return details.months[position];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        int position;
        if (groupPosition==0){
            position= childPosition-1;
        }else {
            position = (12*(groupPosition-1))+(12-details.month)+childPosition;
        }

        TextView month = convertView.findViewById(R. id.idmonth);
        TextView repayment = convertView.findViewById(R.id.idRepayment);
        TextView principal = convertView.findViewById(R.id.idPrincipal);
        TextView interest = convertView.findViewById(R.id.idIntrest);
        TextView remaining = convertView.findViewById(R.id.idRemaing);
        if (childPosition==0){
            month.setText("Month");
            repayment.setText("Repayment");
            principal.setText("Principal");
            interest.setText("Interest");
            remaining.setText("Remaining");

            return convertView;
        }else {
            month.setText(monthText[details.months[position]]);
            repayment.setText(new DecimalFormat("##.##").format(details.repayment[position]));
            principal.setText(new DecimalFormat("##.##").format(details.principal[position]));
            interest.setText(new DecimalFormat("##.##").format(details.intrest[position]));
            remaining.setText(new DecimalFormat("##.##").format(details.remaing[position]));

            return convertView;
        }
     }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition==0){
            return 14  -details.month;
        }else if (groupPosition==details.years.length-1){
            return  details.month;
        }else {
            return 13;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return details.years[groupPosition];
    }

    @Override
    public int getGroupCount() {
        return details.years.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView yearlyRepayment =  convertView.findViewById(R.id.tyrPayment);
        TextView yearlyInterest =  convertView.findViewById(R.id.tyIntrest);
        TextView year =  convertView.findViewById(R.id.idYear);


        yearlyRepayment.setText(new DecimalFormat("##.##").format(details.yearlyPayment[groupPosition]));
        yearlyInterest.setText(new DecimalFormat("##.##").format(details.yearlyInterest[groupPosition]));
        year.setText(new DecimalFormat("##.##").format(details.years[groupPosition]));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}