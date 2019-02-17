package com.example.vaibhav.flamex;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {
    GridView product;
    ListView listView;
    ArrayList<ProductList> listItems;
    EditText psearch;
    BootstrapButton reset;
    BootstrapButton adminaccess;
    BootstrapButton calcu;
    BootstrapButton print;
    BootstrapButton clearbilldata;
    RelativeLayout rel;
    database products = new database(this);
    billdatabase bill = new billdatabase(this);
    CategoriesDatabase categories = new CategoriesDatabase(this);
    ArrayList<ProductList> productlist = new ArrayList<ProductList>();
    ArrayList<CategoryList> categorylist = new ArrayList<CategoryList>();
    ListAdapter adapter;
    ProductAdapter w;
    TextView total, discount_total, to_pay;
    float tot= (float) 0.00;
    float discount_tot= (float) 0.00;
    public static String filename = "userdatabase";
    SharedPreferences somedata;
    NotificationManager mNotificationManager;
    int numMessages=0;
    int notificationID=99;
    ArrayList<notificationlist> notificationlist = new ArrayList<notificationlist>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        total=(TextView)findViewById(R.id.Total);
        discount_total=(TextView)findViewById(R.id.DiscountTotal);
        to_pay=(TextView)findViewById(R.id.ToPay);
        clearbilldata=(BootstrapButton)findViewById(R.id.clearbill);
        listView = (ListView) findViewById(R.id.listView);
        product = (GridView) findViewById(R.id.gridView2);
        listItems = new ArrayList<ProductList>();
        adapter = new ListAdapter(this, listItems);
        listView.setAdapter(adapter);
        psearch = (EditText) findViewById(R.id.psearch);
        psearch.clearFocus();
        reset = (BootstrapButton) findViewById(R.id.resetProduct);
        calcu = (BootstrapButton) findViewById(R.id.calculator);
        print = (BootstrapButton) findViewById(R.id.print);
        rel = (RelativeLayout) findViewById(R.id.layouts);
        total=(TextView)findViewById(R.id.Total);
        discount_total=(TextView)findViewById(R.id.DiscountTotal);
        to_pay=(TextView)findViewById(R.id.ToPay);
        onTapOutsideBehaviour(rel);
        adminaccess = (BootstrapButton) findViewById(R.id.addproducts);
        adminaccess.setOnClickListener(this);
        products.Open();
        notificationlist=products.addingalldatainnotificationlist();
        productlist = products.addingalldata();
        products.close();
        if(notificationlist.size()!=0&&(getIntent().getStringExtra("flag").equals("0")))
        {displayNotification();}
        w = new ProductAdapter(getApplicationContext(), R.layout.row_grid,
                productlist);
        product.setAdapter(w);
        categories.Open();
        categorylist = categories.addingalldata();
        categories.close();
        CategoryAdapter s = new CategoryAdapter(getApplicationContext(),
                R.layout.row_grid, categorylist);
        final GridView categories = (GridView) findViewById(R.id.gridView1);
        categories.setAdapter(s);
        categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String ename = categorylist.get(position).name;
                w.filterbycategory(ename);
            }
        });
/*****************************************************************************************************/
        /*added this code and two new classes for swipe to delete
        However on delete adjust the total and discount accordingly and it is removed from the list but the total remain the same */

        //swipe to delete code
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listItems.get(position).quantityinsert=1;
                                    listItems.get(position).discount="0.00";
                                    listItems.remove(position);
                                    adapter.notifyDataSetChanged();
                                    total();
                                }

                            }
                        });
        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());
