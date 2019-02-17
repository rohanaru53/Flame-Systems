package com.example.vaibhav.flamex;

import java.util.ArrayList;


public class Report{
    ArrayList<String> xAxis = new ArrayList<String>();
    ArrayList<Integer> yAxis = new ArrayList<Integer>();
    public static int IntegerDate(String dateTime)
    {
        String date[] = dateTime.split(" ");
        String datesplit[] = date[0].split("-");
        int dateInteger = Integer.parseInt(datesplit[0] + datesplit[1] + datesplit[2]);
        return (dateInteger);
    }
    public void select(String InitialTime, String FinalTime,ArrayList<billdatabaselist> bill)
    {       int i=0,quant =0;
            String previousDate = "ABCD";
            for(int j=bill.size()-1;j>=0;j--) {
                int quantity = Integer.parseInt(bill.get(j).quantity);
                String time = bill.get(j).timestramp;
                String date[] = time.split(" ");
                int dateInInteger = IntegerDate(date[0]);
                if ((dateInInteger >= IntegerDate(InitialTime)) && (dateInInteger <= IntegerDate(FinalTime))) {
                    if (previousDate.equals(date[0])) {
                        quant = yAxis.get(i) + quantity;
                        yAxis.remove(i);
                        yAxis.add(i, quant);
                    } else {
                        xAxis.add(i, date[0]);
                        yAxis.add(i, quantity);
                        previousDate = date[0];
                    }
                }
            }
    }
    public ArrayList<String> getxAxis() {
        return xAxis;
    }
    public ArrayList<Integer> getyAxis() {
        return yAxis;
    }

}
