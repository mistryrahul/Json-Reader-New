package com.poziomlabs.progress.dodukaan;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearComplex extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_complex);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] complex_array  =  getIntent().getStringExtra("complex").split(",",-1);

        for(int i =0; i<(complex_array.length -2)/3; i++) {
            int myInt = (i==0) ? 1 : 0;
            ((TextView) ((ViewGroup) ((ViewGroup) ((ViewGroup) findViewById(R.id.complex)).getChildAt(i)).getChildAt(0)).getChildAt(1)).setText(complex_array[3 * i + 2].toString().split(":", -1)[0+myInt]);
            ((TextView) ((ViewGroup) ((ViewGroup) ((ViewGroup) findViewById(R.id.complex)).getChildAt(i)).getChildAt(0)).getChildAt(0)).setText(complex_array[3 * i + 2].toString().split(":", -1)[1+myInt]);

        }

        for(int i = (complex_array.length -2)/3;i<3; i++)
            ((ViewGroup)findViewById(R.id.complex)).getChildAt(i).setVisibility(View.GONE);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop(Float.parseFloat(complex_array[0].split("\"",-1)[3]),Float.parseFloat(complex_array[1].split("\"",-1)[3]));
    }

    private void loadBackdrop(float lat, float lng) {
       // final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
       // Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14.0f) );
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(new LatLng(lat, lng)).title("Your location"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}