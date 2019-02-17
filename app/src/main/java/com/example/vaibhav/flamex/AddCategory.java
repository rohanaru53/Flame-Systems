package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class AddCategory extends Activity {
    CategoriesDatabase categories = new CategoriesDatabase(this);
    EditText cname;
    BootstrapButton confirm, reset;
    RelativeLayout r1;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        setContentView(R.layout.addcategory);
        cname = (EditText) findViewById(R.id.categoryname);
        confirm = (BootstrapButton) findViewById(R.id.confirm);
        r1=(RelativeLayout)findViewById(R.id.addcategory);
        reset = (BootstrapButton) findViewById(R.id.resetCategory);
        onTapOutsideBehaviour(r1);
        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cname.setText("");
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String categoryname = cname.getText().toString();
                if ((categoryname.length()) >= 1) {
                    categories.Open();
                    if (categories.checknameexists(categoryname)) {
                        Snackbar.make(findViewById(android.R.id.content), "Category Already Existed!!", Snackbar.LENGTH_SHORT)
                                                            .setActionTextColor(Color.RED)
                                .show();

                    } else {
                        categories.CreateEntry(categoryname);
                        Snackbar.make(findViewById(android.R.id.content), "Category Name Inserted!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.BLACK)
                                .show();
                        cname.setText("");

                    }
                    categories.close();
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Category Name!!", Snackbar.LENGTH_SHORT)
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
                    hideSoftKeyboard(AddCategory.this);
                    cname.clearFocus();
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
        Intent i = new Intent(AddCategory.this, MainActivity.class);
        i.putExtra("flag","1");
        startActivity(i);

    }
}
