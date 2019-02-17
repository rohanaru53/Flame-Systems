package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.List;

public class Addproducts extends Activity {
    String pname="", cname="", weight="", unit="", quantity="", price="";
    EditText epname, eweight, equantity, eprice,barcodedata;
    BootstrapButton  barcodebutton;
    BootstrapButton save,reset;
    Spinner Category, units;
    String barcode = " ";
    database products = new database(this);
    ArrayAdapter<String> categoryAdapter;
    RelativeLayout r1;
    ArrayList<String> categorylist = new ArrayList<String>();
    CategoriesDatabase categories = new CategoriesDatabase(this);


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        categories.Open();
        categorylist = categories.addingalldatainspinner();
        categories.close();
        setContentView(R.layout.addproducts);
        barcodedata = (EditText) findViewById(R.id.barcodedata);
        epname = (EditText) findViewById(R.id.name);
        r1 = (RelativeLayout) findViewById(R.id.addproducts);
        eweight = (EditText) findViewById(R.id.weight);
        equantity = (EditText) findViewById(R.id.quantity);
        eprice = (EditText) findViewById(R.id.price);
        Category = (Spinner) findViewById(R.id.categories);
        units = (Spinner) findViewById(R.id.units);
        save = (BootstrapButton) findViewById(R.id.save);
        reset = (BootstrapButton) findViewById(R.id.reset);
        barcodebutton = (BootstrapButton) findViewById(R.id.barcode);
        barcodebutton.setTextColor(getResources().getColor(R.color.Black));
        onTapOutsideBehaviour(r1);
        barcodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                pname = epname.getText().toString();
                weight = eweight.getText().toString();
                quantity = equantity.getText().toString();
                price = eprice.getText().toString();
                b.putString("pname", pname);
                b.putString("cname", cname);
                b.putString("weight", weight);
                b.putString("unit", unit);
                b.putString("price", price);
                b.putString("quantity", quantity);
                Intent intent = new Intent(Addproducts.this, BarcodeScanner.class);
                intent.putExtras(b);
                startActivityForResult(intent, 2);
            }
        });
        barcodedata.setText(barcode);
        epname.setText(pname);
        eweight.setText(weight);
        equantity.setText(quantity);
        eprice.setText(price);
        barcodedata.setEnabled(false);

        List<String> unitslist = new ArrayList<String>();
        unitslist.add("mg");
        unitslist.add("g");
        unitslist.add("kg");
        unitslist.add("ml");
        unitslist.add("l");
        ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, unitslist);
        unitsAdapter
                .setDropDownViewResource(android.R.layout.simple_list_item_checked);
        units.setAdapter(unitsAdapter);
        categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categorylist);
        categoryAdapter
                .setDropDownViewResource(android.R.layout.simple_list_item_checked);
        Category.setAdapter(categoryAdapter);
        Category.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                cname = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        units.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                unit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                epname.setText("");
                eweight.setText("");
                equantity.setText("");
                eprice.setText("");
                barcodedata.setText("");
                barcodedata.setEnabled(true);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pname = epname.getText().toString();
                weight = eweight.getText().toString();
                quantity = equantity.getText().toString();
                price = eprice.getText().toString();
                // TODO Auto-generated method stub
                if ((pname.length() >= 1) && (cname.length() > 1) && (quantity.length() >= 1) && (price.length() >= 1)) {
                    products.Open();
                    if (products.checknameexists(pname, cname, weight, unit)) {
                        Snackbar.make(findViewById(android.R.id.content), "Product Already Existed!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    } else {
                        products.CreateEntry(pname, cname, weight, barcode, unit, quantity, price);
                        Snackbar.make(findViewById(android.R.id.content), "Product Inserted!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.BLACK)
                                .show();
                        epname.setText("");
                        eweight.setText("");
                        equantity.setText("");
                        eprice.setText("");
                        barcodedata.setText("");
                        barcodedata.setEnabled(true);
                    }
                    products.close();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Parameters!!", Snackbar.LENGTH_SHORT)
                            .setActionTextColor(Color.RED)
                            .show();
                }
            }
        });
    }
    private void onTapOutsideBehaviour(View view) {
        if (!(view instanceof EditText) || !(view instanceof Button)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Addproducts.this);
                    barcodedata.clearFocus();
                    epname.clearFocus();
                    eweight.clearFocus();
                    equantity.clearFocus();
                    eprice.clearFocus();
                    return false;
                }

            });
        }
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Addproducts.this, MainActivity.class);

        i.putExtra("flag","1");

        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Bundle b = data.getExtras();
            pname=b.getString("pname");
            cname= b.getString("cname");
            weight=b.getString("weight");
            unit=b.getString("unit");
            price=b.getString("price");
            quantity=b.getString("quantity");
            barcode=b.getString("barcode");


        }
    }
}