/********************************************Code for swipe ends here  ***********************************************************************/

        product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int flag = 0, size;
                ProductList p = (ProductList) product.getItemAtPosition(position);
                size = listItems.size();
                for (int i = 0; i < size; i++) {
                    String str = listItems.get(i).name;
                    String str2 = p.name;
                    if (str2.equals(str)) {
                        flag++;
                        if (p.quantityinsert < Integer.parseInt(p.quantity)) {
                            p.quantityinsert++;
                        } else {
                            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Product Exhausted!!", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED);
                            snackbar.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    snackbar.dismiss();
                                }
                            }, 500);
                        }
                    }
                }
                if (flag == 0) {
                    if (Integer.parseInt(p.quantity) <= 0) {
                        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Product Exhausted!!", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED);
                        snackbar.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                snackbar.dismiss();
                            }
                        }, 500);
                    } else {
                        listItems.add((ProductList) product.getItemAtPosition(position));
                    }
                }
                adapter.notifyDataSetChanged();
                total();
            }
        });
        clearbilldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<listItems.size();i++){
                    listItems.get(i).quantityinsert=1;
                    listItems.get(i).discount="0.00";
                }
                listItems.clear();
                adapter.notifyDataSetChanged();
                total();
            }
        });
        product.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ProductList p = (ProductList) product.getItemAtPosition(position);
                Snackbar.make(findViewById(android.R.id.content), "Product: "+p.name+" Quantity: "+p.quantity+" Price: "+p.price,Snackbar.LENGTH_SHORT)
                        .setActionTextColor(Color.RED)
                        .show();
                return true;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                w.filter("");
                psearch.setText("");
            }
        });
        psearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = psearch.getText().toString()
                        .toLowerCase(Locale.getDefault());
                w.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        calcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, calc.class);
                startActivity(i);
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listItems.isEmpty()) {
                    bill.Open();
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .format(Calendar.getInstance().getTime());
                    for (int i = 0; i < listItems.size(); i++) {
                        String name = listItems.get(i).name;
                        String category = listItems.get(i).category;
                        Integer quantity = listItems.get(i).quantityinsert;
                        String price = listItems.get(i).price;
                        bill.CreateEntry(name, quantity.toString(), price, formattedDate);
                        products.Open();
                        products.updatingquantity(name, category, quantity.toString());
                        products.close();
                    }
                    bill.close();
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    i.putExtra("flag", "0");
                    startActivity(i);
                    finish();
                }
            }

        });
        psearch.clearFocus();

    }


    public void total()
    {
        tot=(float)0.00;
        discount_tot=(float)0.00;
        for(int j=0;j<listItems.size();j++)
        {

            tot=tot+((Float.parseFloat(listItems.get(j).price) * listItems.get(j).quantityinsert));

            discount_tot=discount_tot+(Float.parseFloat(listItems.get(j).discount));

        }
        total.setText("Total: " + String.format("%.2f", (tot)));
        discount_total.setText("Discount: " + String.format("%.2f", (discount_tot)));
        to_pay.setText("Grand Total: " + String.format("%.2f", (tot - discount_tot)));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK){
                Bundle extras = getIntent().getExtras();
                int flag= data.getIntExtra("flag",1);
                String result=data.getStringExtra("result");
                int position=data.getIntExtra("position",0);
                if (flag==1)
                {
                    float conversion=(Float.parseFloat(result))/100;
                    listItems.get(position).discount=String.format("%.2f", ((Float.parseFloat(listItems.get(position).price) * listItems.get(position).quantityinsert) * conversion));
                    adapter.notifyDataSetChanged();
                }
                else if (flag==2)
                {
                    listItems.get(position).discount=result;
                }

                adapter.notifyDataSetChanged();
                total();


            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
    private void onTapOutsideBehaviour(View view) {
        if (!(view instanceof EditText) || !(view instanceof Button)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        final Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.custom_dialog_login);
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
                    Intent i = new Intent(MainActivity.this, Add.class);
                    startActivity(i);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Correct Parameters!!", Snackbar.LENGTH_SHORT)
                            .setActionTextColor(Color.RED)
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    class ProductAdapter extends ArrayAdapter<ProductList> {
        Context context2;
        int resource;
        ArrayList<ProductList> arraylistproductname;
        private ArrayList<ProductList> arraylist;
        LayoutInflater vi2;

        public ProductAdapter(Context context, int resource,
                              ArrayList<ProductList> objects) {
            super(context, resource, objects);
            arraylistproductname = objects;
            this.arraylist = new ArrayList<ProductList>();
            this.arraylist.addAll(arraylistproductname);
            this.resource = resource;
            this.context2 = context;
            vi2 = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // TODO Auto-generated constructor stub
        }

        public void filterbycategory(String ename) {
            ename = ename.toLowerCase();
            arraylistproductname.clear();
            if (ename.length() == 0) {
                arraylistproductname.addAll(arraylist);
            } else {
                for (ProductList wp : arraylist) {
                    if (wp.category.toLowerCase()
                            .equals(ename)) {
                        arraylistproductname.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public void filter(String charText) {
            charText = charText.toLowerCase();
            arraylistproductname.clear();
            if (charText.length() == 0) {
                arraylistproductname.addAll(arraylist);
            } else {
                for (ProductList wp : arraylist) {
                    if (wp.name.toLowerCase().contains(charText)) {
                        arraylistproductname.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public class Holder {
            Button button;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View row = convertView;
            Holder holder = null;
            if (row == null) {
                row = vi2.inflate(resource, null);
                holder = new Holder();
                holder.button = (Button) row.findViewById(R.id.button);
                row.setTag(holder);
            } else {
                holder = (Holder) row.getTag();
            }
            products.Open();
            holder.button.setText(arraylistproductname.get(position).name);
            if ((products.getquantity(arraylistproductname.get(position).name, arraylistproductname.get(position).category))<= 0) {
                holder.button.setBackgroundResource(R.drawable.list_item_finished);
                holder.button.setTextColor(Color.parseColor("#FF808381"));

            }
            else if ((products.getquantity(arraylistproductname.get(position).name, arraylistproductname.get(position).category)) < ((products.getCriticalquantity(arraylistproductname.get(position).name, arraylistproductname.get(position).category)))) {
                holder.button.setBackgroundResource(R.drawable.list_item_red);
                holder.button.setTextColor(Color.parseColor("red"));

            } else {
                holder.button.setBackgroundResource(R.drawable.list_buttons);
                holder.button.setTextColor(Color.parseColor("black"));
            }

            products.close();

            return row;
        }
    }

    class CategoryAdapter extends ArrayAdapter<CategoryList> {
        Context context2;
        int resource;
        ArrayList<CategoryList> arraylistcategoryname;
        LayoutInflater vi2;

        public CategoryAdapter(Context context, int resource,
                               ArrayList<CategoryList> objects) {
            super(context, resource, objects);
            arraylistcategoryname = objects;
            this.resource = resource;
            this.context2 = context;
            vi2 = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // TODO Auto-generated constructor stub
        }

        public class Holder {
            Button button;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View row = convertView;
            Holder holder = null;
            if (row == null) {
                row = vi2.inflate(resource, null);
                holder = new Holder();
                holder.button = (Button) row.findViewById(R.id.button);
                row.setTag(holder);
            } else {
                holder = (Holder) row.getTag();
            }
            holder.button.setText(arraylistcategoryname.get(position).name);
            return row;
        }
    }
    protected void displayNotification() {
          /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        mBuilder.setContentTitle("Caution!!");
        mBuilder.setContentText("Swipe down to see the message");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.logo_notification);
        mBuilder.setSound(soundUri);
        mBuilder.setAutoCancel(true);

   /* Increase notification number every time a new notification arrives */
        mBuilder.setNumber(++numMessages);

   /* Add Big View Specific Configuration */
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Low On Inventory!!");

        // Moves events into the big view
        for (int i=0; i < notificationlist.size(); i++) {
            inboxStyle.addLine("Product: "+notificationlist.get(i).name +"     Category: "+notificationlist.get(i).category+ "     Quantity: "+notificationlist.get(i).quantity);
        }

        mBuilder.setStyle(inboxStyle);

   /* Creates an explicit intent for an Activity in your app */
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("flag","1");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

}




