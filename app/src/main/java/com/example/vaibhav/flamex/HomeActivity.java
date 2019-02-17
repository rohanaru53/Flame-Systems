package com.example.vaibhav.flamex;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by Deepanshu on 10/29/2015.
 */
public class HomeActivity extends Activity  {
    public static String filename = "userdatabase";
    SharedPreferences somedata;
    String datareturned = "";
    RelativeLayout r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        r1=(RelativeLayout)findViewById(R.id.layouts);
        r1.setClickable(false);
        try {
            somedata = getSharedPreferences(filename, 0);
            datareturned = somedata.getString("userid","Could't find Data");
        } catch (Exception e) {
        }
        if (datareturned.equals("Could't find Data")) {
            final Dialog dialog1 = new Dialog(HomeActivity.this);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // Include dialog.xml file
            dialog1.setContentView(R.layout.custom_dialog_signup);
            // set values for custom dialog components - text, image and
            // button
            dialog1.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog1.show();
            Button signup = (Button) dialog1.findViewById(R.id.signUp);
            final EditText userId = (EditText) dialog1.findViewById(R.id.userid);
            final EditText password = (EditText) dialog1.findViewById(R.id.password);
            signup.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog1.dismiss();
                    SharedPreferences.Editor editor = somedata.edit();
                    String userid = userId.getText().toString();
                    String Password = password.getText().toString();
                    if(!userid.isEmpty()&&(!Password.isEmpty())) {
                        editor.putString("userid", userid);
                        editor.putString("password", Password);
                        editor.commit();
                        Intent i = new Intent(HomeActivity.this, MainActivity.class);
                        i.putExtra("flag","0");
                        startActivity(i);
                    }
                    else{
                        Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Parameters!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();

                    }
                }
            });
            dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });

        } else {
            final Dialog dialog1 = new Dialog(HomeActivity.this);

            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // Include dialog.xml file
            dialog1.setContentView(R.layout.custom_dialog_login);
            dialog1.setCanceledOnTouchOutside(false);
            // set values for custom dialog components - text, image and
            // button
            dialog1.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog1.show();
            Button login = (Button) dialog1.findViewById(R.id.login);
            final EditText userId = (EditText) dialog1.findViewById(R.id.userid);
            final EditText password = (EditText) dialog1.findViewById(R.id.password);
            login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String userid = "", Password = "";
                    try {
                        somedata = getSharedPreferences(filename, 0);
                        userid = somedata.getString("userid", "Could't find Data");
                        Password = somedata.getString("password", "Could't find Data");
                    } catch (Exception e) {
                    }
                    if (userid.equals(userId.getText().toString()) && (Password.equals(password.getText().toString()))) {
                        dialog1.dismiss();
                        Intent i=new Intent(HomeActivity.this,MainActivity.class);
                        i.putExtra("flag","0");
                        startActivity(i);

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Parameters!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                }
            });
            dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });

        }
    }
}
