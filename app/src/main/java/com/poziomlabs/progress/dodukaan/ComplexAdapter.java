package com.poziomlabs.progress.dodukaan;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guest on 1/2/16.
 */
public class ComplexAdapter extends BaseAdapter {

    private Activity activity;
    private  ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater= null ;

    public ComplexAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem( int position) {
        return position;
    }
    public long getItemId( int position) {
        return position;
    }
    public View getView( int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if (convertView== null )
            vi = inflater.inflate(R.layout.listrow, null );
        TextView title = (TextView)vi.findViewById(R.id.title);
        final TextView artist = (TextView)vi.findViewById(R.id.artist);
        TextView duration = (TextView)vi.findViewById(R.id.cdistance);
      //  ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image);

        HashMap<String, String> complex = new  HashMap<String, String>();
        complex = data.get(position);




        title.setText(complex.get("name"));
        artist.setText(complex.get("address"));
        duration.setText(complex.get("distance")+" kms");
      //  thumb_image.setImageResource(R.drawable.a3);

/*
        vi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                activity.findViewById(R.id.list).setVisibility(View.GONE);
              //  activity.findViewById(R.id.demo).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.demo).setVisibility(View.VISIBLE);

                Scheduler fragment = new Scheduler();

                fm1.beginTransaction().add(R.id.demo, fragment, "demo").commit();
            }
        });*/

        return vi;
    }


}
