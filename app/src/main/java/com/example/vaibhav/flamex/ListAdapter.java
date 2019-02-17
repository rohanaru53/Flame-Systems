package com.example.vaibhav.flamex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter implements View.OnClickListener{

    ArrayList<ProductList> result;
    Context context;
    private static final int RESULT_REQUEST=1;


    int [] imageId;
    private static LayoutInflater inflater=null;
    public ListAdapter(MainActivity mainActivity, ArrayList<ProductList> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;

        context=mainActivity;
        inflater = ( LayoutInflater )mainActivity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        //return null;
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder{
        TextView name;
        TextView price;
        TextView quant;
        TextView amount;
        TextView discount_amount;
        ImageButton cancel;
        ImageButton discount;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item, null);
        holder.name=(TextView) rowView.findViewById(R.id.name);
        holder.price=(TextView) rowView.findViewById(R.id.price);
        holder.quant=(EditText) rowView.findViewById(R.id.quant);
        holder.amount=(TextView)rowView.findViewById(R.id.amount);
        holder.discount_amount=(TextView)rowView.findViewById(R.id.discountamt);
        holder.cancel=(ImageButton) rowView.findViewById(R.id.cancel);
        holder.discount=(ImageButton)rowView.findViewById(R.id.discount);
        holder.name.setText(result.get(position).name);
        holder.price.setText(result.get(position).price);
        holder.quant.setText(Integer.toString(result.get(position).quantityinsert));
        holder.quant.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if(holder.quant.getText().length()!=0) {
                        InputMethodManager inputManager = (InputMethodManager)
                                context.getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        if (Integer.parseInt(result.get(position).quantity) >= Integer.parseInt(holder.quant.getText().toString())) {
                            result.get(position).quantityinsert = Integer.parseInt(holder.quant.getText().toString());
                            notifyDataSetChanged();
                            if(context instanceof MainActivity){
                                ((MainActivity)context).total();
                            }
                        }
                        else {
                            holder.quant.setText(result.get(position).quantity);
                            result.get(position).quantityinsert = Integer.parseInt(result.get(position).quantity);
                            notifyDataSetChanged();
                            if(context instanceof MainActivity){
                                ((MainActivity)context).total();
                            }
                           Toast.makeText(context, "Quantity Exceeded", Toast.LENGTH_SHORT).show();
                        }
                    }

                    return true;
                }
                return false;
            }
        });
        holder.cancel.setImageResource(R.drawable.cancel);
        holder.discount.setImageResource(R.drawable.discount);

        holder.amount.setText(Integer.toString(Integer.parseInt(result.get(position).price) * result.get(position).quantityinsert));
        holder.discount_amount.setText(result.get(position).discount);
        holder.cancel.setTag(position);

        holder.cancel.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        result.get(position).quantityinsert=1;
                        result.get(position).discount="0.00";
                        result.remove(position);
                        notifyDataSetChanged();
                        if(context instanceof MainActivity){
                            ((MainActivity)context).total();
                        }


                    }
                }
        );
        holder.discount.setTag(position);
        holder.discount.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent i = new Intent(context,Discount_calc.class);
                        Bundle pass=new Bundle();
                        pass.putInt("position",position);
                        i.putExtras(pass);
                        ((Activity)context).startActivityForResult(i, RESULT_REQUEST);
                    }
                }
        );



        return rowView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


}

