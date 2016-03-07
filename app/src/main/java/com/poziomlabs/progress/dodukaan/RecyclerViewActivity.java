package com.poziomlabs.progress.dodukaan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewActivity extends AppCompatActivity {

    private List<Store> stores;
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        if(getIntent().getAction() == "android.intent.action.VIEW") {
            Bundle bundle = getIntent().getExtras();
          Log.d("Running",getIntent().getData().toString());
        }

        setContentView(R.layout.activity_recycler_view);
        rv = (RecyclerView) findViewById(R.id.rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.parseColor("#fff0a443"));
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);


        initializeData();

        // specify an adapter (see also next example)
        mAdapter = new RVAdapter(stores,getApplicationContext());
        rv.setAdapter(mAdapter);
        /*
        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opnactvty = new Intent(getApplicationContext(), MainActivity.class);
                opnactvty.putExtra("StoreID",String.valueOf(v.getId()));
                opnactvty.putExtra("StoreCategory",String.valueOf(v.getId()));
                opnactvty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("Intent", opnactvty.getAction());
                startActivity(opnactvty);
            }
        });
        */






    }

    private void initializeData(){

        stores = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra("schedule");
        if(id != null)
            stores.add(new Store("Sorry!", "Currently we are not able to serve your request.", R.drawable.closed,0));

else{
        stores.add(new Store("Monday Fruits", "7 AM - 8AM", R.drawable.fruit,0));
        stores.add(new Store("Tuesday Veggies", "8 AM - 8:30 AM", R.drawable.vegetables,169668804));
        stores.add(new Store("American Wednesday", "7:30 AM - 8 AM", R.drawable.american,785383583));
        stores.add(new Store("Thursday Organic", "7:30 AM - 8 AM", R.drawable.veggies,1761712102));
        stores.add(new Store("Saturday Holi Special", "5:30 PM - 6 PM", R.drawable.sustainable,785383583));


        }

    }






}
