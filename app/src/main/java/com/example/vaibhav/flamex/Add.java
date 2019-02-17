package com.example.vaibhav.flamex;

 import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Add extends TabActivity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.add);
        TabHost tabHost = getTabHost();
        TabSpec addproducts = tabHost.newTabSpec(" Add Products");
        addproducts.setIndicator("Add Products");
        Intent addproductIntent = new Intent(this, Addproducts.class);
        addproducts.setContent(addproductIntent);
        TabSpec addcategory = tabHost.newTabSpec("Add Category");
        addcategory.setIndicator("Add Category");
        Intent addcategoryIntent = new Intent(this, AddCategory.class);
        addcategory.setContent(addcategoryIntent);
        TabSpec updateinventory = tabHost.newTabSpec("Update Inventory");
        updateinventory.setIndicator("Update Inventory");
        Intent update = new Intent(this, UpdateInventory.class);
        updateinventory.setContent(update);
        TabSpec analytics = tabHost.newTabSpec("Analytics");
        analytics.setIndicator("Analytics");
        Intent analyticsproduct = new Intent(this, Analytics.class);
        analytics.setContent(analyticsproduct);
        TabSpec billhistory = tabHost.newTabSpec("Bill History");
        billhistory.setIndicator("Bill History");
        Intent billsproduct = new Intent(this, Billhistory.class);
        billhistory.setContent(billsproduct);
        tabHost.addTab(addproducts);
        tabHost.addTab(addcategory);
        tabHost.addTab(updateinventory);
        tabHost.addTab(billhistory);
        tabHost.addTab(analytics);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Add.this, MainActivity.class);
        i.putExtra("flag","1");
        startActivity(i);
    }

}
