package com.example.dineshvarma.mortgage;


public class Details {
    int month , year , n  ;
    double total, percent , princepal;
    int[] years;
    double[] yearlyPayment, yearlyInterest;
    double totalPayment =0, totalInterest =0;
    int[] months;
    double[] repayment;
    double[] intrest;
    double[] remaing;
    double[] principal;
    String paymentType, yy;

    public void details(int n, double total, double percent, String yy, String paymentType, String yyyy) {
        this.n = n;
        this.total = total;
        this.percent = percent;
        this.yy = yy;
        this.paymentType = paymentType;
        year = Integer.parseInt(yyyy);

        months =  new int[n*12];
        repayment =  new double[n*12];
        intrest =  new double[n*12];
        remaing =  new double[n*12];
        principal = new double[n*12];
        years =new int[n+1];
        yearlyPayment = new  double[n+1];
        yearlyInterest = new  double[n+1];
        month = Integer.parseInt(yy.substring(yy.length()-5, yy.length()-3));
        int m = month;
        if (paymentType.equals("Equal installments")) {
           double rpayment =  (total * (percent/1200)) /
                   (1 - Math.pow(1 + (percent/1200), -(n*12)));
            for (int i=0; i<n*12;i++){
                intrest[i] = total * percent / 1200;
                repayment[i]=rpayment;
                principal[i] = rpayment-intrest[i];
                total = total-principal[i];
                remaing[i] = total;
                months[i] = m;
                m++;
                if (m>12){
                    m -=12;
                }

            }
        } else {
            princepal = total / (n * 12);
            for (int i = 0; i < n * 12; i++) {
                intrest[i] = total * percent / 1200;
                repayment[i] = princepal + intrest[i];
                total = total - princepal;
                principal[i] = princepal;
                remaing[i] = total;
                months[i] = m;
                m++;
                if (m>12){
                    m -=12;
                }
            }
        }
        int a=0;
        if (month==1){
            n=n-1;
        }
        int cMonth = month;
        for (int i=0; i<=n;i++){
            years[i] = (year+i);
            for (int j =cMonth; j<=12;j++ ){
                if (a<repayment.length) {
                    totalPayment += repayment[a];
                    totalInterest += intrest[a];
                    a++;
                }
            }
            cMonth =1;
            yearlyPayment[i] = totalPayment;
            yearlyInterest[i] = totalInterest;
            totalPayment = 0;
            totalInterest = 0;

        }
    }
}


