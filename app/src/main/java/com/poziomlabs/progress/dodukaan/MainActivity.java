package com.poziomlabs.progress.dodukaan;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "HttpExample";
    ArrayList<Product> products = new ArrayList<Product>();
    static ListView listview;
    static Button btnDownload;
    static RelativeLayout appbar;
    static TextView waiting;
    String storeID;
    ProgressDialog mProgressDialog;
    TextView tv;
    TextView green;
    static TextView dyn;
    static Integer firstindex = 0;
    static SparseBooleanArray selectedItems = new SparseBooleanArray(10);
    static SparseIntArray selectedQuant = new SparseIntArray(10);


    private final static int NOTIFY_ID= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  //      TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    //    Log.d("Running", tm.getDeviceId());
        Bundle bundle = getIntent().getExtras();
        storeID= bundle.getString("StoreID");



        listview = (ListView) findViewById(R.id.listview);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        appbar = (RelativeLayout) findViewById(R.id.toprect);
        waiting = (TextView) findViewById(R.id.details);
        btnDownload.setText("Click to Order ");
                //bundle.getString("StoreCategory").split(" ")[0]);


       // new LongOperation().execute("eh");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           // btnDownload.setEnabled(true);
           // btnDownload.setVisibility(View.GONE);
            btnDownload.performClick();
        } else {
            btnDownload.setEnabled(false);
        }
    }
    private class LongOperation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

                try {
                    HttpPost httppost = new HttpPost("http://dodukaan.com/mail");
                    HttpClient httpclient = new DefaultHttpClient();

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("content", dyn.getText().toString()));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpclient.execute(httppost);
                    // StatusLine stat = response.getStatusLine();
                    int status = response.getStatusLine().getStatusCode();

                    if (status == 200) {
                     //   HttpEntity entity = response.getEntity();
                     //   String data = EntityUtils.toString(entity);
                     //   JSONObject json = new JSONObject(data);
                        return "success";
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }

          return null;
        }

        @Override
        protected void onPostExecute(String result) {

            mProgressDialog.dismiss();

           order_complete();



        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set your ProgressBar Title
            mProgressDialog.setTitle("Ordering, Please wait");
            mProgressDialog.setIcon(R.drawable.s_run_speed);
            // Set your ProgressBar Message
            //mProgressDialog.setMessage("Obtaining Vendor Info");
            //mProgressDialog.setIndeterminate(false);
            //mProgressDialog.setMax(1);
            //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show ProgressBar
            mProgressDialog.setCancelable(false);
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            super.onProgressUpdate(progress);
            // Update the ProgressBar
            mProgressDialog.setProgress(progress[0]);
        }
    }

    public void quantity_change(View view)
    {

        dyn = (TextView)findViewById(R.id.dynamic);
        ViewGroup v = (ViewGroup)view.getParent();

        tv =     ((TextView)v.getChildAt(4));
       tv =     ((TextView) ((ViewGroup) view.getParent()).getChildAt(4));

        green =   ((TextView)v.getChildAt(2));

        if(v.indexOfChild(view) == 5 && Integer.parseInt(tv.getText().toString()) >= 0) {
            if(Integer.parseInt(tv.getText().toString()) == 1) {
                v.setBackgroundColor(Color.TRANSPARENT);
            selectedItems.delete(((ViewGroup)v.getParent()).indexOfChild(v)+firstindex);
            selectedQuant.delete(((ViewGroup)v.getParent()).indexOfChild(v)+firstindex);


            }
            if(Integer.parseInt(tv.getText().toString()) >= 1)
            {tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) - 1));
             dyn.setText(String.valueOf(  Float.parseFloat(dyn.getText().toString()) - Float.parseFloat(green.getText().toString().split(" ")[0]) ));
                selectedQuant.put(((ViewGroup)v.getParent()).indexOfChild(v)+firstindex,Integer.parseInt(tv.getText().toString()));

            }



        }
        else {

            if(Integer.parseInt(tv.getText().toString()) == 0) {
                v.setBackgroundColor(Color.CYAN);
                selectedItems.put(((ViewGroup)v.getParent()).indexOfChild(v)+firstindex, true);


            }
                tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
            selectedQuant.put(((ViewGroup) v.getParent()).indexOfChild(v) + firstindex, Integer.parseInt(tv.getText().toString()));

            dyn.setText(String.valueOf(Float.parseFloat(green.getText().toString().split(" ")[0]) + Float.parseFloat(dyn.getText().toString())));


        }



    }

    public void buttonClickHandler(View view) {

        dyn = (TextView)findViewById(R.id.dynamic);
        if(Float.parseFloat(dyn.getText().toString()) >= 200)

        {

            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("PreOrdering, Please wait");
            mProgressDialog.setIcon(R.drawable.s_run_speed);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            //new LongOperation().execute("eh");
            String url = "http://dodukaan.com/mail";

// Request a string response
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            mProgressDialog.dismiss();
                            order_complete();
                         //   System.out.println(response.substring(0,100));

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    // Error handling
                    System.out.println("Something went wrong!");
                    error.printStackTrace();

                }
            }) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    // the POST parameters:
                    params.put("content", "hello");
                    return params;
                }
            };

