package com.poziomlabs.progress.dodukaan;

import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Splash extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    //   getApplicationContext().getSharedPreferences("MyPrefs", 0).edit().clear().commit();

        //For hiding the action bar occurring on the splash screen

    //    getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
     //   getSupportActionBar().hide();
   //     this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //   getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
     //   getActionBar().hide();

        setContentView(R.layout.activity_splash);

        View imv = (View) findViewById(R.id.splashid);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        imv.startAnimation(scaleAnimation);







       Thread location = new Thread()
        {


            private int k = 0;

         //   private final HttpClient Client = new DefaultHttpClient();
            private String URL = "https://script.google.com/macros/s/AKfycbwro50BxXC-8_Z_bEYJdFCfeQU8euwClIqaoOZs3mbCc-4wLtU/exec";
        //   final ProgressBar pro = (ProgressBar)findViewById(R.id.apicall);

            private void threadMsg(String msg) {
                Intent intnt = new Intent(getApplicationContext(), ListComplex.class);

                if (!msg.equals(null) && !msg.equals("")) {

                 //   Toast.makeText(getBaseContext(),"Message is: "+msg,Toast.LENGTH_SHORT).show();

                //    loadingdialog.dismiss();


                        intnt.putExtra("complex", msg);


                }

                else
                {
                    intnt.putExtra("schedule", "Not yet in your complex");



                }

                intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intnt);

            }

            private final Handler handler = new Handler() {

                public void handleMessage(Message msg) {

                    String aResponse = msg.toString();

                    if ((null != aResponse)) {
                           Log.d("Running",aResponse);
                        // ALERT MESSAGE
                    //    Toast.makeText(getBaseContext(),"Server Response: "+aResponse, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        // ALERT MESSAGE
                        Toast.makeText( getBaseContext(),"Not Got Response From Server.",Toast.LENGTH_SHORT).show();
                    }

                }
            };


            public void run() {
                try {

                    Looper.prepare();

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                       Toast.makeText(getApplicationContext(), "Network Available", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Network Unavailable", Toast.LENGTH_LONG).show();

                    }
                    MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                        @Override
                        public void gotLocation(Location location) {
                           Log.d("Running",String.valueOf(location.getLatitude()));
                           Log.d("Running",String.valueOf(location.getLongitude()));
//                       Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
                            final String SetServerString = "";
                            try{

                            URL = URL + "?lat=" + String.valueOf(location.getLatitude()) + "&lng=" + String.valueOf(location.getLongitude());

                              /*  HttpParams httpParameters = new BasicHttpParams();

                                int timeoutConnection = 4000;
                                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

                                int timeoutSocket = 10000;
                                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

                                HttpGet httpget = new HttpGet(URL);


                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = httpClient.execute(httpget, responseHandler);*/

                                final long mRequestStartTime;
                                mRequestStartTime = System.currentTimeMillis();
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                                               // Toast.makeText(getApplicationContext(),String.valueOf(totalRequestTime), Toast.LENGTH_LONG).show();
                                                threadMsg(response);

                                               // System.out.println(response.substring(0,100));

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast.makeText(getApplicationContext(),"Slow internet. Please restart the app", Toast.LENGTH_LONG).show();

                                        System.out.println("Something went wrong!");
                                        error.printStackTrace();
                                        Intent intnt = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                                        intnt.putExtra("schedule", "Not yet in your complex");
                                        intnt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intnt);

                                    }
                                });

                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        5000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);




                            }


                        catch(Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"No / Slow internet. Please restart the app", Toast.LENGTH_LONG).show();

                        }

/*
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            //  SharedPreferences.Editor editor = sharedpreferences.edit();
                            if (sharedpreferences.getString("username","notregistered") == "notregistered")
                            {
                                Scheduler fragmentS1 = new Scheduler();

                                getSupportFragmentManager().beginTransaction().replace(R.id.splashid, fragmentS1).commit();

                            }
*/


                        }

                    };


                    MyLocation myLocation = new MyLocation();
                    myLocation.getLocation(getApplicationContext(), locationResult);


                    Looper.loop();
                }
                catch(Exception e){
                    e.printStackTrace();

                }

                finally{

                    Toast.makeText(getApplicationContext(),"Done", Toast.LENGTH_LONG).show();



                }
            }

        };



     //   timer.start();
        location.start();

    }




}
