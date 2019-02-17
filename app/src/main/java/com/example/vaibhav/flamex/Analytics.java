package com.example.vaibhav.flamex;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Deepanshu on 11/1/2015.
 */
public class Analytics extends Activity{
    EditText estartdate,eenddate;
    String startdate="",enddate="";
    database products = new database(this);
    ArrayList<String> productlist = new ArrayList<String>();
    String name="";
    Spinner product;
    ArrayAdapter<String> productAdapter;
    Report report=new Report();
    BootstrapButton generate;
    BootstrapButton reset;
    billdatabase bill =new billdatabase(this);
    ArrayList<Integer> yaxis=new ArrayList<>();
    ArrayList<String> xaxis=new ArrayList<>();
    BarChart chart;

    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setContentView(R.layout.analytics);
        estartdate = (EditText) findViewById(R.id.startdate);
        eenddate = (EditText) findViewById(R.id.enddate);
        generate=(BootstrapButton)findViewById(R.id.generate);
        reset=(BootstrapButton)findViewById(R.id.resetanalytics);
        product=(Spinner)findViewById(R.id.products);
        products.Open();
        productlist = products.addingalldatainspinner();
        products.close();
        estartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Analytics.this, startingdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Analytics.this, endingdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        productAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, productlist);
        productAdapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        product.setAdapter(productAdapter);
        product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.isEmpty() && (!startdate.isEmpty() && (!enddate.isEmpty()))) {
                    ArrayList<billdatabaselist> billlist = new ArrayList<billdatabaselist>();
                    bill.Open();
                    billlist = bill.addingnamedata(name);
                    if(billlist.size()>0) {
                        yaxis=new ArrayList<>();
                        report.select(startdate, enddate, billlist);
                        yaxis = report.getyAxis();
                        xaxis = report.getxAxis();
                        final Dialog dialog1 = new Dialog(Analytics.this);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(R.layout.graph);
                        dialog1.setCanceledOnTouchOutside(true);
                        dialog1.show();
                        chart = (BarChart)dialog1.findViewById(R.id.chart);
                        BootstrapButton savetogallary=(BootstrapButton)dialog1.findViewById(R.id.savetogallary);
                        adddata();
                        savetogallary.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String savename = "Product:" + name + "Chart" + " from " + startdate + " to " + enddate;
                                chart.saveToGallery(savename,85);
                                dialog1.dismiss();
                            }
                        });
                        dialog1.setOnKeyListener(new Dialog.OnKeyListener() {

                            @Override
                            public boolean onKey(DialogInterface arg0, int keyCode,
                                                 KeyEvent event) {
                                // TODO Auto-generated method stub
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    dialog1.dismiss();
                                    xaxis.clear();
                                    yaxis.clear();
                                }
                                return true;
                            }
                        });
                        dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog1.dismiss();
                                xaxis.clear();
                                yaxis.clear();
                            }
                        });
                    }
                    bill.close();
                    estartdate.setText("");
                    eenddate.setText("");
                    name.equals("");
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Parameters!!", Snackbar.LENGTH_SHORT)
                            .setActionTextColor(Color.RED)
                            .show();

                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estartdate.setText("");
                eenddate.setText("");
                name.equals("");
            }
        });
    }
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startingdate = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
            startdate=sdf.format(myCalendar.getTime());
            estartdate.setText(startdate);
        }
    };
    DatePickerDialog.OnDateSetListener endingdate = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
            enddate=sdf.format(myCalendar.getTime());
            eenddate.setText(enddate);
        }
    };
    private void adddata() {

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for(int i=0;i<yaxis.size();i++){
            valueSet1.add(new BarEntry(yaxis.get(i).intValue(),i));
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1,name);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(xaxis,barDataSet1);
        chart.setData(data);
        chart.setDescription("Product: " + name + "Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
       }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Analytics.this, MainActivity.class);
        i.putExtra("flag","1");
        startActivity(i);
    }
}