// Add the request to the queue
            Volley.newRequestQueue(this).add(stringRequest);
        }

        /*
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){

                //Displaying the android ID for analytics purposes. Source is stack overflow.
                Log.d("Running",Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID));

                //Displaying the latitude, longitude of the device. Source is stack overflow.

                Toast.makeText(getApplicationContext(),String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();



            }
        };

        // Simple notification push to the user. Source is plural sight

        String title = "Stores open";
        String text = "Come fast and collect your groceries";
        Intent intent = new Intent(this,CardViewActivity.class);
        intent.setAction("Notify");
        NotificationCompat.Builder builder  = initBasicBuilder(title,text,intent);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID,notification);


        // Detecting user's location. Source is stackoverflow.

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
        Log.d("Running", "Till here0");
*/

        // Downloading from spreadsheet. Source is telerik
else{
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
               // Toast.makeText(getApplicationContext(),storeID, Toast.LENGTH_LONG).show();

            //    Toast.makeText(getApplicationContext(),Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID),Toast.LENGTH_LONG).show();

                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1sGYusVnBg05_0l3UX_o2pRuR9PpLNK7N5c9G2zklQXk&gid="+storeID);}

    }

    //Notification builder function. Source is PluralSight
/*
    private NotificationCompat.Builder initBasicBuilder(String title, String text, Intent intent)

    {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.oe_logo).setContentTitle(title).setContentText(text);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getBaseContext());
        taskStackBuilder.addNextIntentWithParentStack(intent);



        if(intent != null) {
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            //  PendingIntent.getActivity(this,0,intent,0);
            builder.setContentIntent(pendingIntent);

            //AlarmManager for sending notifications through IntentService

            Intent myintent = new Intent(this, MyIntentService.class);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            pendingIntent = PendingIntent.getService(this, 0 ,myintent,0);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,20);
            calendar.set(Calendar.MINUTE, 58);
            calendar.set(Calendar.SECOND,00);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*100, pendingIntent);

        }

        return builder;

    }
    */

    //Processing the json read from spreadsheet. Source is telerik

    public void order_complete()
    {
        Bundle bundle = new Bundle();
        bundle.putString("complex", "done");

        ShopNear fragmentS2 = new ShopNear();
        fragmentS2.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.ofinder, fragmentS2).commit();

        Log.d("Running","Fine");

    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String name = columns.getJSONObject(0).getString("v");
                int quantity = columns.getJSONObject(1).getInt("v");
                float price = columns.getJSONObject(2).getInt("v");
                String image = columns.getJSONObject(3).getString("v");
                String username = columns.getJSONObject(4).getString("v");
                Product product = new Product(name, quantity, price, image, username);
                products.add(product);
            }


            final ProductAdapter adapter = new ProductAdapter(this, R.layout.product, products);
            listview.setAdapter(adapter);
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    firstindex = firstVisibleItem;
                    for(int i= 0; i < visibleItemCount ; i++) {
                        if (selectedItems.get(i + firstVisibleItem) == true)

                        {
                            view.getChildAt(i).setBackgroundColor(Color.CYAN);
                            ((TextView)((ViewGroup)view.getChildAt(i)).getChildAt(4)).setText(String.valueOf(selectedQuant.get(i+firstVisibleItem)));

                        } else {
                            view.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }

                    }

                }
            });




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




