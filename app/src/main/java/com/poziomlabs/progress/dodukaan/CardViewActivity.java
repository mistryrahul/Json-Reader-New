package com.poziomlabs.progress.dodukaan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.content.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardViewActivity extends Activity {

    TextView storeName;
    TextView storeCategory;
    ImageView storePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_view);
        storeName = (TextView)findViewById(R.id.store_name);
        storeCategory = (TextView)findViewById(R.id.store_category);
        storePhoto = (ImageView)findViewById(R.id.store_photo);

        storeName.setText("Supreme Building");
        storeCategory.setText("Chandivali");
        storePhoto.setImageResource(R.drawable.a3);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        String pasteData = "";

        Log.d("Running",getIntent().getStringExtra("complex"));
        Toast.makeText(getApplicationContext(), getIntent().getStringExtra("complex"), Toast.LENGTH_LONG).show();


        CardView cv;
        cv = (CardView)findViewById(R.id.cv);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent opnactvty = new Intent("com.example.progress.json_reader.READERACTIVITY");
                Log.d("Intent", opnactvty.getAction());
                startActivity(opnactvty);
            }
        });


    }
}
