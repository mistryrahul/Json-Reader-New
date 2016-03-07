package com.poziomlabs.progress.dodukaan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private LruCache<String, Bitmap> mMemoryCache;

    Context context;
    private ArrayList<Product> products;



    public ProductAdapter(Context context, int textViewResourceId, ArrayList<Product> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.products = items;


        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };


    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // System.out.println("getview:"+position+" "+convertView);
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.product, null);

        }
        Product o = products.get(position);
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView quantity = (TextView) v.findViewById(R.id.quantity);
            TextView price = (TextView) v.findViewById(R.id.price);


            FrameLayout iframe = (FrameLayout) v.findViewById(R.id.imageframe);

            final ImageView image = (ImageView) v.findViewById(R.id.image);
          //  TextView username = (TextView) v.findViewById(R.id.username);
            //  TextView points = (TextView) v.findViewById(R.id.points);

            name.setText(String.valueOf(o.getName()));
            quantity.setText("0");
            price.setText(String.valueOf(o.getPrice()) + "  per kg");


           // View bar =   v.findViewById(R.id.name);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Float addsub;
                    int color = Color.TRANSPARENT;
                    Drawable background = v.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    if(color == Color.CYAN) {
                        ((ViewGroup) v).setBackgroundColor(Color.TRANSPARENT);
                        addsub = Float.parseFloat(((TextView)(((ViewGroup) v).getChildAt(2))).getText().toString().split(" ")[0]) * Float.parseFloat(((TextView)(((ViewGroup) v).getChildAt(4))).getText().toString()) ;
                        ((TextView)(((ViewGroup) v).getChildAt(4))).setText("0");
                        MainActivity.dyn.setText(String.valueOf(  Float.parseFloat(MainActivity.dyn.getText().toString()) -addsub));
                        MainActivity.selectedItems.delete(((ViewGroup)v.getParent()).indexOfChild(v) + MainActivity.firstindex);
                        MainActivity.selectedQuant.delete(((ViewGroup)v.getParent()).indexOfChild(v)+ MainActivity.firstindex );


                    }
                    else
                    {
                        ((TextView)(((ViewGroup) v).getChildAt(4))).setText("1");
                        ((ViewGroup) v).setBackgroundColor(Color.CYAN);
                        addsub = Float.parseFloat(((TextView)(((ViewGroup) v).getChildAt(2))).getText().toString().split(" ")[0]);
                        MainActivity.dyn.setText(String.valueOf(addsub + Float.parseFloat(MainActivity.dyn.getText().toString())));
                        MainActivity.selectedItems.put(((ViewGroup)v.getParent()).indexOfChild(v) + MainActivity.firstindex,true);
                        MainActivity.selectedQuant.put(((ViewGroup)v.getParent()).indexOfChild(v)+ MainActivity.firstindex ,1);

                    }

                }
            });

try {
//    class DownLoadImage extends AsyncTask<String, Integer, Bitmap> {
//        private FrameLayout iframe = null;
//        private int counter = 0;
//        private int content_len = 0;
//        private final WeakReference<FrameLayout> iframerefer;
//
//
//
//
//
//        public DownLoadImage(FrameLayout iframe)
//        {
//            this.iframe = iframe;
//            iframerefer = new WeakReference<FrameLayout> (iframe);
//        }
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            Bitmap bitmpa = null;
//            HttpURLConnection connection = null;
//            InputStream input = null;
//            FileOutputStream fileOutputStream;
//            File file;
//            try {
//                URL url = new URL(params[0].substring(params[0].indexOf('"')+1,params[0].indexOf('"',params[0].indexOf('"')+1)));
//                connection = (HttpURLConnection) url.openConnection();
//               // content_len= connection.getContentLength();
//
//                connection.setDoInput(true);
//
//              //  Toast.makeText(context, url.toString(), Toast.LENGTH_LONG).show();
//
//                connection.connect();
//
//
//
//
//                input = connection.getInputStream();
///*
//                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(params[0]).getLastPathSegment());
//                fileOutputStream = new FileOutputStream(file);
//                Log.d("File",file.getAbsolutePath());
//                int read = -1;
//
//
//                byte[] buffer = new byte[1024];
//                while((read = input.read(buffer))!= -1)
//                {
//                    fileOutputStream.write(buffer,0,read);
//                    counter += read;
//                    publishProgress(counter);
//                }
//*/
//                bitmpa = BitmapFactory.decodeStream(input);
//                addBitmapToMemoryCache(params[0],bitmpa);
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return bitmpa;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            Log.d("Counter",values[0].toString());
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            //super.onPostExecute(bitmap);
//            if(iframerefer!= null && bitmap != null)
//            {
//             final ProgressBar progressBar  = (ProgressBar)iframerefer.get().findViewById(R.id.progress);
//             final ImageView imageView = (ImageView)iframerefer.get().findViewById(R.id.image);
//                progressBar.setVisibility(View.GONE);
//               imageView.setImageBitmap(bitmap);
//
//            }
//
//
//
//
//        }
//        @Override
//        protected  void onPreExecute()
//        {
//            MainActivity.btnDownload.setVisibility(View.VISIBLE);
//            MainActivity.appbar.setVisibility(View.VISIBLE);
//            MainActivity.waiting.setVisibility(View.GONE);
//        }
//    }


//    final String imageKey = String.valueOf(o.getImage());
//    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
//    if (bitmap!= null)
//    {
//
//image.setImageBitmap(bitmap);
//    }
//    else {
//        image.setImageResource(R.drawable.dd_image);
//        final DownLoadImage dw = new DownLoadImage(iframe);
//        dw.execute(o.getImage());
//
//       // Picasso.with(this.context).load(o.getImage()).into(image);
//
//    }
    MainActivity.btnDownload.setVisibility(View.VISIBLE);
            MainActivity.appbar.setVisibility(View.VISIBLE);
            MainActivity.waiting.setVisibility(View.GONE);
    Picasso.with(this.context).load(o.getImage().substring(o.getImage().indexOf('"')+1,o.getImage().indexOf('"',o.getImage().indexOf('"')+1))).into(image);
}
catch (Exception e) {
    e.printStackTrace();
}



          //  username.setText(String.valueOf(o.getUsername()));
        }
        return v;
    }
}