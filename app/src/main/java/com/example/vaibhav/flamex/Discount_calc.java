package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Discount_calc extends Activity implements View.OnClickListener{

    Button one, two, three, four, five, six, seven, eight, nine, zero,decimal, absolute, percentage, cancel, ok;
    EditText disp;
    int flag=2;
    String result;
    Bundle pass2;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discount_entry);
        pass2=getIntent().getExtras();
        position=pass2.getInt("position");

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        decimal=(Button)findViewById(R.id.decimal);
        absolute=(Button)findViewById(R.id.absolute);
        percentage=(Button)findViewById(R.id.percentage);

        cancel = (Button) findViewById(R.id.cancel);
        ok=(Button)findViewById(R.id.ok);

        disp = (EditText) findViewById(R.id.display);


        try{
            one.setOnClickListener(this);

            two.setOnClickListener(this);

            three.setOnClickListener(this);

            four.setOnClickListener(this);

            five.setOnClickListener(this);

            six.setOnClickListener(this);

            seven.setOnClickListener(this);

            eight.setOnClickListener(this);

            nine.setOnClickListener(this);

            zero.setOnClickListener(this);

            cancel.setOnClickListener(this);

            decimal.setOnClickListener(this);

            percentage.setOnClickListener(this);

            absolute.setOnClickListener(this);

            ok.setOnClickListener(this);

        }
        catch(Exception e){

        }
    }

    @Override
    public void onClick(View arg0) {
        Editable str =  disp.getText();
        switch(arg0.getId()){
            case R.id.one:

                str = str.append(two.getText());
                disp.setText(str);
                break;
            case R.id.two:

                str = str.append(two.getText());
                disp.setText(str);
                break;
            case R.id.three:

                str = str.append(three.getText());
                disp.setText(str);
                break;
            case R.id.four:

                str = str.append(four.getText());
                disp.setText(str);
                break;
            case R.id.five:

                str = str.append(five.getText());
                disp.setText(str);
                break;
            case R.id.six:

                str = str.append(six.getText());
                disp.setText(str);
                break;
            case R.id.seven:

                str = str.append(seven.getText());
                disp.setText(str);
                break;
            case R.id.eight:

                str = str.append(eight.getText());
                disp.setText(str);

                break;
            case R.id.nine:

                str = str.append(nine.getText());
                disp.setText(str);

                break;
            case R.id.zero:

                str = str.append(zero.getText());
                disp.setText(str);

                break;
            case R.id.decimal:

                str = str.append(decimal.getText());
                disp.setText(str);

                break;
            case R.id.percentage:

                flag=1;
                str.clear();
                disp.setText("");
                disp.setHint("Enter Discount %");

                break;
            case R.id.absolute:

                flag=2;
                str.clear();
                disp.setText("");
                disp.setHint("Enter Discount Amount");

                break;
            case R.id.ok:
                if (str.length()!=0) {
                    result=disp.getText().toString();
                    Intent returnIntent = new Intent();
                    Bundle pass3=new Bundle();
                    pass3.putInt("flag",flag);
                    pass3.putString("result", result);
                    pass3.putInt("position",position);

                    returnIntent.putExtras(pass3);

                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
                else {
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
                break;
            case R.id.cancel:

                str.clear();
                disp.setText("");

                if (flag==1)
                    disp.setHint("Enter Discount %");
                else
                    disp.setHint("Enter Discount Amount");

                break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}



