package com.poziomlabs.progress.dodukaan;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


public class ListComplex extends ActionBarActivity implements ViewSwitcher.ViewFactory {

    int imgs[] =
            {

                   R.drawable.app1,
                    R.drawable.car1,
                    R.drawable.notify1,
                    R.drawable.shop1,
                    R.drawable.bill1,
                   /* R.drawable.shop,
                    R.drawable.getbill*/

            };
    ListView list;
    ComplexAdapter adapter;
    ProgressDialog mProgressDialog;
    ImageSwitcher sw;
    public static int i = 0;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_complex);

        //Image Switcher Part for demo of the product

        sw = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
        sw.setFactory(this);
        sw.setImageResource(R.drawable.dd_image);
        sw.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        sw.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));

        final Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                sw.setImageResource(imgs[arg2]);
            }
        });


        //List View part which displays the complexes
        final String[] complex_array = getIntent().getStringExtra("complex").split(",", -1);
        ArrayList<HashMap<String, String>> comList = new ArrayList<HashMap<String, String>>();


        if (complex_array.length <= 3) {
            startFragment(0, "no");
            HashMap<String, String> h = new HashMap<String, String>();

            h.put("address","We haven't come to your neighborhood yet" );
            h.put("name", "Click to Preorder" );
            h.put("distance", "Sorry!" );
            comList.add(h);

        }


        else {



            for (int i = 0; i < (complex_array.length - 2) / 3; i++) {
                int myInt = (i == 0) ? 1 : 0;

                HashMap<String, String> h = new HashMap<String, String>();

                h.put("address", complex_array[3 * i + 2].toString().split(":", -1)[0 + myInt]);
                h.put("name", complex_array[3 * i + 2].toString().split(":", -1)[1 + myInt]);
                h.put("distance", complex_array[3 * i + 3].toString());
                comList.add(h);

            }

        }


            // FragmentManager fm = getSupportFragmentManager();

            list = (ListView) findViewById(R.id.list);
            // Getting adapter by passing xml data ArrayList
            adapter = new ComplexAdapter(this, comList);
            list.setAdapter(adapter);


            //Calling the listener from outside and not inside the adapter

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    startFragment(1, (String) ((TextView)((RelativeLayout) view).getChildAt(1)).getText());



                   /* Intent i = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                    //If you wanna send any data to nextActicity.class you can use
                    i.putExtra("schedule", position);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);*/

                    //      findViewById(R.id.list).setVisibility(View.GONE);
                    //  activity.findViewById(R.id.demo).setVisibility(View.VISIBLE);
                    //findViewById(R.id.demo).setVisibility(View.VISIBLE);

                    //             Scheduler fragment = new Scheduler();
                    //               getSupportFragmentManager().beginTransaction().replace(R.id.list, fragment, "demo").commit();
                }
            });


            // Click event for single list row


            final AtomicBoolean running = new AtomicBoolean(true);


            // Selecting an element directly from the parent element is always a problem because it is actually an adapter which is holding the view. So we sol
            //solve the problem by going to the adapter as was done before.


            // Finally this is the working solution for a set of images changing. It does not require Gallery.

            new Thread(new Runnable() {
                public void run() {

                    while (running.get()) {

                        // gallery.getAdapter().getView(i,null,null).performClick();
                        i = (i + 1) % 5;


                        try {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    sw.setImageResource(imgs[i]);
                                }
                            });

                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();


            //  running.set(false);



    }

    public class ImageAdapter extends BaseAdapter {

        private Context ctx;

        public ImageAdapter(Context c) {
            ctx = c;
        }

        public int getCount() {

            return imgs.length;
        }

        public Object getItem(int arg0) {

            return arg0;
        }

        public long getItemId(int arg0) {

            return arg0;
        }

        public View getView(int arg0, View arg1, ViewGroup arg2) {

            ImageView iView = new ImageView(ctx);
            iView.setImageResource(imgs[arg0]);
            iView.setScaleType(ImageView.ScaleType.FIT_XY);
            iView.setLayoutParams(new Gallery.LayoutParams(200, 150));
            return iView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_complex, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View makeView() {
        ImageView iView = new ImageView(this);
        iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iView.setLayoutParams(new ImageSwitcher.LayoutParams
                (ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        iView.setBackgroundColor(0xFF000000);
        return iView;
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void registration(View v)
    {
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.dname);
        final TextInputLayout phoneWrapper = (TextInputLayout) findViewById(R.id.dphone);
        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.demail);
        final TextInputLayout apartWrapper = (TextInputLayout) findViewById(R.id.dapartment);

        hideKeyboard();

        String username = usernameWrapper.getEditText().getText().toString();
        String phone = phoneWrapper.getEditText().getText().toString();
        String email = emailWrapper.getEditText().getText().toString();
        String apartment = apartWrapper.getEditText().getText().toString();



        if (validEmail(email)) {



            new RetrieveFeedTask().execute(username,phone,email,apartment);}

else
        {
            emailWrapper.setError("Invalid Email. Please re-enter...");
        }




    }


public void startFragment(int i, String name) {
    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    //  SharedPreferences.Editor editor = sharedpreferences.edit();
    if (sharedpreferences.getString("username", "notregistered") == "notregistered") {

        Bundle bundle = new Bundle();
        bundle.putString("complex", name);

        Scheduler fragmentS1 = new Scheduler();
        fragmentS1.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.cfinder, fragmentS1).commit();

       // if(name != "no")

    }

    else
    {
        Intent intnt = new Intent(getApplicationContext(), RecyclerViewActivity.class);
        //If you wanna send any data to nextActicity.class you can use

        if(i==0)
        intnt.putExtra("schedule", "Not yet in your complex");
        intnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intnt);
    }

}

    class RetrieveFeedTask extends AsyncTask<String, Integer, String> {

        private Exception exception;
        private String token;
        private String m_Text = "";

        protected String doInBackground(String... urls) {

            String post_url = "https://script.google.com/macros/s/AKfycbzrCZxm3dp-bWotDTaHVq6N_1RuenDohJcwIP5PYH_7Fbv9LVme/exec";


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(post_url);

            try
            {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", urls[0]));
                nameValuePairs.add(new BasicNameValuePair("phone", urls[1]));
                nameValuePairs.add(new BasicNameValuePair("email", urls[2]));
                nameValuePairs.add(new BasicNameValuePair("apartment", urls[3]));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpClient.execute(httpPost);







            }
            catch (ClientProtocolException e)
            {
                System.out.println("First Exception caz of HttpResponese :" + e);
                e.printStackTrace();
            }
            catch (IOException e)
            {
                System.out.println("Second Exception caz of HttpResponse :" + e);
                e.printStackTrace();
            }

            post_url = "https://api.ringcaptcha.com/6une6u8y8aguni5ija5o/code/SMS";
            httpPost = new HttpPost(post_url);

            try
            {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", "91"+urls[1]));
                nameValuePairs.add(new BasicNameValuePair("api_key","236c7aef0b5c57a7fd27fcbb29a84335a718c574"));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity =   response.getEntity();


                if (entity != null) {
                    String retSrc = EntityUtils.toString(entity);
                    // parsing JSON
                    JSONObject result = new JSONObject(retSrc); //Convert String to JSON Object
                     token = result.getString("token");

                }

            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("username", urls[1]);
            editor.putString("email", urls[2]);
            editor.putString("apartment", urls[3]);
            editor.commit();
            return urls[1];
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ListComplex.this);
            mProgressDialog.setTitle("Registering and sending you 4 digit PIN..Please wait");
            mProgressDialog.setIcon(R.drawable.s_run_speed);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }
        @Override
        protected void onProgressUpdate(Integer... progress) {

            super.onProgressUpdate(progress);
            // Update the ProgressBar
            mProgressDialog.setProgress(progress[0]);
        }
        protected void onPostExecute(final String k) {

            mProgressDialog.dismiss();

            /*
            Intent intnt = new Intent(getApplicationContext(), RecyclerViewActivity.class);
            if(i==0)
                intnt.putExtra("schedule", "Not yet in your complex");
            intnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intnt);

            */


            AlertDialog.Builder builder = new AlertDialog.Builder(ListComplex.this);
            builder.setTitle("Enter PIN");

// Set up the input
            final EditText input = new EditText(ListComplex.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);


// Set up the buttons
            builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    new captchaVerify().execute(k,token, m_Text);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();


            /*
            final EditText input = new EditText(ListComplex.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            mProgressDialog.setView(input);
            mProgressDialog.show();

            */





        }
    }

    class captchaVerify extends AsyncTask<String,String,String>
    {

        final EditText input = new EditText(ListComplex.this);

        @Override
        protected void onPreExecute()

        {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            mProgressDialog.setView(input);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String post_url = "https://api.ringcaptcha.com/6une6u8y8aguni5ija5o/verify";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(post_url);

            try
            {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("phone", "91"+params[0]));
                nameValuePairs.add(new BasicNameValuePair("api_key","236c7aef0b5c57a7fd27fcbb29a84335a718c574"));
                nameValuePairs.add(new BasicNameValuePair("token",params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);

            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

            return "Success";
        }

        @Override
        protected void onPostExecute(String k)

        {



            Intent intnt = new Intent(getApplicationContext(), RecyclerViewActivity.class);
            //If you wanna send any data to nextActicity.class you can use

            if(i==0 && k!= "Success")
                intnt.putExtra("schedule", "Wrong credentials. Please retry.");

            intnt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intnt);

        }

    }



}
