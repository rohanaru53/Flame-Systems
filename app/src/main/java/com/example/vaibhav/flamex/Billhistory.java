package com.example.vaibhav.flamex;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Deepanshu on 11/4/2015.
 */
public class Billhistory extends Activity {
    billdatabase billdata=new billdatabase(this);
    ListView list,timebill;
    ArrayList<String> time;
    ArrayAdapter<String> billladapter;
    ArrayList<billdatabaselist> bill;
    BillAdapter specficbilladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billhistory);
        list=(ListView)findViewById(R.id.bill);
        time=new ArrayList<>();
        billdata.Open();
        time=billdata.displayingtime();
        billdata.close();
        Set<String> s = new LinkedHashSet<>(time);
        time.clear();
        time.addAll(s);
        billladapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,time);
        list.setAdapter(billladapter);
        Collections.sort(time);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                final Dialog dialog1 = new Dialog(Billhistory.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.custom_dailog_bill);
                dialog1.setCanceledOnTouchOutside(true);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                timebill=(ListView)dialog1.findViewById(R.id.productbill);
                dialog1.show();
                String itemValue = (String) list.getItemAtPosition(position);
                bill=new ArrayList<>();
                billdata.Open();
                bill=billdata.addingtimedata(itemValue);
                billdata.close();
                specficbilladapter=new BillAdapter(Billhistory.this,R.layout.bill_row,bill);
                timebill.setAdapter(specficbilladapter);
                dialog1.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog1.dismiss();
                    }
                });
            }
        });
    }
    class BillAdapter extends ArrayAdapter<billdatabaselist> {
        Context context2;
        int resource;
        ArrayList<billdatabaselist> arraylistbillname;
        LayoutInflater vi2;

        public BillAdapter(Context context, int resource,
                               ArrayList<billdatabaselist> objects) {
            super(context, resource, objects);
            arraylistbillname = objects;
            this.resource = resource;
            this.context2 = context;
            vi2 = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // TODO Auto-generated constructor stub
        }
        public class Holder {
            TextView name,price,quantity;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View row = convertView;
            Holder holder = null;
            if (row == null) {
                row = vi2.inflate(resource, null);
                holder = new Holder();
                holder.name = (TextView) row.findViewById(R.id.bname);
                holder.price=(TextView)row.findViewById(R.id.bprice);
                holder.quantity=(TextView)row.findViewById(R.id.bamount);
                row.setTag(holder);
            } else {
                holder = (Holder) row.getTag();
            }
            holder.name.setText(arraylistbillname.get(position).name);
            holder.price.setText(arraylistbillname.get(position).price);
            holder.quantity.setText(arraylistbillname.get(position).quantity);

            return row;

        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Billhistory.this, MainActivity.class);
        i.putExtra("flag","1");
        startActivity(i);
    }
}
