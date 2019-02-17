package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Deepanshu on 29-Oct-15.
 */
public class UpdateInventory extends Activity {
    database products = new database(this);
    ArrayList<String> productlist = new ArrayList<String>();
    ArrayAdapter<String> productAdapter;
    AutoCompleteTextView psearch;
    EditText quantity,price;
    String name;
    RelativeLayout r1;
    BootstrapButton save,reset;
    String flag="1";
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.updateinventory);
        quantity=(EditText)findViewById(R.id.quantity);
        price=(EditText)findViewById(R.id.price);
        save=(BootstrapButton)findViewById(R.id.save);
        reset=(BootstrapButton)findViewById(R.id.reset);
        psearch = (AutoCompleteTextView) findViewById(R.id.name);
        r1=(RelativeLayout)findViewById(R.id.updateinventory);
        onTapOutsideBehaviour(r1);
        products.Open();
        productlist = products.addingalldatainspinner();
        products.close();
        Collections.sort(productlist);
        productAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,productlist);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psearch.setThreshold(1);
        psearch.setAdapter(productAdapter);
        psearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                psearch.setEnabled(false);
                name = parent.getItemAtPosition(position).toString();
                products.Open();
                price.setText(products.getprice(name, products.getcategory(name)));
                price.setEnabled(false);
                products.close();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag="0";
                products.Open();
                String eprice, equantity;
                eprice = price.getText().toString();
                equantity = quantity.getText().toString();
                if (!eprice.isEmpty() && (!equantity.isEmpty()) && (!psearch.getText().toString().isEmpty())) {
                    if (products.updateinventory(name, products.getcategory(name), equantity, eprice)) {
                        Snackbar.make(findViewById(android.R.id.content), "Product Updated!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.BLACK)
                                .show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please enter correct Parameters!!", Snackbar.LENGTH_SHORT)
                            .setActionTextColor(Color.BLACK)
                            .show();
                }
                products.close();
            }


        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psearch.setEnabled(true);
                psearch.setText("");
                price.setText("");
                quantity.setText("");
            }
        });
    }
    private void onTapOutsideBehaviour(View view) {
        if (!(view instanceof EditText) || !(view instanceof Button)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(UpdateInventory.this);
                    quantity.clearFocus();
                    price.clearFocus();
                    psearch.clearFocus();
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
        Intent i = new Intent(UpdateInventory.this, MainActivity.class);
        i.putExtra("flag",flag);
        startActivity(i);
    }
}
